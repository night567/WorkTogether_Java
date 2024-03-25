package cn.edu.szu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.service.AuthRoleAuthorityService;
import cn.edu.szu.auth.mapper.AuthRoleAuthorityMapper;
import org.springframework.stereotype.Service;

/**
* @author 86199
* @description 针对表【wt_auth_role_authority(角色的资源)】的数据库操作Service实现
* @createDate 2024-03-26 01:02:00
*/

/**
 * 修改角色拥有的权限
 */
@Service
public class AuthRoleAuthorityServiceImpl extends ServiceImpl<AuthRoleAuthorityMapper, AuthRoleAuthority>
    implements AuthRoleAuthorityService{

}




