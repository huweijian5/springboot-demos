package com.example.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 最简开启redis缓存可以不提供此配置类，配置文件中的spring.cache.redis相关配置有效
 * 启用此缓存配置类后配置文件中spring.cache.redis相关配置失效
 * 可以在此处配置相关cacheName对应的缓存时间
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    private String keyPrefix = "prefix";

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                //.entryTtl(Duration.ofDays(1))//缓存1天
                .entryTtl(Duration.ofSeconds(150))//缓存150s
                //.disableCachingNullValues() //不缓存空值
                .computePrefixWith(cacheName -> cacheName.concat(":").concat(keyPrefix).concat(":"))//自定义key前缀
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer()))//序列化后在redis就会明文保存了，方便调试，生产环境中建议关闭，需要引入jackson依赖
                ;



        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("test");
        cacheNames.add("user");//本例中未用

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("test", cacheConfiguration);
        configMap.put("user", cacheConfiguration.entryTtl(Duration.ofSeconds(120)));
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     // 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }

}