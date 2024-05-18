package cn.edu.szu.teamwork.mapper;

import cn.edu.szu.teamwork.pojo.domain.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author zgr24
* @description 针对表【wt_schedule(日程)】的数据库操作Mapper
* @createDate 2024-05-18 01:27:44
* @Entity cn.edu.szu.teamwork.pojo.domain.Schedule
*/
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {
    Schedule selectScheduleByIdAndGroupId(Long Id, Long groupId);
    List<Schedule> selectScheduleByGroupId(Long groupId);
    List<Schedule> selectScheduleByType(Long groupId,Long type);

}




