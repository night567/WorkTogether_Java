package cn.edu.szu.teamwork.pojo;

import cn.edu.szu.teamwork.pojo.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private String id; // ID
    private String groupId; // 所属团队ID
    private String creatorId; // 创建者ID
    private List<String> userIds; // 参与者ID
    private String title; // 标题
    private LocalDateTime startTime; // 开始时间
    private LocalDateTime endTime; // 结束时间
    private Integer type; // 日程类型
    private String description; // 描述

    public ScheduleDTO(Schedule schedule) {
        this.id = schedule.getId().toString();
        this.groupId = schedule.getGroupId().toString();
        this.creatorId = schedule.getCreatorId().toString();
        this.title = schedule.getTitle();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.type = schedule.getType();
        this.description = schedule.getDescription();
    }
}
