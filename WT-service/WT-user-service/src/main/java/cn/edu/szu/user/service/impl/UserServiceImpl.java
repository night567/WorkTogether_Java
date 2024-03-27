package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.user.dao.UserDao;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.pojo.UserDTO;
import cn.edu.szu.user.service.UserService;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String createAccount(LoginDTO loginDTO) {
        // 校验邮箱
        String email = loginDTO.getEmail();
        if (!RegexUtils.isEmail(email)) {
            return "";
        }

        // 检验用户是否存在
        if (getUserByEmail(email) != null) {
            return "";
        }

        // 校验验证码
        String cacheCode = stringRedisTemplate.opsForValue().get("login:code:" + email);
        String code = loginDTO.getVerificationCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return "";
        }

        // 生成用户
        User user = new User();
        user.setEmail(email);
        user.setName(email);
        // 密码加盐
        String salt = RandomUtil.randomBytes(16).toString();
        System.out.println(salt + ", " + salt.length());
        String pwd = DigestUtils.md5DigestAsHex((loginDTO.getPassword() + salt).getBytes());
        user.setSalt(salt);
        user.setPassword(pwd);
        user.setStatus(true);
        user.setCreateTime(LocalDateTime.now());
        userDao.insert(user);

        //生成token
        System.out.println(user.getId());
        return JwtUtil.getToken(user.getId());
    }

    @Override
    public String login(LoginDTO loginDTO) {
        // 检查账户是否存在
        String email = loginDTO.getEmail();
        User user = getUserByEmail(email);
        if (user == null) {
            return "";
        }

        // 比对密码
        String salt = user.getSalt();
        String password = loginDTO.getPassword();
        String pwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if (!pwd.equals(user.getPassword())) {
            return "";
        }

        // 生成jwt
        return JwtUtil.getToken(user.getId());
    }

    @Override
    public User getUserByEmail(String email) {
        // 检验邮箱正确性
        if (!RegexUtils.isEmail(email)) {
            return null;
        }

        // 查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, email);
        return userDao.selectOne(lqw);
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            return null;
        }

        return userDao.selectById(id);
    }

    @Override
    public List<UserDTO> getUserByCompany(Long id) {
        List<User> users = userDao.selectList(null);
        return users.stream().map(User::parseToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean updateById(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setStatus(userDTO.getStatus());
        return userDao.updateById(user) > 0;
    }
}
