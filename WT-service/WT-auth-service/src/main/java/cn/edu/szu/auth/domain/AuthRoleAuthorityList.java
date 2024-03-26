package cn.edu.szu.auth.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuthRoleAuthorityList {

    /**
     * 权限列表   一个角色的多个权限
     */
    List<AuthAuthority> authorities;

    /**
     * 角色id
     #c_auth_role
     */
    private Long roleId;


    /**
     * 创建人
     */
    private Long createUser;

}
