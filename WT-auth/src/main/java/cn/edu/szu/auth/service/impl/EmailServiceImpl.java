package cn.edu.szu.auth.service.impl;

import cn.edu.szu.auth.service.EmailService;
import cn.edu.szu.common.utils.RegexUtils;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
        stringRedisTemplate.opsForValue().set("login:code:" + email, code, 10, TimeUnit.MINUTES);

        //TODO:发送验证码
        System.out.println(email + "的验证码：" + code);

        return true;
    }
}
