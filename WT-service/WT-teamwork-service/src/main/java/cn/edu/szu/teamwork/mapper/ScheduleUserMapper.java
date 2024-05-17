package cn.edu.szu.teamwork.mapper;

import cn.edu.szu.teamwork.pojo.domain.ScheduleUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zgr24
* @description 针对表【wt_schedule_user(日程参与者)】的数据库操作Mapper
* @createDate 2024-05-18 01:36:47
* @Entity cn.edu.szu.teamwork.pojo.domain.ScheduleUser
*/
@Mapper
public interface ScheduleUserMapper extends BaseMapper<ScheduleUser> {

}




