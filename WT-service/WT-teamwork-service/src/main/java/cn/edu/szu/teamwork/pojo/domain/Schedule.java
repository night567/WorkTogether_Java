package cn.edu.szu.teamwork.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程
 * @TableName wt_schedule
 */
@Data
@TableName(value = "wt_schedule")
public class Schedule implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id; // ID

    @TableField(value = "group_id")
    private Long groupId; // 所属团队ID

    @TableField(value = "create_id")
    private Long createId; // 创建者ID

    @TableField(value = "title")
    private String title; // 标题

    @TableField(value = "startTime")
    private LocalDateTime startTime; // 开始时间

    @TableField(value = "endTime")
    private LocalDateTime endTime; // 结束时间

    @TableField(value = "type")
    private Integer type; // 日程类型

    @TableField(value = "description")
    private String description; // 描述

    @TableField(value = "is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Boolean isDeleted; // 是否删除
}