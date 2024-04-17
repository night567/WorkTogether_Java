package cn.edu.szu.feign.fallback;

import cn.edu.szu.feign.client.UserClient;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return null;
    }
}
