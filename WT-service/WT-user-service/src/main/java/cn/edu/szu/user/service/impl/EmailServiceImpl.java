package cn.edu.szu.user.service.impl;

import cn.edu.szu.common.utils.RegexUtils;
import cn.edu.szu.user.pojo.User;
import cn.edu.szu.user.service.EmailService;
import cn.edu.szu.user.service.UserService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
     * 生成注册验证码
     * @param email 邮箱
     * @return 验证码
     */
    @Override
    public String generateRegistrationCode(String email) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return null;
        }

        //检查账号是否已经存在
        if (userService.getUserByEmail(email) != null) {
            return null;
        }

        //生成验证码
        String code = RandomUtil.randomNumbers(6);

        //保存验证码到redis(10分钟有效期)
        stringRedisTemplate.opsForValue().set(LOGIN_REGISTER_CODE_KEY + email, code, LOGIN_REGISTER_CODE_TTL, TimeUnit.MINUTES);

        return code;
    }

    /**
     * 初步检查注册验证码（不会将redis的验证码删除）
     * @param email 邮箱
     * @param code 验证码
     * @return 验证结果
     */
    @Override
    public boolean checkRegistrationCode(String email, String code) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        //检查验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_REGISTER_CODE_KEY + email);
        return cacheCode != null && cacheCode.equals(code);
    }

    /**
     * 生成账号验证码
     * @param email 邮箱
     * @return 验证码
     */
    @Override
    public String generateVerificationCode(String email) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return null;
        }

        //检查账号是否存在
        if (userService.getUserByEmail(email) == null) {
            return null;
        }

        //生成验证码
        String code = RandomUtil.randomNumbers(6);

        //保存验证码到redis(10分钟有效期)
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        return code;
    }

    /**
     * 初步检查账号验证码（不会将redis的验证码删除）
     * @param email 邮箱
     * @param code 验证码
     * @return 验证结果
     */
    @Override
    public boolean checkVerificationCode(String email, String code) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        //检查验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        return cacheCode != null && cacheCode.equals(code);
    }

    @Override
    public boolean sendInviteCode(String email, Long companyId) {
        //验证邮箱
        if (!RegexUtils.isEmail(email)) {
            return false;
        }

        String code = null;
        //生成验证码
        code = RandomUtil.randomNumbers(6);
        //获取用户ID
        User user = userService.getUserByEmail(email);

        //保存验证码到redis(10分钟有效期)
        stringRedisTemplate.opsForHash().put(INVITE_CODE_KEY + code, "email", email);
        stringRedisTemplate.opsForHash().put(INVITE_CODE_KEY + code, "companyId", companyId.toString());
        stringRedisTemplate.expire(INVITE_CODE_KEY + code, INVITE_CODE_TTL, TimeUnit.MINUTES);

        //发送验证码
        sendCodeEmail(email, code);
        return true;
    }

    @Async //开启异步（异步调用必须在不同类中调用）
    public void sendCodeEmail(String email, String code) {
        System.out.println("开始发送");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2839468956@qq.com");
        message.setTo(email);
        message.setSubject("验证码");
        message.setText("您的验证码是：" + code);
        javaMailSender.send(message);
        System.out.println("发送成功");
    }
}
