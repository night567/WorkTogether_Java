package cn.edu.szu.feign.fallback;

import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.feign.client.AuthClient;
import org.springframework.cloud.openfeign.FallbackFactory;

public class AuthClientFallbackFactory implements FallbackFactory<AuthClient> {
    @Override
    public AuthClient create(Throwable cause) {
        return checkAuthDTO -> new Result(404, false, "远程调用失败");
    }
}
