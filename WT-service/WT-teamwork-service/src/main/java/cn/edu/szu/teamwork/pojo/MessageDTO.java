package cn.edu.szu.teamwork.pojo;

import cn.edu.szu.feign.pojo.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id; // ID

    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId; // 所属团队ID

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId; // 执行操作的用户ID

    private UserDTO user; // 执行操作的用户详细信息

    private String context; // 通知内容

    private Integer type; // 通知类型(0:用户消息 1:日程系统消息)

    private Boolean isRead; // 是否已读

    private Boolean handleLater; // 是否设为稍后处理

    @JsonSerialize(using = ToStringSerializer.class)
    private Long scheduleId; // 关联日程ID

    private String scheduleTitle; // 关联日程名称

    private Integer scheduleType; // 关联日程类型

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 消息发送时间
}
