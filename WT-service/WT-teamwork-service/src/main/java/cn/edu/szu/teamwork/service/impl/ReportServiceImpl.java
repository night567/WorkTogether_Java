package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.teamwork.mapper.ReportMapper;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static cn.edu.szu.common.utils.RedisConstants.USER_REPORT_KEY;

/**
 * @author zgr24
 * @description 针对表【wt_report(简报)】的数据库操作Service实现
 * @createDate 2024-06-16 16:27:47
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean submitReport(Report report) {
        // 保存数据到数据库
        report.setStatus(0);
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
}




