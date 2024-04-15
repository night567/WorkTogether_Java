package cn.edu.szu.gateway.filter;

import cn.edu.szu.common.utils.JwtUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.edu.szu.common.utils.RedisConstants.LOGIN_USER_KEY;
import static cn.edu.szu.common.utils.RedisConstants.LOGIN_USER_TTL;

@Component
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final List<String> PASS_PATH = Arrays.asList(
            "/api/email",
            "/api/user/createUser",
            "/api/user/login"

    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().pathWithinApplication().value();

        // 2.部分接口放行
        if (PASS_PATH.stream().anyMatch(path::startsWith)) {
            System.out.println("skipPath：" + path);
            return chain.filter(exchange);
        }

        // 3.获取token
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            // 拦截
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);// 设置状态码
            return exchange.getResponse().setComplete();// 拦截请求
        }

        // 4.解析token，得到用户id
        Long id = JwtUtil.getUserId(token);
        if (id == null) {
            // 拦截
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);// 设置状态码
            return exchange.getResponse().setComplete();// 拦截请求
        }

        // 5.检查登录账号是否有效,延长登录有效期
        String key = LOGIN_USER_KEY + id;
        if (stringRedisTemplate.opsForHash().entries(key).isEmpty()) {
            // 拦截
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);// 设置状态码
            return exchange.getResponse().setComplete();// 拦截请求
        }
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // TODO：6.验证权限

        return chain.filter(exchange);
    }

    /**
     * 设置过滤器优先级
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
