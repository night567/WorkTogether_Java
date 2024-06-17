package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.domain.AuthRole;
import cn.edu.szu.auth.domain.UserRole;
import cn.edu.szu.auth.domain.UserRoleListDTO;
import cn.edu.szu.auth.mapper.AuthResourceMapper;
import cn.edu.szu.auth.mapper.UserRoleMapper;
import cn.edu.szu.auth.service.UserRoleService;
import cn.edu.szu.feign.pojo.CheckAuthDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zgr24
 * @description 针对表【wt_auth_user_role(角色分配账号角色绑定)】的数据库操作Service实现
 * @createDate 2024-03-27 17:16:32
 */
@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private AuthResourceMapper authResourceMapper;

    @Override
    @Transactional
    public boolean saveRoleToUser(UserRoleListDTO userRoleList) {
        Long userId = userRoleList.getUserId();
        Long createUser = userRoleList.getCreateUser();
        List<Long> newRoleIds = userRoleList.getRoleIds();
        // 取得已有的角色
        List<Long> oldRoleIds = query().eq("user_id", userId).list()
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 删除需要去除的角色
        List<Long> deleteRoleIds = new ArrayList<>(oldRoleIds);
        deleteRoleIds.removeAll(newRoleIds);
        if (!deleteRoleIds.isEmpty()) {
            remove(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, userId)
                    .in(UserRole::getRoleId, deleteRoleIds));
        }

        // 添加新增的角色
        List<Long> addRoleIds = new ArrayList<>(newRoleIds);
        addRoleIds.removeAll(oldRoleIds);
        if (!addRoleIds.isEmpty()) {
            List<UserRole> addUserRole = addRoleIds.stream()
                    .map(id -> UserRole.builder()
                            .userId(userId)
                            .roleId(id)
                            .createUser(createUser)
                            .createTime(LocalDateTime.now())
                            .build())
                    .collect(Collectors.toList());
            saveBatch(addUserRole);
        }

        return true;
    }

    @Override
    public List<AuthRole> getRoleByUserId(Long userId, String companyId) {
        List<AuthRole> roleList = userRoleMapper.getRolesByUserId(userId, companyId);
        if (roleList == null) {
            return Collections.emptyList();
        }
        return roleList;
    }

    @Override
    public boolean checkAuth(CheckAuthDTO checkAuthDTO) {
        List<String> resources = userRoleMapper.getAllResourceIdsByUserId(checkAuthDTO.getUserId(), checkAuthDTO.getCompanyId());
//        resources.forEach(System.out::println);
        return resources.stream().anyMatch(checkAuthDTO.getResource()::startsWith);
    }
}




