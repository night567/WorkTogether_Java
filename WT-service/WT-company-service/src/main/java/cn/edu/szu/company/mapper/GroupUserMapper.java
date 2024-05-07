package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.GroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author zgr24
* @description 针对表【wt_group_user(团队成员)】的数据库操作Mapper
* @createDate 2024-04-28 16:39:41
* @Entity cn.edu.szu.company.pojo.domain.GroupUser
*/
@Mapper
public interface GroupUserMapper extends BaseMapper<GroupUser> {

    @Select("select gu.user_id as id,g.name as deptName from wt_group_user as gu ,wt_group as g " +
            "where gu.group_id = g.id and gu.group_id = #{id}")
    List<MemberDTO> selectByGroupId(Long id);

    @Insert(("insert into wt_group_user "))
    int addUserToGroup(Long uid, Long gid, Date date);

}




