package cn.edu.szu.feign.client;

import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.pojo.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/user/findIdByEmail/{email}")
    Long getUserByMail(@PathVariable String email);

    @PostMapping("api/user/update/user")
    boolean updateUserInfo(@RequestParam String name, @RequestParam String phone, @RequestParam Long userId,@RequestParam String avatar);

    @PostMapping("api/user/uploadImage")
     Result uploadImage(@RequestParam MultipartFile file);

    @GetMapping("/api/user/getUserIdsByName/{name}")
    List<String> getIdsByName(@PathVariable String name);
}
