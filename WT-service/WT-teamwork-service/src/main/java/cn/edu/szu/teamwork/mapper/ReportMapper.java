package cn.edu.szu.teamwork.mapper;

import cn.edu.szu.teamwork.pojo.domain.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zgr24
 * @description 针对表【wt_report(简报)】的数据库操作Mapper
 * @createDate 2024-06-16 16:27:47
 * @Entity cn.edu.szu.teamwork.pojo.domain.Report
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {

}




