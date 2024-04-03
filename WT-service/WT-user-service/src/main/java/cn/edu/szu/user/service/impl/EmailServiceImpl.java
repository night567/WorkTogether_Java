package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.user.service.EmailService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static cn.edu.szu.common.utils.RedisConstants.LOGIN_CODE_KEY;
import static cn.edu.szu.common.utils.RedisConstants.LOGIN_CODE_TTL;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendVerificationCode(String email) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        //生成验证码
        String code = RandomUtil.randomNumbers(6);

        //保存验证码到redis(10分钟有效期)
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        //TODO:发送验证码
        System.out.println(email + "的验证码：" + code);

        return true;
    }
}
