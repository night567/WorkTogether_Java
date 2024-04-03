package cn.edu.szu.user.service;

import cn.edu.szu.user.pojo.User;

import java.util.List;

public interface MemberService {
    /**
     *
     * @param user_ids 用户ID集合
     * @return 用户列表
     */
    List<User> selectUserByUserIds(List<Integer> user_ids);
}
