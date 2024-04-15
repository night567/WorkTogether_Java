package cn.edu.szu.feign.client;

import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.fallback.AuthClientFallbackFactory;
import cn.edu.szu.feign.pojo.CheckAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service" , fallbackFactory = AuthClientFallbackFactory.class)
public interface AuthClient {
    @PostMapping("/api/userAndRole/check")
    Result checkAuth(@RequestBody CheckAuthDTO checkAuthDTO);
}