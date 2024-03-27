package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.domain.AuthAuthority;
import cn.edu.szu.auth.domain.AuthRoleAuthorityList;

import cn.edu.szu.auth.expection.RoleNotFoundException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.AuthRoleAuthority;
import cn.edu.szu.auth.service.AuthRoleAuthorityService;
import cn.edu.szu.auth.mapper.AuthRoleAuthorityMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    AuthRoleAuthorityMapper roleAuthorityMapper;

    /**
     * 添加角色-权限
     * @param authorityList
     */
    @Transactional
    @Override
    public void addRoleAuthority(AuthRoleAuthorityList authorityList) {
        AuthRoleAuthority authority = new AuthRoleAuthority();
//        拷贝创建人,角色id信息
        BeanUtils.copyProperties(authorityList,authority);
        authority.setCreateTime(new Date());
        List<AuthAuthority> list = authorityList.getAuthorities();
        List<AuthRoleAuthority> authList = new ArrayList<>();
        if (list != null){
            for (AuthAuthority a : list){
                AuthRoleAuthority authorityTemp = new AuthRoleAuthority();
                BeanUtils.copyProperties(authority,authorityTemp);
                BeanUtils.copyProperties(a,authorityTemp);
                authList.add(authorityTemp);
            }
        }
        try {
            saveBatch(authList);
        }catch (Exception e){
            throw new RoleNotFoundException("Role not found with Role id: " + authorityList.getRoleId());
        }

    }

    /**
     * 根据id删除角色权限
     * @param id
     */
    @Override
    public void delRoleAuthorityById(Long id) {
        int cnt = roleAuthorityMapper.deleteById(id);
        if (cnt == 0){
            throw new RoleNotFoundException("Role not found with Role id: " + id);
        }
    }

    /**
     * 根据ids批量删除id
     * @param ids
     */
    @Override
    public void delRoleAuthorityByIds(List<Long> ids) {
        roleAuthorityMapper.deleteBatchIds(ids);
    }

    /**
     * 根据角色id删除
     * @param id
     */
    @Override
    public void deleteByRoleId(Long id) {
        List<Long> ids = selectIdsByAuthId(id);
        delRoleAuthorityByIds(ids);
    }

    @Override
    public List<AuthRoleAuthority> getByRoleId(Long id) {
        return roleAuthorityMapper.selectByRoleId(id);
    }

    /**
     * 根据角色id查询所有的id
     * @param id
     * @return
     */
    public List<Long> selectIdsByAuthId(Long id) {
        return roleAuthorityMapper.selectIdsByRoleId(id);
    }


}




