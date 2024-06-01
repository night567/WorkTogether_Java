package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.MemberDTO;
import cn.edu.szu.company.pojo.domain.GroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select gu.user_id as id,g.name as groupName,t.type as position,gu.join_time as joinTime from wt_group_user as gu ,wt_group as g,wt_type as t " +
            "where gu.group_id = g.id and gu.group_id = #{id} and t.id = gu.type and gu.is_deleted = 0")
    List<MemberDTO> selectByGroupId(Long id);

    @Update("UPDATE wt_group_user set is_deleted = 1 where user_id = #{uid} and group_id = #{gid}")
    int delMemberFromGroup(Long uid,Long gid);

    @Update("UPDATE wt_group_user set type = #{type} where user_id = #{uid} and group_id = #{gid}")
     int updateMember(Long uid,Long gid,Long type);
    @Select("select type from wt_type")
    List<String> selectPosition();

    @Select("select id from wt_type where type = #{position}" )
    Long selectPositionId(String position);

    @Select("select type from wt_type where id=#{id}")
    String selectPositionById(Long id);

}




