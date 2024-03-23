package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.LoginFormDTO;

public interface LoginService {
    String createAccount(LoginFormDTO loginForm);
    String login(LoginFormDTO loginFormDTO);
}
