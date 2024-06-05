package cn.edu.szu.teamwork.pojo;

import cn.edu.szu.teamwork.pojo.domain.Schedule;
import cn.edu.szu.teamwork.pojo.domain.ScheduleUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInfoDTO {
    private String id; // ID
    private String groupId; // 所属团队ID
    private String creatorId; // 创建者ID
    private String title; // 标题
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime; // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime; // 结束时间
    private Integer type; // 日程类型
    private String description; // 描述
    private List<ScheduleUserInfo> scheduleUsers; //参与者

    public ScheduleInfoDTO(Schedule schedule) {
        this.id = schedule.getId().toString();
        this.groupId = schedule.getGroupId().toString();
        if(schedule.getCreatorId()!=null)
            this.creatorId = schedule.getCreatorId().toString();
        this.title = schedule.getTitle();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.type = schedule.getType();
        this.description = schedule.getDescription();
    }
}
