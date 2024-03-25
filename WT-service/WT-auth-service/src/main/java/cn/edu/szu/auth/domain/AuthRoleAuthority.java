package cn.edu.szu.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色的资源
 * @TableName wt_auth_role_authority
 */
@TableName(value ="wt_auth_role_authority")
@Data
public class AuthRoleAuthority implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 权限id
#c_auth_resource
#c_auth_menu
     */
    @TableField(value = "authority_id")
    private Long authorityId;

    /**
     * 权限类型
#AuthorizeType{MENU:菜单;RESOURCE:资源;}
     */
    @TableField(value = "authority_type")
    private String authorityType;

    /**
     * 角色id
#c_auth_role
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private Long createUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}