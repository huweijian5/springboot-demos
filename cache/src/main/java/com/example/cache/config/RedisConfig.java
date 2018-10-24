package com.example.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

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

    /**
     * 缓存管理器
     * @param factory
     * @return
     */
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


    /**
     * redis模板，存储关键字是字符串，值jackson2JsonRedisSerializer是序列化后的值
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        RedisSerializer redisSerializer = new StringRedisSerializer();
        //key
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        //value
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }

}