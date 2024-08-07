package cn.edu.szu.user.service;

import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.domain.User;

import java.util.List;

public interface UserService {
    Result createAccount(LoginDTO loginDTO);
    List<String> getIdsByName(String name);
    Result login(LoginDTO loginDTO);

    User getUserByEmail(String email);

    UserDTO getById(Long id);

    List<User> getUserByCompany(Long id, Integer pageNum, Integer pageSize);

    boolean updateById(User user);
    String getAvatarById(String id);
    boolean updateUserInfo(String name, String phone, Long userId, String avatar);
}
