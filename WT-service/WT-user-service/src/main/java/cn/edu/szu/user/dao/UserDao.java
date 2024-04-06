package cn.edu.szu.user.dao;

import cn.edu.szu.user.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
