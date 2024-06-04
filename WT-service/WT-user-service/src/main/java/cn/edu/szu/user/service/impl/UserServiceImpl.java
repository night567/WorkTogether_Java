package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.feign.client.CompanyClient;
import cn.edu.szu.feign.pojo.UserDTO;
import cn.edu.szu.user.mapper.UserLoginMapper;
import cn.edu.szu.user.mapper.UserMapper;
import cn.edu.szu.user.pojo.LoginDTO;
import cn.edu.szu.user.pojo.domain.User;
import cn.edu.szu.user.pojo.domain.UserLogin;
import cn.edu.szu.user.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.edu.szu.common.utils.RedisConstants.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CompanyClient companyClient;

    @Override
    @Transactional
    public Result createAccount(LoginDTO loginDTO) {
        // 校验邮箱
        String email = loginDTO.getEmail();
        if (!RegexUtils.isEmail(email)) {
            return new Result(Code.SAVE_ERR, null, "邮箱格式错误/不支持该类邮箱");
        }

        // 检验用户是否存在
        if (getUserByEmail(email) != null) {
            return new Result(Code.SAVE_ERR, null, "账户已经存在");
        }

        // 校验验证码
        String cacheCode = stringRedisTemplate.opsForValue().getAndDelete(LOGIN_REGISTER_CODE_KEY + email);
        String code = loginDTO.getVerificationCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return new Result(Code.SAVE_ERR, null, "验证码错误");
        }

        // 生成用户账户
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(email);
        // 密码加盐
        String salt = RandomUtil.randomBytes(16).toString();
        String pwd = DigestUtils.md5DigestAsHex((loginDTO.getPassword() + salt).getBytes());
        userLogin.setSalt(salt);
        userLogin.setPassword(pwd);
        userLoginMapper.insert(userLogin);
        // 生成用户信息
        User user = new User();
        user.setId(userLogin.getId());
        user.setEmail(email);
        user.setName(email);
        user.setCreateTime(new Date());
        user.setSex(3);
        userMapper.insert(user);

        // 保存用户登录信息
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);
        System.out.println(user);
        Map<String, Object> userMap = BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> {
                            if(fieldValue != null) {
                                return fieldValue.toString();
                            } else {
                                return null;
                            }
                        }));
        String key = LOGIN_USER_KEY + userLogin.getId().toString();
        stringRedisTemplate.opsForHash().putAll(key, userMap);
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);

        //生成token
        String token = JwtUtil.getToken(userLogin.getId());
        System.out.println(user + " " + token);
        return new Result(Code.SAVE_OK, token, "登录成功");
    }

    @Override
    @Transactional
    public Result login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        if (!RegexUtils.isEmail(email)) {
            return new Result(Code.SAVE_ERR, null, "邮箱格式错误/不支持该类邮箱");
        }

        // 检查账户是否存在
        LambdaQueryWrapper<UserLogin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserLogin::getEmail, email);
        UserLogin userLogin = userLoginMapper.selectOne(lqw);
        if (userLogin == null) {
            return new Result(Code.SAVE_ERR, null, "账户不存在");
        }

        // 比对密码
        String salt = userLogin.getSalt();
        String password = loginDTO.getPassword();
        String pwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if (!pwd.equals(userLogin.getPassword())) {
            return new Result(Code.SAVE_ERR, null, "密码错误");
        }

        // 储存user
        User user = userMapper.selectById(userLogin.getId());
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);
        Map<String, Object> userMap = BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> {
                            if(fieldValue != null) {
                                return fieldValue.toString();
                            } else {
                                return null;
                            }
                        }));
        String key = LOGIN_USER_KEY + userLogin.getId().toString();
        stringRedisTemplate.opsForHash().putAll(key, userMap);
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 生成jwt
        String token = JwtUtil.getToken(userLogin.getId());
        return new Result(Code.SAVE_OK, token, "登录成功");
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
        return userMapper.selectOne(lqw);
    }

    @Override
    public UserDTO getById(Long id) {
        if (id == null) {
            return null;
        }
        User user = userMapper.selectById(id);
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public List<User> getUserByCompany(Long id) {
        List<Long> user_ids = companyClient.selectUserIdsByCID(id);
        return userMapper.selectBatchIds(user_ids);
    }

    @Override
    public boolean updateById(User user) {
        if (user.getId() == null) {
            return false;
        }
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean updateUserInfo(String name, String phone, Long userId,String avatar) {
        return userMapper.updateUserInfo(name,phone,userId,avatar)>0;
    }


}
