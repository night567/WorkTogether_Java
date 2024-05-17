package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.teamwork.mapper.ScheduleUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.service.ScheduleService;
import cn.edu.szu.teamwork.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author zgr24
* @description 针对表【wt_schedule(日程)】的数据库操作Service实现
* @createDate 2024-05-18 01:27:44
*/
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService{
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private ScheduleUserMapper scheduleUserMapper;
}




