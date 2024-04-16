package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.exception.NameConflictException;

import cn.edu.szu.auth.exception.RoleNotFoundException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.service.AuthRoleService;
import cn.edu.szu.auth.mapper.AuthRoleMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 86199
* @description 针对表【wt_auth_role(角色)】的数据库操作Service实现
* @createDate 2024-03-26 00:42:02
*/
@Service
@Slf4j
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
        // 检查是否存在名称冲突
        boolean nameConflict = checkNameConflict(role.getName());
        if (nameConflict) {
            throw new NameConflictException("Role name already exists: " + role.getName());
        }
        role.setCreateTime(new Date());
        role.setStatus(true);
        role.setReadonly(false);
        roleMapper.insert(role);
    }

    /**
     * 检查是否有重复名字
     * @param roleName
     * @return
     */
    public boolean checkNameConflict(String roleName) {
        QueryWrapper<AuthRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", roleName);
        int count = roleMapper.selectCount(queryWrapper);
        return count > 0;
    }


    /**
     * 删除角色
     * @param id
     */
    @Override
    public void delRole(Long id) {

        int cnt = roleMapper.deleteById(id);
        if(cnt == 0){
            throw new RoleNotFoundException("Role not found with id: " + id);
        }

    }

    /**
     * 修改角色   名称等
     * @param role
     */
    @Override
    public void updateRole(AuthRole role) {
        int cnt = roleMapper.updateById(role);
//        log.info("ttttttttt");
//        System.out.println(cnt+" -------------------------------  ");
        log.info("lll:"+cnt);
        if (cnt == 0){
            throw new RoleNotFoundException("Role not found with id: " + role.getId());
        }
    }

    /**
     * 返回所有角色
     */
    @Override
    public List<AuthRole> selectAllRole(Long id) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("company_id", id);
        return roleMapper.selectByMap(conditionMap);
    }

    /**
     * 按照id获取角色
     * @param id
     */
    @Override
    public AuthRole selectById(Long id) {

        AuthRole role = roleMapper.selectById(id);
        if (role == null){
            throw new RoleNotFoundException("Role not found with id: " + id);
        }
        return role;

    }


}




