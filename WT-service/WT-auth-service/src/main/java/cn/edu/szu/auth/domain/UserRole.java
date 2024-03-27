package cn.edu.szu.auth.domain;

import cn.edu.szu.auth.service.UserRoleService;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 角色分配 账号角色绑定
 *
 * @TableName wt_auth_user_role
 */

@Data
@Builder
@TableName(value = "wt_auth_user_role")
public class UserRole implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 创建人ID
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
}