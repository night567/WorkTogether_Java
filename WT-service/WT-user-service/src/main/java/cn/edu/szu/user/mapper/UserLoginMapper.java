package cn.edu.szu.user.mapper;

import cn.edu.szu.user.pojo.domain.UserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zgr24
 * @description 针对表【wt_user_login】的数据库操作Mapper
 * @createDate 2024-04-14 20:57:51
 * @Entity cn.edu.szu.user.domain.WtUserLogin
 */
@Mapper
public interface UserLoginMapper extends BaseMapper<UserLogin> {
}




