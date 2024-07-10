package cn.edu.szu.teamwork.config;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.models.role.RedisNodeDescription;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
@Slf4j
@Configuration
class WriteToMasterReadFromReplicaConfiguration {

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {

//    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//            .readFrom(ReadFrom.REPLICA_PREFERRED)
//            .build();
//
    RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("127.0.0.1", 6379);
//    return new LettuceConnectionFactory(serverConfig, clientConfig);
    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    LettuceClientConfiguration clientConfig =
            LettucePoolingClientConfiguration.builder().readFrom(new ReadFrom() {
                      @Override
                      public List<RedisNodeDescription> select(Nodes nodes) {
                        List<RedisNodeDescription> allNodes = nodes.getNodes();
                        int ind = Math.abs((int) (Math.random() * 11) % allNodes.size());
                        RedisNodeDescription selected = allNodes.get(ind);
                        log.info("Selected random node {} with uri {}", ind, selected.getUri());
                        List<RedisNodeDescription> remaining = IntStream.range(0, allNodes.size())
                                .filter(i -> i != ind)
                                .mapToObj(allNodes::get).collect(Collectors.toList());
                        return
                                Stream.of(selected).collect(Collectors.toList());
                      }
                    }).commandTimeout(Duration.ofMillis(10000))
                    .poolConfig(config).build();
    return new LettuceConnectionFactory(serverConfig, clientConfig);


  }


}