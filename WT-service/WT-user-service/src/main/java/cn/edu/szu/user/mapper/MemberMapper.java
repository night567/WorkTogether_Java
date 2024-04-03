package cn.edu.szu.user.mapper;

import cn.edu.szu.user.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper {
    /**
     *
     * @param user_ids 用户ID集合
     * @return 用户列表
     */
    List<User> selectUserByUserIds(List<Integer> user_ids);
}
