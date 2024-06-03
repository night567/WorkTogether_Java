package cn.edu.szu.teamwork.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件
        PaginationInnerInterceptor pageInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInnerInterceptor.setMaxLimit(1000L); // 分页上限
        interceptor.addInnerInterceptor(pageInnerInterceptor);

        return interceptor;
    }
}
