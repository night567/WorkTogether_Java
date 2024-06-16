package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.teamwork.mapper.ReportMapper;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl  extends ServiceImpl<ReportMapper,Report> implements ReportService{
    @Autowired
    private ReportMapper reportMapper;
    @Override
    public Report getReportById(String id) {
        return reportMapper.selectById(id);
    }

    @Override
    public List<Report> getMyReports(String userId) {
        return reportMapper.getMyReports(userId);
    }
}
