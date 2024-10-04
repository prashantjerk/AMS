package com.example.AMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisConfig {
    @Bean
    public JedisPooled jedisPooled() {
        HostAndPort hostandport = new HostAndPort("10.200.4.80", 6379);

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .password("prakar2UoL#@")
                .build();
        return new JedisPooled(hostandport, config);
    }
}
