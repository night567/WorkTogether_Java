package cn.edu.szu.teamwork.service;

import cn.edu.szu.teamwork.pojo.ScheduleDTO;
import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.pojo.domain.ScheduleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zgr24
 * @description 针对表【wt_schedule(日程)】的数据库操作Service
 * @createDate 2024-05-18 01:27:44
 */
public interface ScheduleService extends IService<Schedule> {
    boolean createSchedule(ScheduleDTO scheduleDTO);

    boolean updateSchedule(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> selectUserSchedule(Long groupId, Long userId, String startTime, String endTime, boolean flag);

    boolean isScheduleInTimeRange(ScheduleDTO scheduleDTO, LocalDateTime startTime, LocalDateTime endTime);

    List<ScheduleDTO> selectScheduleByGroupId(Long groupId, String startTime, String endTime, boolean flag);

    List<ScheduleDTO> selectScheduleByType(Long groupId, Long type, String startTime, String endTime, boolean flag);

    ScheduleDTO selectScheduleById(Long id);

    List<ScheduleUser> selectScheduleMemberList(Long id);

    boolean judgeSchedule(Long scheduleId, Long uid);

    boolean joinSchedule(Long scheduleId, Long uid);

    boolean refuseOrTentativeSchedule(ScheduleUser scheduleUserDTO);
}
