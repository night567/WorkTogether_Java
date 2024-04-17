package cn.edu.szu.user.service;

import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.User;

import java.util.List;

public interface UserService {
    Result createAccount(LoginDTO loginDTO);

    Result login(LoginDTO loginDTO);

    User getUserByEmail(String email);

    UserDTO getById(Long id);

    List<User> getUserByCompany(Long id);

    boolean updateById(User user);
}
