package cn.edu.szu.user.service;

import cn.edu.szu.user.pojo.User;

public interface UserService {
    User getUserByEmail(String email);

    User getById(Long id);
}
