package com.example.cache.service;

import com.example.cache.bean.Test;
import com.example.cache.dao.CacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    CacheDao cacheDao;

    public void add(Test test) {
        cacheDao.insert(test);
    }

    public Test get(Integer id) {
        return cacheDao.select(id);
    }

    public void delete(Integer id) {
        cacheDao.delete(id);
    }

    public void modify(Test test) {
        cacheDao.update(test);
    }

    /**
     * 删除所有缓存
     */
    @CacheEvict(cacheNames = "test",allEntries = true)// @Cacheable 类似，但会把方法的返回值放入缓存中, 主要用于数据新增和修改方法
    public void deleteAllCache() {
    }


}
