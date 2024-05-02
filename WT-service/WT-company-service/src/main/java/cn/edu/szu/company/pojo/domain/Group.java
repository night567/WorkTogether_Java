package cn.edu.szu.company.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团队
 *
 * @TableName wt_group
 */
@Data
@TableName(value = "wt_group")
public class Group implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "company_id")
    private Long companyId; // 所属企业ID

    @TableField(value = "name")
    private String name; // 部门名称

    @TableField(value = "manager_id")
    private Long managerId; // 负责人ID

    @TableField(value = "description")
    private String description; // 介绍

    @TableField(value = "member_num")
    private Integer memberNum; // 团队人数

    @TableField(value = "dept_num")
    private Integer deptNum; // 包含部门数量

    @TableField(value = "create_time")
    private Date createTime; // 创建时间

    @TableField(value = "is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Boolean isDeleted; // 逻辑删除
}