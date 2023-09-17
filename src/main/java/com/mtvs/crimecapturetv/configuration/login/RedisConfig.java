package com.mtvs.crimecapturetv.configuration.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;


public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // RedisConnectionFactory : Redis와의 연결을 설정하고 관리하기 위한 인터페이스
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
        // LettuceConnectionFactory : Spring 애플리케이션에서 Lettuce를 사용하여 Redis와 상호 작용하기 위한 연결 관리를 지원하는 클래스
    }

}
