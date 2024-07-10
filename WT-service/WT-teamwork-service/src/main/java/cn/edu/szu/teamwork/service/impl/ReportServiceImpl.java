package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.feign.client.CompanyClient;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.teamwork.mapper.ReportMapper;
import cn.edu.szu.teamwork.pojo.MyRedLock;
import cn.edu.szu.teamwork.pojo.ReportCondition;
import cn.edu.szu.teamwork.pojo.ReportVO;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.edu.szu.common.utils.RedisConstants.USER_REPORT_KEY;

@Service
public class ReportServiceImpl  extends ServiceImpl<ReportMapper,Report> implements ReportService{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    UserClient userClient;
    @Autowired
    CompanyClient companyClient;

    @Autowired
    private RedissonClient redissonClient1;

    @Autowired
    private RedissonClient redissonClient2;

    @Autowired
    private RedissonClient redissonClient3;

    @Override
    public boolean submitReport(Report report) {
        // 保存数据到数据库
        report.setStatus(0L);
        report.setReportTime(LocalDateTime.now());
        boolean saved = save(report);
        if (!saved) {
            return false;
        }

        // 记录提交信息到redis的bitmap中
        String key = USER_REPORT_KEY + report.getUserId() + ":" + report.getYear();
        stringRedisTemplate.opsForValue().setBit(key, report.getWeekNum(), true);

        return true;
    }

    @Override
    public boolean updateReport(Report report) {
        RLock lock1 = redissonClient1.getLock(String.valueOf(report.getId()));
        RLock lock2 = redissonClient2.getLock(String.valueOf(report.getId()));
        RLock lock3 = redissonClient3.getLock(String.valueOf(report.getId()));
        //创建红锁 客户端
        RedissonRedLock redLock = new MyRedLock(lock1, lock2, lock3);
        report.setReportTime(LocalDateTime.now());
        boolean isLockBoolean;
        try {
            isLockBoolean = redLock.tryLock(1, 20, TimeUnit.SECONDS);
            if (updateById(report)) {
                return true;
            }
        } catch (InterruptedException e) {
            System.err.printf("线程:"+Thread.currentThread().getId()+"发生异常,加锁失败");
            throw new RuntimeException(e);
        }finally {
            // 无论如何，最后都要解锁
            redLock.unlock();
        }


        return false;
    }

    @Override
    public Report getReportById(String id) {
        return reportMapper.selectById(id);
    }

    @Override
    public List<Report> getMyReports(String userId) {
        return reportMapper.getMyReports(userId);
    }

    @Override
    public List<ReportVO> getReportByCondition(ReportCondition condition, Long cid) {
        String test = condition.getName();
        // 假设你有以下列表:
        if (condition.getName() != null && !condition.getName().trim().equals("")){
            List<String> idsByName = userClient.getIdsByName(condition.getName());
            List<Long> cuids = companyClient.selectUserIdsByCID(cid);
            List<String> uid = new ArrayList<>();
            for (Long cuid : cuids) {
                uid.add(String.valueOf(cuid));
            }

// 使用 retainAll() 方法找到交集
            idsByName.retainAll(uid);
            if (idsByName.size() == 0){
                idsByName.add("-1");
            }
            List<ReportVO> res = reportMapper.getReportByConditions(condition.getStatus(), condition.getWeekNum(), idsByName, condition.getUserId());
            setReportVO(res);
            return res;
        }
        List<ReportVO> res = reportMapper.getReportByConditions(condition.getStatus(), condition.getWeekNum(), null, condition.getUserId());
        setReportVO(res);
        return res;
    }

    @Override
    public boolean reviewReport(String id) {
        return reportMapper.reviewReport(id)>0;
    }

    private void setReportVO(List<ReportVO> res){
        for (ReportVO vo: res){
            String avatar = userClient.getAvatarById(String.valueOf(vo.getUserId()));
            vo.setImgUrl(avatar);
        }
    }

}
