package cn.edu.szu.teamwork.service;

import cn.edu.szu.teamwork.pojo.ReportCondition;
import cn.edu.szu.teamwork.pojo.ReportVO;
import cn.edu.szu.teamwork.pojo.domain.Report;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ReportService extends IService<Report> {
    boolean submitReport(Report report);

    boolean updateReport(Report report);

    Report getReportById(String id);

    List<Report> getMyReports(String userId);


    List<ReportVO> getReportByCondition(ReportCondition condition, Long cid);
}
