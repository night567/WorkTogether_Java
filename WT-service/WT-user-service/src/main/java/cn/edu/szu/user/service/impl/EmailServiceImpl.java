package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.user.service.EmailService;
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
    /**
     *
     * @param email
     * @param type 0代表登录，1代表邀请码
     * @return
     */
    @Override
    public boolean sendVerificationCode(String email,Integer type,Long companyId) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        String code=null;
        //生成验证码
        if(type==0)
        code = RandomUtil.randomNumbers(6);
        else if (type==1) {
             code = RandomUtil.randomNumbers(6)+companyId.toString();
        }

        //保存验证码到redis(10分钟有效期)
        if(type==0)
            stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        else if(type==1)
            stringRedisTemplate.opsForValue().set(INVITE_CODE_KEY + email, code,LOGIN_USER_TTL, TimeUnit.MINUTES);
        else
            return false;
        //TODO:发送验证码
        System.out.println(email + "的验证码：" + code);
        return true;
    }
    public void sendVerificationCodeEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2839468956@qq.com");
        message.setTo(email);
        message.setSubject("验证码");
        message.setText("您的验证码是：" + code);
        javaMailSender.send(message);
    }
}
