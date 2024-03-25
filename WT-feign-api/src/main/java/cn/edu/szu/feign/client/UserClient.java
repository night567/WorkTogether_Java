package cn.edu.szu.feign.client;

import cn.edu.szu.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/api/user/findByEmail/{email}")
    Result getUserByEmail(@PathVariable("email") String email);
}
