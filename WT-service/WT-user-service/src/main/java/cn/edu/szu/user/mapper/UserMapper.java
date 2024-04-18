package cn.edu.szu.user.mapper;

import cn.edu.szu.user.pojo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zgr24
 * @description 针对表【wt_user(用户)】的数据库操作Mapper
 * @createDate 2024-04-14 20:08:11
 * @Entity cn.edu.szu.user.domain.WtUser
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}




