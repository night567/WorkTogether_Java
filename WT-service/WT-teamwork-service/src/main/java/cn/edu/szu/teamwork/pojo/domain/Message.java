package cn.edu.szu.teamwork.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息
 * @TableName wt_message
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wt_message")
public class Message implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id; // ID

    @TableField(value = "group_id")
    private Long groupId;

    @TableField(value = "user_id")
    private Long userId; // 执行操作的用户ID

    @TableField(value = "context")
    private String context; // 通知内容

    @TableField(value = "schedule_id")
    private Long scheduleId; // 关联日程ID

    @TableField(value = "type")
    private Integer type; // 通知类型(0:用户消息 1:日程系统消息)

    @TableField(value = "create_time")
    private LocalDateTime createTime; // 消息发送时间

    public Message(Long groupId, Long userId, String context, Long scheduleId, Integer type) {
        this.groupId = groupId;
        this.userId = userId;
        this.context = context;
        this.scheduleId = scheduleId;
        this.type = type;
    }
}