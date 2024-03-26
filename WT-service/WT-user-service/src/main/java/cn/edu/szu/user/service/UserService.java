package cn.edu.szu.user.service;

import cn.edu.szu.user.pojo.LoginForm;
import cn.edu.szu.user.pojo.User;

public interface UserService {
    String createAccount(LoginForm loginForm);

    String login(LoginForm loginForm);

    User getUserByEmail(String email);

    User getById(Long id);
}
