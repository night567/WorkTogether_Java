package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.UserRole;
import cn.edu.szu.auth.domain.UserRoleListDTO;
import cn.edu.szu.auth.domain.WtAuthMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zgr24
* @description 针对表【wt_auth_user_role(角色分配账号角色绑定)】的数据库操作Service
* @createDate 2024-03-27 17:16:32
*/
public interface UserRoleService extends IService<UserRole> {
    boolean saveRoleToUser(UserRoleListDTO userRoleList);
}
