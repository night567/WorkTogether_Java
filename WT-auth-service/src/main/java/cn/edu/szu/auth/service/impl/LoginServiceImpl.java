package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.domain.LoginFormDTO;
import cn.edu.szu.auth.service.LoginService;
import cn.edu.szu.common.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String createUser(LoginFormDTO loginForm) {
        // 校验邮箱
        String email = loginForm.getEmail();
        if (!RegexUtils.isEmail(email)) {
            return "";
        }

        //校验验证码
        String cacheCode = stringRedisTemplate.opsForValue().get("login:code:" + email);
        String code = loginForm.getVerificationCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return "";
        }

        //生成用户


        //生成token


        //返回token
        return "";
    }
}
