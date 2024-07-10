package cn.edu.szu.teamwork.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author tbb
 *
 */
@Configuration
public class RedssonConfig
{
//	@Bean(destroyMethod = "shutdown")
//	public RedissonClient redisson() throws IOException
//	{
//		RedissonClient redisson = Redisson.create(Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
//		return redisson;
//	}
	@Bean
	public Redisson redissonClient1() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
		config.useSingleServer().setAddress("redis://127.0.0.1:6380").setDatabase(0);
		return (Redisson) Redisson.create(config);
	}

	@Bean
	public Redisson redissonClient2() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
		config.useSingleServer().setAddress("redis://127.0.0.1:6378").setDatabase(0);
		return (Redisson) Redisson.create(config);
	}

	@Bean
	public Redisson redissonClient3() throws IOException {
		Config config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
		config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
		return (Redisson) Redisson.create(config);
	}

}
