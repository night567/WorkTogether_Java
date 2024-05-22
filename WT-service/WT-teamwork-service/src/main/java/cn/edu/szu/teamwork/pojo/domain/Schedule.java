package cn.edu.szu.teamwork.pojo.domain;

import cn.edu.szu.teamwork.pojo.ScheduleDTO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程
 * @TableName wt_schedule
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wt_schedule")
public class Schedule implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id; // ID

    @TableField(value = "group_id")
    private Long groupId; // 所属团队ID

    @TableField(value = "creator_id")
    private Long creatorId; // 创建者ID

    @TableField(value = "title")
    private String title; // 标题

    @TableField(value = "start_time")
    private LocalDateTime startTime; // 开始时间

    @TableField(value = "end_time")
    private LocalDateTime endTime; // 结束时间

    @TableField(value = "type")
    private Integer type; // 日程类型

    @TableField(value = "description")
    private String description; // 描述

    @TableField(value = "is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Boolean isDeleted; // 是否删除
}