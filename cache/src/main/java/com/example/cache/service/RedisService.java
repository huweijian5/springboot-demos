package com.example.cache.service;

import com.example.cache.bean.Test;
import com.example.cache.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public  void setString(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value);
        stringRedisTemplate.expire(key,50, TimeUnit.SECONDS);
    }

    public String getString(String key){
        return  stringRedisTemplate.opsForValue().get(key);
    }

    public void setObj(String key,Test value) {
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,50, TimeUnit.SECONDS);
    }

    public Test getObj(String key) {
        return (Test)redisTemplate.opsForValue().get(key);
    }





}
