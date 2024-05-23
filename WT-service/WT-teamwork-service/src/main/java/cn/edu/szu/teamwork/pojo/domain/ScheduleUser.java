package cn.edu.szu.teamwork.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 日程参与者
 * @TableName wt_schedule_user
 */
@TableName(value = "wt_schedule_user")
@Data
public class ScheduleUser implements Serializable {
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id; // ID

    @TableField(value = "schedule_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long scheduleId; // 日程ID

    @TableField(value = "user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId; // 用户ID

    @TableField(value = "join_status")
    private Integer joinStatus; // 是否参加(0:暂定, 1:接受, 2:拒绝)

    @TableField(value = "refuse_reason", updateStrategy = FieldStrategy.IGNORED)
    private String refuseReason; // 拒绝理由

    @TableField(value = "is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Boolean isDeleted; // 是否删除
}