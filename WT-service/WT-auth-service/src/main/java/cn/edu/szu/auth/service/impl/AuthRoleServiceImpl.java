package cn.edu.szu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.service.AuthRoleService;
import cn.edu.szu.auth.mapper.AuthRoleMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
* @author 86199
* @description 针对表【wt_auth_role(角色)】的数据库操作Service实现
* @createDate 2024-03-26 00:42:02
*/
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole>
    implements AuthRoleService{

    @Autowired
    private AuthRoleMapper roleMapper;

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void addRole(AuthRole role) {
        role.setCreateTime(new Date());
        role.setStatus(true);
        role.setReadonly(false);
        roleMapper.insert(role);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void delRole(Long id) {
        roleMapper.deleteById(id);
    }

    /**
     * 修改角色   名称等
     * @param role
     */
    @Override
    public void updateRole(AuthRole role) {
        roleMapper.updateById(role);
    }

    /**
     * 返回所有角色
     */
    @Override
    public List<AuthRole> selectAllRole() {
        return roleMapper.selectList(null);
    }

    /**
     * 按照id获取角色
     * @param id
     */
    @Override
    public AuthRole selectById(Long id) {
        return roleMapper.selectById(id);
    }


}




