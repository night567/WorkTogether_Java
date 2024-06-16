package cn.edu.szu.teamwork.mapper;

import cn.edu.szu.teamwork.pojo.domain.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper extends BaseMapper<Report> {
    @Select("select * from wt_report where user_id=#{userId}")
    List<Report> getMyReports(String userId);
}
