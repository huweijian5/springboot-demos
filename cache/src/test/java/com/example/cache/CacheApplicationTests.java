package com.example.cache;

import com.example.cache.dao.CacheDao;
import com.example.cache.service.CacheService;
import com.example.cache.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CacheService cacheService;

    @Test
    public void contextLoads() {
        com.example.cache.bean.Test test;

        test = new com.example.cache.bean.Test(1, "hello");
        cacheService.add(test);
        logger.info("add 1:" + test);
        test.setId(2);
        test.setContent("world");
        cacheService.add(test);
        logger.info("add 2:" + test);
        test.setId(3);
        test.setContent("hello world");
        cacheService.add(test);
        logger.info("add 3:" + test);

        test = cacheService.get(1);
        logger.info("get 1:" + test);

        test.setContent("hello world");
        CacheDao.map.put(1, test);
        test = new com.example.cache.bean.Test(2, "hello world");
        CacheDao.map.put(2, test);
        logger.info("now map  :" + CacheDao.map);

        test = cacheService.get(1);
        logger.info("get 1:" + test);

        test = CacheDao.map.get(1);
        cacheService.modify(test);
        logger.info("modify 1:" + test);

        test = cacheService.get(1);
        logger.info("get 1:" + test);

        test = CacheDao.map.remove(1);

        logger.info("now map  :" + CacheDao.map);

        test = cacheService.get(1);
        logger.info("get 1:" + test);

        cacheService.delete(1);
        logger.info("delete 1:" + test);

        test = cacheService.get(1);
        logger.info("get 1:" + test);

        test = cacheService.get(2);
        logger.info("get 2:" + test);

        cacheService.deleteAllCache();
        logger.info("clean all cache");

        test = cacheService.get(1);
        logger.info("get 1:" + test);

        test = cacheService.get(2);
        logger.info("get 2:" + test);

        test = cacheService.get(3);
        logger.info("get 3:" + test);

    }

    @Autowired
    RedisService redisService;

    @Test
    public void redis() {
        redisService.setString("redis","aaaa");
        logger.info("save redis->aaaa");
        String value=redisService.getString("redis");
        logger.info("get redis:"+value);

        com.example.cache.bean.Test t=new com.example.cache.bean.Test(123,"aaa");
        redisService.setObj("test",t);
        logger.info("save test:"+t.toString());
        t=redisService.getObj("test");
        logger.info("get test:"+t.toString());
    }


}
