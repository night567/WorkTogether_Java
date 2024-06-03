package cn.edu.szu.feign.client;

import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.pojo.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/user/findIdByEmail/{email}")
    Long getUserByMail(@PathVariable String email);

    @PutMapping("api/user/update/user")
    boolean updateUserInfo(@RequestParam String name, @RequestParam String phone, @RequestParam String avatar, @RequestParam Long userId);
}
