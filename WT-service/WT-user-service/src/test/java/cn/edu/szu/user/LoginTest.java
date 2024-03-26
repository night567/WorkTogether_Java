package cn.edu.szu.user;

import cn.edu.szu.user.pojo.LoginDTO;
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

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("2484101884@qq.com");
        loginDTO.setPassword("passw0rd");
        String token = userService.login(loginDTO);
        System.out.println(token);
    }
}
