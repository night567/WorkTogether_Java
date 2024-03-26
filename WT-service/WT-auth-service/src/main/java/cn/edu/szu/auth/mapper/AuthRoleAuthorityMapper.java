package cn.edu.szu.auth.mapper;

import cn.edu.szu.auth.domain.AuthRoleAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86199
* @description 针对表【wt_auth_role_authority(角色的资源)】的数据库操作Mapper
* @createDate 2024-03-26 01:02:00
* @Entity cn.edu.szu.auth.domain.AuthRoleAuthority
*/
@Mapper
public interface AuthRoleAuthorityMapper extends BaseMapper<AuthRoleAuthority> {

    @Select("select id from wt_auth_role_authority where authority_id = #{authorityId}")
    List<Long> selectIdsByRoleId(Long authorityId);

}




