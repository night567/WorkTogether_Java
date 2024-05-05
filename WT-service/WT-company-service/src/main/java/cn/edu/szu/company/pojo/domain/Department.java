package cn.edu.szu.company.pojo.domain;

import cn.edu.szu.company.pojo.DeptDTO;
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
    private Long parentId; // 父部门ID

    @TableField(value = "company_id")
    private Long companyId; // 企业ID

    @TableField(value = "name")
    private String name; // 部门名称

    @TableField(value = "num")
    private Long num; // 部门人数

    @TableField(value = "manager_id")
    private Long managerId; // 负责人ID

    @TableField(value = "introduction")
    private String introduction; // 介绍

    @TableField(value = "create_time")
    private Date createTime; // 创建时间

    @TableField(value = "is_deleted")
    private Boolean isDeleted; // 逻辑删除

    @TableField(value = "job")
    private Integer job; // 待分配任务数


    public Department(DeptDTO department) {
        this.id=department.getId();
        this.companyId=department.getCompanyId();
        this.parentId=department.getParentId();
        this.name=department.getName();
        this.managerId=department.getManagerId();
        this.introduction=department.getIntroduction();
        this.createTime=department.getCreateTime();
        this.job=department.getJob();
        this.num=department.getNum();
    }
}