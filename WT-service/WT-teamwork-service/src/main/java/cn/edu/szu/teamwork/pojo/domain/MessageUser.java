package cn.edu.szu.teamwork.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息接收者
 * @TableName wt_message_user
 */
@Data
@TableName(value = "wt_message_user")
public class MessageUser implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "message_id")
    private Long messageId; // 消息ID

    @TableField(value = "user_id")
    private Long userId; // 用户ID

    @TableField(value = "is_read")
    private Boolean isRead; // 是否已读

    @TableField(value = "handle_later")
    private Boolean handleLater; // 是否设为稍后处理
}