package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.AuthRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86199
* @description 针对表【wt_auth_role(角色)】的数据库操作Service
* @createDate 2024-03-26 00:42:02
*/
public interface AuthRoleService extends IService<AuthRole> {
    /**
     * 添加角色
     * @param role
     */
    void addRole(AuthRole role);

    /**
     * 删除角色
     * @param id
     */
    void delRole(Long id);

    /**
     * 修改角色
     * @param role
     */
    void updateRole(AuthRole role);

    /**
     * 查询公司所有角色
     */
    List<AuthRole> selectAllRole(Long id);

    /**
     * 根据id查询角色
     * @param id
     */
    AuthRole selectById(Long id);

}
