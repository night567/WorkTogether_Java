package cn.edu.szu.user.service;

import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.pojo.UserDTO;

import java.util.List;

public interface UserService {
    String createAccount(LoginDTO loginDTO);

    String login(LoginDTO loginDTO);

    User getUserByEmail(String email);

    User getById(Long id);

    List<UserDTO> getUserByCompany(Long id);

    boolean updateById(UserDTO userDTO);
}
