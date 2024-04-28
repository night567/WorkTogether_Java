package cn.edu.szu.company.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门
 *
 * @TableName wt_department
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wt_department")
public class Department implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "parent_id")
    private Long parent_id; // 父部门ID

    @TableField(value = "company_id")
    private Long company_id; // 企业ID

    @TableField(value = "name")
    private String name; // 部门名称

    @TableField(value = "manager_id")
    private Long manager_id; // 负责人ID

    @TableField(value = "describe")
    private String describe; // 介绍

    @TableField(value = "create_time")
    private Date create_time; // 创建时间

    @TableField(value = "is_deleted")
    private Boolean is_deleted; // 逻辑删除
}