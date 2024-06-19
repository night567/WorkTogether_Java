package cn.edu.szu.teamwork.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wt_report")
public class ReportVO {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId; //用户id
    private String imgUrl;
    @TableField(value = "reviewer_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reviewerId; //评审人id

    @TableField(value = "year")
    private Long year; //年

    @TableField(value = "week_num")
    private Long weekNum; //周数

    @TableField(value = "text1")
    private String text1; //回答内容一

    @TableField(value = "text2")
    private String text2; //回答内容二

    @TableField(value = "text3")
    private String text3; //回答内容三

    @TableField(value = "text4")
    private String text4; //回答内容四

    @TableField(value = "status")
    private Long status; //评审状态(0: 未评审，1：已评审)

    @TableField(value = "report_time")
    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm", timezone = "GMT+8")
    private LocalDateTime reportTime; //提交时间
}
