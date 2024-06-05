package cn.edu.szu.teamwork.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class ScheduleUserInfo implements Serializable {

    private Long id; // ID
    private Long scheduleId; // 日程ID
    private Long userId; // 用户ID
    private String name; // 用户名称
    private Integer joinStatus; // 是否参加(0:暂定, 1:接受, 2:拒绝)
    private String refuseReason; // 拒绝理由
    private Boolean isDeleted; // 是否删除
    private String avatar;// 参与者头像
}