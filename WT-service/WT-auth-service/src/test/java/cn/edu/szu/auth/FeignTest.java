package cn.edu.szu.auth;

import cn.edu.szu.common.domain.Result;
import cn.edu.szu.feign.client.UserClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FeignTest {
    @Autowired
    private UserClient userClient;

    @Test
    void testGetUserByEmail() {
        Result result = userClient.getUserByEmail("2484101884@qq.com");
        System.out.println(result);
    }
}
