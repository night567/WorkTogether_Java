package cn.edu.szu.auth.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class AuthAuthority {

    /**
     * 权限id
     #c_auth_resource
     #c_auth_menu
     */
    private Long authorityId;
    /**     * 权限类型
     #AuthorizeType{MENU:菜单;RESOURCE:资源;}

     */
    private String authorityType;
}
