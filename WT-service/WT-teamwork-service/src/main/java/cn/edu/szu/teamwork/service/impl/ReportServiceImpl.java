package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.feign.client.CompanyClient;
import cn.edu.szu.feign.client.UserClient;
import cn.edu.szu.teamwork.mapper.ReportMapper;
import cn.edu.szu.teamwork.pojo.ReportCondition;
import cn.edu.szu.teamwork.pojo.domain.Report;
import cn.edu.szu.teamwork.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl  extends ServiceImpl<ReportMapper,Report> implements ReportService{
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    UserClient userClient;
    @Autowired
    CompanyClient companyClient;

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
