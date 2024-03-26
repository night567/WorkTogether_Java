package cn.edu.szu.user.service;

import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.User;

public interface UserService {
    String createAccount(LoginDTO loginDTO);

    String login(LoginDTO loginDTO);

    User getUserByEmail(String email);

    User getById(Long id);
}
