package cn.edu.szu.teamwork.service.impl;

import cn.edu.szu.teamwork.mapper.ScheduleMapper;
import cn.edu.szu.teamwork.mapper.ScheduleUserMapper;
import cn.edu.szu.teamwork.pojo.ScheduleDTO;
import cn.edu.szu.teamwork.pojo.domain.Message;
import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.pojo.domain.ScheduleUser;
import cn.edu.szu.teamwork.service.MessageService;
import cn.edu.szu.teamwork.service.ScheduleService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    private MessageService messageService;
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
        List<Long> userIds = scheduleDTO.getUserIds().stream().map(Long::valueOf).collect(Collectors.toList());
        for (Long userId : userIds) {
            ScheduleUser scheduleUser = new ScheduleUser();
            scheduleUser.setScheduleId(scheduleId);
            scheduleUser.setUserId(userId);
            scheduleUser.setJoinStatus(0);
            if (scheduleUserMapper.insert(scheduleUser) <= 0) {
                throw new RuntimeException("创建参与人失败");
            }
        }

        // 发送邀请消息
        Message message = Message.builder()
                .groupId(Long.valueOf(scheduleDTO.getGroupId()))
                .userId(Long.valueOf(scheduleDTO.getCreatorId()))
                .context("邀请你参加日程")
                .scheduleId(scheduleId)
                .type(0).build();

        messageService.sandMessageAsync(message, userIds);

        return true;
    }

    /**
     * 删除指定ID的调度信息及其关联的用户调度信息。
     *
     * @param id 调度信息的唯一标识符。
     * @return 始终返回true，表示删除成功。
     */
    @Override
    @Transactional
    public boolean delSchedule(String id, Long uid) {
        // 删除指定ID的日程信息
        Schedule schedule = scheduleMapper.selectById(id);
        if (schedule == null || !schedule.getCreatorId().equals(uid)) {
            // 只能由创建者删除日程
            return false;
        }
        scheduleMapper.deleteById(id);

        // 构建查询包装器，删除与该调度信息关联的所有用户调度关系
        LambdaQueryWrapper<ScheduleUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScheduleUser::getScheduleId, id);
        // 发送删除日程消息
        List<Long> ids = scheduleUserMapper.selectList(lqw)
                .stream().map(ScheduleUser::getUserId).collect(Collectors.toList());
        Message message = Message.builder()
                .groupId(schedule.getGroupId())
                .userId(schedule.getCreatorId())
                .context("删除了日程")
                .scheduleId(schedule.getId())
                .type(0).build();
        messageService.sandMessageAsync(message, ids);

        scheduleUserMapper.delete(lqw);

        return true;
    }

    @Override
    @Transactional
    public boolean updateSchedule(ScheduleDTO scheduleDTO) {
        // 构建日程
        Schedule schedule = scheduleMapper.selectById(scheduleDTO.getId());
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setType(scheduleDTO.getType());
        schedule.setDescription(scheduleDTO.getDescription());
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

        // 需要更新状态的用户列表
        ArrayList<Long> updateUserList = new ArrayList<>(userList);
        updateUserList.retainAll(userIds);


        if (!addUserList.isEmpty()) {
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

            // 对新增的用户发送邀请消息
            Message inviteMessage = Message.builder()
                    .groupId(schedule.getGroupId())
                    .userId(schedule.getCreatorId())
                    .context("邀请你参加日程")
                    .scheduleId(scheduleId)
                    .type(0).build();
            messageService.sandMessageAsync(inviteMessage, addUserList);
        }

        if (!delUserList.isEmpty()) {
            // 删除用户
            int deleted = scheduleUserMapper.delete(new LambdaQueryWrapper<ScheduleUser>()
                    .eq(ScheduleUser::getScheduleId, scheduleId)
                    .in(ScheduleUser::getUserId, delUserList));

            // 发送移出日程消息
            Message delMessage = Message.builder()
                    .groupId(schedule.getGroupId())
                    .userId(schedule.getCreatorId())
                    .context("将你移出了日程")
                    .scheduleId(scheduleId)
                    .type(0).build();
            messageService.sandMessageAsync(delMessage, delUserList);
        }

        if (!updateUserList.isEmpty()) {
            // 更新用户参与状态
            LambdaUpdateWrapper<ScheduleUser> updateWrapper = Wrappers.<ScheduleUser>lambdaUpdate()
                    .set(ScheduleUser::getJoinStatus, 0)
                    .in(ScheduleUser::getUserId, updateUserList);
            scheduleUserMapper.update(null, updateWrapper);

            // 发出更新日程消息
            Message message = Message.builder()
                    .groupId(schedule.getGroupId())
                    .userId(schedule.getCreatorId())
                    .context("修改了日程，并且将你的日程参与状态改为：待定")
                    .scheduleId(scheduleId)
                    .type(0).build();
            messageService.sandMessageAsync(message, updateUserList);
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
            if (schedule == null) {
                continue;
            }
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
        LocalDateTime newScheduleStartTime = newSchedule.getStartTime();
        LocalDateTime newScheduleEndTime = newSchedule.getEndTime();
        for (Schedule schedule : schedules) {
            LocalDateTime startTime = schedule.getStartTime();
            LocalDateTime endTime = schedule.getEndTime();
            if (newScheduleStartTime.isBefore(endTime) && newScheduleEndTime.isAfter(startTime)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 加入日程---强制
     * @param scheduleId
     * @param uid
     * @return
     */
    @Override
    @Transactional
    public boolean joinSchedule(Long scheduleId, Long uid) {
        // 查数据库对象(检验请求正确)
        LambdaQueryWrapper<ScheduleUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScheduleUser::getScheduleId, scheduleId);
        lqw.eq(ScheduleUser::getUserId, uid);
        ScheduleUser scheduleUser = scheduleUserMapper.selectOne(lqw);
        if (scheduleUser == null) {
            return false;
        }

        scheduleUser.setJoinStatus(1);
        scheduleUserMapper.updateById(scheduleUser);
        return true;
    }

    @Override
    @Transactional
    public boolean refuseOrTentativeSchedule(ScheduleUser scheduleUserDTO) {
        Integer joinStatus = scheduleUserDTO.getJoinStatus();
        Long scheduleId = scheduleUserDTO.getScheduleId();
        Long userId = scheduleUserDTO.getUserId();

        // 查数据库对象(检验请求正确)
        LambdaQueryWrapper<ScheduleUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScheduleUser::getScheduleId, scheduleId);
        lqw.eq(ScheduleUser::getUserId, userId);
        ScheduleUser scheduleUser = scheduleUserMapper.selectOne(lqw);
        if (scheduleUser == null) {
            return false;
        }
        Schedule schedule = scheduleMapper.selectById(scheduleId);

        // 修改数据库
        Message message = Message.builder()
                .groupId(schedule.getGroupId())
                .userId(userId)
                .scheduleId(scheduleId)
                .type(0).build();
        if (joinStatus == null) {
            return false;
        } else if (joinStatus == 0) {
            scheduleUser.setJoinStatus(0);
            scheduleUser.setRefuseReason(null);
            message.setContext("将日程的参与状态改为：待定");
        } else if (joinStatus == 2) {
            scheduleUser.setJoinStatus(2);
            String refuseReason = scheduleUserDTO.getRefuseReason();
            if (StrUtil.isBlank(refuseReason)) {
                refuseReason = "未填写原因";
            }
            scheduleUser.setRefuseReason(refuseReason);
            message.setContext("拒绝了日程，原因：" + refuseReason);
        } else {
            return false;
        }
        scheduleUserMapper.updateById(scheduleUser);
        messageService.sandMessageAsync(message, Collections.singletonList(schedule.getCreatorId()));
        return true;
    }
}









