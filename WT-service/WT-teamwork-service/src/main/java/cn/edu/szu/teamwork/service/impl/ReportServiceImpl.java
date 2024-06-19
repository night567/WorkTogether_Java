package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.feign.client.CompanyClient;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.teamwork.mapper.ReportMapper;
import cn.edu.szu.teamwork.pojo.ReportCondition;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        report.setReportTime(LocalDateTime.now());
        if (updateById(report)) {
            return true;
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
    public List<Report> getReportByCondition(ReportCondition condition,Long cid) {
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
            List<Report> res = reportMapper.getReportByConditions(condition.getStatus(), condition.getWeekNum(), idsByName);
            return res;
        }
        List<Report> res = reportMapper.getReportByConditions(condition.getStatus(), condition.getWeekNum(), null);
        return res;
    }
}
