package cn.edu.szu.auth.mapper;

import cn.edu.szu.auth.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author zgr24
* @description 针对表【wt_auth_user_role(角色分配
账号角色绑定)】的数据库操作Mapper
* @createDate 2024-03-27 17:16:32
* @Entity cn.edu.szu.auth.domain.AuthUserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {
    public List<String> getAllResourceIdsByUserId(Long id, Long companyId);
}




