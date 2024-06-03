package cn.edu.szu.teamwork;

import cn.edu.szu.feign.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync // 开启异步任务支持
@SpringBootApplication
@EnableFeignClients(basePackages = "cn.edu.szu.feign.client", defaultConfiguration = DefaultFeignConfig.class)
public class TeamworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamworkApplication.class, args);
    }
}
