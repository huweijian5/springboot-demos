package com.example.cache.dao;

import com.example.cache.bean.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.cnblogs.com/OnlyCT/p/7845660.html
 * https://blog.csdn.net/dreamhai/article/details/80642010
 * 需要注意的是当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能
 */
@Repository
@CacheConfig(cacheNames = "test")//@CacheConfig是一个类级别的注解，允许共享缓存的名称、KeyGenerator、CacheManager 和CacheResolver。该操作会被覆盖
public class CacheDao {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public static Map<Integer, Test> map = new HashMap<>();


    //@Cacheable(cacheNames = "test", key = "#p0")//标注的方法前先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；若没有数据，执行该方法并将方法返回值放进缓存
    @Cacheable(key = "#p0")//标注的方法前先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；若没有数据，执行该方法并将方法返回值放进缓存
    public Test select(Integer id) {
        logger.info("select");
        return map.get(id);
    }

    //@CachePut(cacheNames = "test", key = "#p0.id")
    @CachePut(key = "#p0.id")
    public Test insert(Test test) {
        logger.info("insert");
        map.put(test.getId(), test);
        return test;
    }

    // @CacheEvict(cacheNames = "test", key = "#p0")//方法执行成功后会从缓存中移除相应数据
    @CacheEvict(key = "#p0")//方法执行成功后会从缓存中移除相应数据
    public void delete(Integer id) {
        logger.info("delete");
        map.remove(id);
    }


    //@CachePut(cacheNames = "test", key = "#p0.id")// @Cacheable 类似，但会把方法的返回值放入缓存中, 主要用于数据新增和修改方法
    @CachePut(key = "#p0.id")// @Cacheable 类似，但会把方法的返回值放入缓存中, 主要用于数据新增和修改方法
    public Test update(Test test) {
        logger.info("update");
        map.put(test.getId(), test);
        return test;
    }


}
