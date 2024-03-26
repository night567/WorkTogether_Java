package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.AuthAuthority;
import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.domain.AuthRoleAuthorityList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86199
* @description 针对表【wt_auth_role_authority(角色的资源)】的数据库操作Service
* @createDate 2024-03-26 01:02:00
*/
public interface AuthRoleAuthorityService extends IService<AuthRoleAuthority> {
    /**
     * 添加角色-权限
     * @param authorityList
     */
    void addRoleAuthority(AuthRoleAuthorityList authorityList);
    /**
     * 根据id删除角色权限
     * @param id
     */
    void delRoleAuthorityById(Long id);
    /**
     * 根据ids批量删除id
     * @param ids
     */
    void delRoleAuthorityByIds(List<Long> ids);

    void deleteByRoleId(Long id);

}
