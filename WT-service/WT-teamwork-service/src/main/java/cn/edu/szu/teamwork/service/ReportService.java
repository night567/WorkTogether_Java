package cn.edu.szu.teamwork.service;

import cn.edu.szu.teamwork.pojo.domain.Report;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zgr24
* @description 针对表【wt_report(简报)】的数据库操作Service
* @createDate 2024-06-16 16:27:47
*/
public interface ReportService extends IService<Report> {
    boolean submitReport(Report report);

    boolean updateReport(Report report);
}
