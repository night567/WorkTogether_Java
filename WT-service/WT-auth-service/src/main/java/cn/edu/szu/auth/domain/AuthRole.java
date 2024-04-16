package cn.edu.szu.auth.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色
 * @TableName wt_auth_role
 */
@TableName(value ="wt_auth_role")
@Data
public class AuthRole implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 功能描述
     */
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Boolean status;

    /**
     * 是否内置角色
     */
    @TableField(value = "readonly")
    private Boolean readonly;

    /**
     * 创建人id
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;



    @TableField(value = "company_id")
    private Long companyId;

    @TableField(value = "is_deleted")
    @TableLogic(value = "0",delval = "1")
    private boolean isDeleted;

}