package cn.edu.szu.company.mapper;

import cn.edu.szu.company.pojo.domain.GroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zgr24
* @description 针对表【wt_group_user(团队成员)】的数据库操作Mapper
* @createDate 2024-04-28 16:39:41
* @Entity cn.edu.szu.company.pojo.domain.GroupUser
*/
@Mapper
public interface GroupUserMapper extends BaseMapper<GroupUser> {

}




