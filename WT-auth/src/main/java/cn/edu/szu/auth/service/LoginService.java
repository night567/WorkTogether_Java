package cn.edu.szu.auth.service;

import cn.edu.szu.auth.domain.LoginFormDTO;

public interface LoginService {
    String createUser(LoginFormDTO loginForm);
}
