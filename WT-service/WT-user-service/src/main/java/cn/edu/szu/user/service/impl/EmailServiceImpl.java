package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.EmailService;
import cn.edu.szu.user.service.UserService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.concurrent.TimeUnit;

import static cn.edu.szu.common.utils.RedisConstants.*;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;
    /**
     *
     * @param email
     * @param
     * @return
     */
    @Override
    public boolean sendVerificationCode(String email) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        String code=null;
        //生成验证码
        code = RandomUtil.randomNumbers(6);

        //保存验证码到redis(10分钟有效期)
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        //TODO:发送验证码
        sendCodeEmail(email,code);
        return true;
    }

    @Override
    public boolean sendInviteCode(String email, Long companyId) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        String code=null;
        //生成验证码
        code = RandomUtil.randomNumbers(6);
        //获取用户ID
        User user = userService.getUserByEmail(email);

        //保存验证码到redis(10分钟有效期)
        stringRedisTemplate.opsForHash().put(INVITE_CODE_KEY+code,"email",email);
        stringRedisTemplate.opsForHash().put(INVITE_CODE_KEY+code,"companyId",companyId);
        stringRedisTemplate.expire(INVITE_CODE_KEY+code,LOGIN_USER_TTL,TimeUnit.MINUTES);

        //TODO:发送验证码
        sendCodeEmail(email,code);
        return true;
    }
    public void sendCodeEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2839468956@qq.com");
        message.setTo(email);
        message.setSubject("验证码");
        message.setText("您的验证码是：" + code);
        javaMailSender.send(message);
    }
}
