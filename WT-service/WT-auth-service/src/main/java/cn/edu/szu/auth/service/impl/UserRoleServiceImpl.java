package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.domain.UserRoleListDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.szu.auth.domain.UserRole;
import cn.edu.szu.auth.service.UserRoleService;
import cn.edu.szu.auth.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    public boolean saveRoleToUser(UserRoleListDTO userRoleList) {
        Long userId = userRoleList.getUserId();
        LambdaQueryWrapper<UserRole> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserRole::getUserId, userId);
        userRoleMapper.delete(lqw);

        Long createUser = userRoleList.getCreateUser();
        userRoleList.getRoleIds().stream()
                .map(id -> UserRole.builder()
                        .userId(userId)
                        .roleId(id)
                        .createUser(createUser)
                        .createTime(LocalDateTime.now())
                        .build())
                .forEach(userRoleMapper::insert);
        return true;
    }
}




