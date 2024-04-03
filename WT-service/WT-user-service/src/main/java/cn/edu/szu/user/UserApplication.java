package cn.edu.szu.user;

import cn.edu.szu.feign.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "cn.edu.szu.feign.client", defaultConfiguration = DefaultFeignConfig.class)
@MapperScan("cn.edu.szu.user.mapper")
@ComponentScan("cn.edu.szu.user.dao")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
