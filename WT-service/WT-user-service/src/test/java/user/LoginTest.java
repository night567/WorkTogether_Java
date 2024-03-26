package user;

import cn.edu.szu.user.pojo.LoginForm;
import cn.edu.szu.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class LoginTest {
    @Autowired
    private UserService userService;

    @Test
    void testLogin() {
        String s = DigestUtils.md5DigestAsHex(("passw0rd" + "123321").getBytes());
        System.out.println(s);

        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("2484101884@qq.com");
        loginForm.setPassword("passw0rd");
        String token = userService.login(loginForm);
        System.out.println(token);
    }
}
