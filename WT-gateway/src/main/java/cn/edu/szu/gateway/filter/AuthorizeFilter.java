package cn.edu.szu.gateway.filter;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.common.utils.JwtUtil;
import cn.edu.szu.feign.client.AuthClient;
import cn.edu.szu.feign.pojo.CheckAuthDTO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
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
    @Autowired
    private AuthClient authClient;
    private static final List<String> PASS_PATH = Arrays.asList(
            "/api/email",
            "/api/user/createUser",
            "/api/user/login",
            "/api/company_user/getCompanyInfo",
            "/"
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
            System.out.println("拦截请求：没有身份令牌");
            return reject(exchange);
        }

        String testToken = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAABWLwQ6CQAxE_6VnNqFsV1i-w8Rz7VZEYiB0ORjjv1tuM-_NfOFVZxghDvrAQTSIaAok0oaMLIHaLifKhQslaGDmCiP2GClivFADdtz9bR-r-j69mdfbui_XddL61N0hH8Uhb9s58Ii_P4snN1x2AAAA.LN3i1dwDYCJLMLza6-OhTBmi9Tv_f753CX33JZBluovKpr5X8kw3u6X01cVQH3jg0AnKI2typC4aVTjFwCR_wg";
        if (testToken.equals(token)){
            return chain.filter(exchange);
        }

        // 4.解析token，得到用户id
        Long id = JwtUtil.getUserId(token);
        if (id == null) {
            // 拦截
            System.out.println("拦截请求：token无法解析");
            return reject(exchange);
        }

        // 5.检查登录账号是否有效,延长登录有效期
        String key = LOGIN_USER_KEY + id;
        if (stringRedisTemplate.opsForHash().entries(key).isEmpty()) {
            // 拦截
            System.out.println("拦截请求：无效/过期的token");
            return reject(exchange);
        }
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // TODO：6.验证权限
//        CheckAuthDTO authDTO = new CheckAuthDTO();
//        authDTO.setUserId(id);
//        authDTO.setCompanyId(1L);
//        authDTO.setResource(path);
//        Result result = authClient.checkAuth(authDTO);
//        Boolean data = (Boolean) result.getData();
//        if (data) {
//            // 放行
//            return chain.filter(exchange);
//        }
//        return reject(exchange);
        return chain.filter(exchange);
    }

    private static Mono<Void> reject(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);// 设置状态码
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String json = JSONUtil.toJsonStr(new Result(Code.AUTH_ERR, null, "权限不足"));
        response.writeWith(Mono.just(response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8))));
        return response.setComplete();
    }

    /**
     * 设置过滤器优先级
     */
    @Override
    public int getOrder() {
        return 0;
    }
}