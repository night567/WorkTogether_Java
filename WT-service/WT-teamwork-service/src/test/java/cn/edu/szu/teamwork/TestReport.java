package cn.edu.szu.teamwork;

import cn.edu.szu.teamwork.mapper.ReportMapper;
import cn.edu.szu.teamwork.pojo.ReportVO;
import cn.edu.szu.teamwork.pojo.domain.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestReport {
    @Autowired
    ReportMapper mapper;

    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        List<ReportVO> reportByConditions =
                mapper.getReportByConditions(null, null, list,null);
        System.out.println(reportByConditions);
    }
}
