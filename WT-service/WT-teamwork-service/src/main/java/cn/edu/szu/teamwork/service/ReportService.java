package cn.edu.szu.teamwork.service;

import cn.edu.szu.teamwork.pojo.ReportCondition;
import cn.edu.szu.teamwork.pojo.domain.Report;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ReportService extends IService<Report> {
    Report getReportById(String id);
    List<Report> getMyReports(String userId);

    List<Report> getReportByCondition(ReportCondition condition,Long cid);
}
