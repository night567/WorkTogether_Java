package cn.edu.szu.teamwork.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 简报
 * @TableName wt_report
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wt_report")
public class Report implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId; // 提交人id

    @TableField(value = "reviewer_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reviewerId; // 评审人id

    @TableField(value = "year")
    private Integer year; // 年

    @TableField(value = "week_num")
    private Integer weekNum; // 周

    @TableField(value = "text1")
    private String text1; // 题目一文本

    @TableField(value = "text2")
    private String text2; // 题目二文本

    @TableField(value = "text3")
    private String text3; // 题目三文本

    @TableField(value = "text4")
    private String text4; // 题目四文本

    @TableField(value = "status")
    private Integer status; // 评审状态(0: 未评审，1：已评审)

    @TableField(value = "report_time")
    private LocalDateTime reportTime; // 提交时间
}