package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.teamwork.mapper.ScheduleMapper;
import cn.edu.szu.teamwork.mapper.ScheduleUserMapper;
import cn.edu.szu.teamwork.pojo.ScheduleDTO;
import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.pojo.domain.ScheduleUser;
import cn.edu.szu.teamwork.service.ScheduleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zgr24
 * @description 针对表【wt_schedule(日程)】的数据库操作Service实现
 * @createDate 2024-05-18 01:27:44
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private ScheduleUserMapper scheduleUserMapper;
    // 日期时间格式
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public boolean createSchedule(ScheduleDTO scheduleDTO) {
        // 创建日程
        Schedule schedule = Schedule.builder()
                .groupId(Long.valueOf(scheduleDTO.getGroupId()))
                .creatorId(Long.valueOf(scheduleDTO.getCreatorId()))
                .title(scheduleDTO.getTitle())
                .startTime(scheduleDTO.getStartTime())
                .endTime(scheduleDTO.getEndTime())
                .type(scheduleDTO.getType())
                .description(scheduleDTO.getDescription())
                .build();
        if (scheduleMapper.insert(schedule) <= 0) {
            throw new RuntimeException("创建日程失败");
        }

        // 构建参与人数据库
        Long scheduleId = schedule.getId();
        List<String> userIds = scheduleDTO.getUserIds();
        for (String userId : userIds) {
            ScheduleUser scheduleUser = new ScheduleUser();
            scheduleUser.setScheduleId(scheduleId);
            scheduleUser.setUserId(Long.valueOf(userId));
            scheduleUser.setJoinStatus(0);
            if (scheduleUserMapper.insert(scheduleUser) <= 0) {
                throw new RuntimeException("创建参与人失败");
            }
        }

        return true;
    }

    @Override
    @Transactional
    public boolean updateSchedule(ScheduleDTO scheduleDTO) {
        // 创建日程
        Schedule schedule = Schedule.builder()
                .id(Long.valueOf(scheduleDTO.getId()))
                .title(scheduleDTO.getTitle())
                .startTime(scheduleDTO.getStartTime())
                .endTime(scheduleDTO.getEndTime())
                .type(scheduleDTO.getType())
                .description(scheduleDTO.getDescription())
                .build();
        if (scheduleMapper.updateById(schedule) <= 0) {
            throw new RuntimeException("更新日程失败");
        }

        // 更新成员列表
        Long scheduleId = schedule.getId();
        List<Long> userIds = scheduleDTO.getUserIds().stream()
                .map(Long::valueOf).collect(Collectors.toList());
        // 获取原先参与成员列表
        List<Long> userList = scheduleUserMapper.selectList(new LambdaQueryWrapper<ScheduleUser>()
                        .eq(ScheduleUser::getScheduleId, scheduleId)
                        .select(ScheduleUser::getUserId))
                .stream()
                .map(ScheduleUser::getUserId)
                .collect(Collectors.toList());

        // 需要新增的用户列表
        ArrayList<Long> addUserList = new ArrayList<>(userIds);
        addUserList.removeAll(userList);

        // 需要删除的用户列表
        ArrayList<Long> delUserList = new ArrayList<>(userList);
        delUserList.removeAll(userIds);

        // 新增用户
        for (Long userId : addUserList) {
            ScheduleUser scheduleUser = new ScheduleUser();
            scheduleUser.setScheduleId(scheduleId);
            scheduleUser.setUserId(userId);
            scheduleUser.setJoinStatus(0);
            if (scheduleUserMapper.insert(scheduleUser) <= 0) {
                throw new RuntimeException("创建参与人失败");
            }
        }

        // 删除用户
        for (Long userId : delUserList) {
            int deleted = scheduleUserMapper.delete(new LambdaQueryWrapper<ScheduleUser>()
                    .eq(ScheduleUser::getScheduleId, scheduleId)
                    .eq(ScheduleUser::getUserId, userId));
            if (deleted <= 0) {
                throw new RuntimeException("删除参与人失败");
            }
        }

        return true;
    }

    //获取个人日程
    @Override
    public List<ScheduleDTO> selectUserSchedule(Long groupId, Long userId, String startTime, String endTime, boolean flag) {
        // 查询用户日程ID集合
        List<Long> scheduleIds = scheduleUserMapper.selectScheduleIdByUserId(userId);

        // 解析字符串形式的时间为 LocalDateTime 对象
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (flag == true) {
            start = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
            end = LocalDateTime.parse(endTime, DATE_TIME_FORMATTER);
        }
        List<ScheduleDTO> scheduleList = new ArrayList<>();


        for (Long id : scheduleIds) {
            // 查询日程
            Schedule schedule = scheduleMapper.selectScheduleByIdAndGroupId(id, groupId);
            List<ScheduleUser> scheduleUsers = scheduleUserMapper.selectUsersByScheduleId(schedule.getId());
            ScheduleDTO scheduleDTO = new ScheduleDTO(schedule);
            scheduleDTO.setScheduleUsers(scheduleUsers);

            //判断获取全部还是时间区内的
            if (flag == true) {
                // 判断日程在给定时间范围内
                if (schedule != null && isScheduleInTimeRange(scheduleDTO, start, end)) {
                    scheduleList.add(scheduleDTO);
                }
            } else {
                scheduleList.add(scheduleDTO);
            }
        }
        return scheduleList;


    }


    // 判断日程是否在给定时间范围内的辅助方法
    @Override
    public boolean isScheduleInTimeRange(ScheduleDTO scheduleDTO, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime scheduleStartTime = scheduleDTO.getStartTime();
        LocalDateTime scheduleEndTime = scheduleDTO.getEndTime();

        // 日程开始时间晚于给定时间范围的结束时间，或者日程结束时间早于给定时间范围的开始时间，则日程不在时间范围内
        return !scheduleStartTime.isAfter(endTime) && !scheduleEndTime.isBefore(startTime);
    }

    //获取团队日程集合
    @Override
    public List<ScheduleDTO> selectScheduleByGroupId(Long groupId, String startTime, String endTime, boolean flag) {
        // 解析字符串形式的时间为 LocalDateTime 对象
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (flag == true) {
            start = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
            end = LocalDateTime.parse(endTime, DATE_TIME_FORMATTER);
        }
        //获取团队全部日程
        List<Schedule> schedules = scheduleMapper.selectScheduleByGroupId(groupId);
        List<ScheduleDTO> schedulesDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO(schedule);
            schedulesDTOs.add(scheduleDTO);
        }
        //获取日程参与者
        for (ScheduleDTO scheduleDTO : schedulesDTOs) {
            String idAsString = scheduleDTO.getId();
            try {
                Long scheduleId = Long.parseLong(idAsString);
                List<ScheduleUser> scheduleUsers= scheduleUserMapper.selectUsersByScheduleId(scheduleId);
                // 进一步处理 scheduleUser

                scheduleDTO.setScheduleUsers(scheduleUsers);
            } catch (NumberFormatException e) {
                // 处理无法转换为 Long 类型的情况
                throw e;
            }

        }

        //定义符合时间的日程
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        for (ScheduleDTO scheduleDTO : schedulesDTOs) {
            if (flag == true) {
                // 判断日程在给定时间范围内
                if (scheduleDTO != null && isScheduleInTimeRange(scheduleDTO, start, end)) {
                    scheduleList.add(scheduleDTO);
                }
            } else {
                scheduleList.add(scheduleDTO);
            }
        }
        return scheduleList;
    }

    //获取不同类型日程集合
    @Override
    public List<ScheduleDTO> selectScheduleByType(Long groupId, Long type, String startTime, String endTime, boolean flag) {
        // 解析字符串形式的时间为 LocalDateTime 对象
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (flag == true) {
            start = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
            end = LocalDateTime.parse(endTime, DATE_TIME_FORMATTER);
        }
        //获取该类型全部日程
        List<Schedule> schedules = scheduleMapper.selectScheduleByType(groupId, type);
        List<ScheduleDTO> schedulesDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO(schedule);
            schedulesDTOs.add(scheduleDTO);
        }
        for (ScheduleDTO scheduleDTO : schedulesDTOs) {

            String idAsString = scheduleDTO.getId();
            try {
                Long scheduleId = Long.parseLong(idAsString);
                List<ScheduleUser> scheduleUsers = scheduleUserMapper.selectUsersByScheduleId(scheduleId);
                // 进一步处理 scheduleUser
                scheduleDTO.setScheduleUsers(scheduleUsers);
            } catch (NumberFormatException e) {
                // 处理无法转换为 Long 类型的情况
                throw e;
            }
        }
        //定义符合时间的日程
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        for (ScheduleDTO scheduleDTO : schedulesDTOs) {
            if (flag == true) {
                // 判断日程在给定时间范围内
                if (scheduleDTO != null && isScheduleInTimeRange(scheduleDTO, start, end)) {
                    scheduleList.add(scheduleDTO);
                }
            } else {
                scheduleList.add(scheduleDTO);
            }

        }
        return scheduleList;
    }

    @Override
    public ScheduleDTO selectScheduleById(Long id) {
        Schedule schedule = scheduleMapper.selectById(id);
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setId(String.valueOf(schedule.getId()));
        scheduleDTO.setGroupId(String.valueOf(schedule.getGroupId()));
        scheduleDTO.setCreatorId(String.valueOf(schedule.getCreatorId()));

        scheduleDTO.setUserIds(scheduleUserMapper.selectUserIdByScheduleId(id));
        return scheduleDTO;
    }

    @Override
    public List<ScheduleUser> selectScheduleMemberList(Long id, Integer type) {
        LambdaQueryWrapper<ScheduleUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScheduleUser::getScheduleId, id);
        lqw.eq(ScheduleUser::getJoinStatus, type);
        return scheduleUserMapper.selectList(lqw);
    }

    @Override
    public boolean judgeSchedule(Long scheduleId, Long uid) {
        List<Long> ids = scheduleUserMapper.selectScheduleIdByUserId(uid);
        Schedule newSchedule = scheduleMapper.selectById(scheduleId);
        List<Schedule> schedules = scheduleMapper.selectBatchIds(ids);
        System.out.println(schedules);
        if (schedules.isEmpty()) {
            return true;
        }
        for (Schedule schedule : schedules) {
            if (schedule.getStartTime().isAfter(newSchedule.getStartTime()) && schedule.getStartTime().isBefore(newSchedule.getEndTime())) {
                return false;
            }
            if (schedule.getEndTime().isAfter(newSchedule.getStartTime()) && schedule.getEndTime().isBefore(newSchedule.getEndTime())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean joinSchedule(Long scheduleId, Long uid) {
        ScheduleUser scheduleUser = new ScheduleUser();
        scheduleUser.setScheduleId(scheduleId);
        scheduleUser.setUserId(uid);
        scheduleUser.setJoinStatus(0);
        scheduleUser.setIsDeleted(false);
        scheduleUserMapper.insert(scheduleUser);
        return false;
    }

    @Override
    @Transactional
    public boolean refuseOrTentativeSchedule(ScheduleUser scheduleUserDTO) {
        Integer joinStatus = scheduleUserDTO.getJoinStatus();

        // 查数据库对象(检验请求正确)
        LambdaQueryWrapper<ScheduleUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScheduleUser::getScheduleId, scheduleUserDTO.getScheduleId());
        lqw.eq(ScheduleUser::getUserId, scheduleUserDTO.getUserId());
        ScheduleUser scheduleUser = scheduleUserMapper.selectOne(lqw);
        if (scheduleUser == null) {
            return false;
        }

        // 修改数据库
        if (joinStatus == null) {
            return false;
        } else if (joinStatus == 0) {
            scheduleUser.setJoinStatus(0);
            scheduleUser.setRefuseReason(null);
        } else if (joinStatus == 2) {
            scheduleUser.setJoinStatus(2);
            scheduleUser.setRefuseReason(scheduleUserDTO.getRefuseReason());
        } else {
            return false;
        }
        scheduleUserMapper.updateById(scheduleUser);
        return true;
    }
}









