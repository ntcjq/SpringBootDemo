package com.cjq.SpringBootDemo.service;


import com.cjq.SpringBootDemo.enums.ResultEnum;
import com.cjq.SpringBootDemo.exception.MyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Redis分布式锁
 */
@Component
public class RedisLock {

    private Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * @param key       主键
     * @param value     = 当前时间 + 过期时间
     * @return
     */
    public boolean lock(String key,String value){
        //如果设值成功，即获取到锁，返回true
        if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        //如果锁超时，且第一个获取到锁的值，也可以获取到锁，返回true
        String oldValue = stringRedisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(oldValue) && Long.parseLong(oldValue) < System.currentTimeMillis()){
            //该方法是获取的同时设值新的value，如果用多个线程同时运行到这儿，先取到值的才能获取到锁.
            String oldValueTwo = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(oldValueTwo) && oldValue.equals(oldValueTwo)){
                return true;
            }
        }
        return false;
    }


    public void unlock(String key,String value){
        String oldValue = stringRedisTemplate.opsForValue().get(key);
        try {
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(value)){
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }else{
                //锁被其他人获取，此时共享数据操作可能会出现异常，需要抛出异常使其回滚
                throw new MyException(ResultEnum.REDIS_LOCKKEY_IS_REGET);
            }
        }catch (Exception e){
            logger.error("【Redis分布式锁】解锁异常{}",e.getMessage());
            throw new MyException(ResultEnum.REDIS_UNCLOCK_FAILD);
        }
    }


    /**
     *  使用方式
     */
    public static void main(String[] args){
        RedisLock redisLock = new RedisLock();
        //过期时间10s
        final long EXPIRE_TIME = 10*1000;

        /**
         * 加锁
         */
        long value = System.currentTimeMillis() + EXPIRE_TIME;
        //此处key根据业务需要来定义
        String key = UUID.randomUUID().toString();
        if(!redisLock.lock(key,String.valueOf(value))){
            //获取锁失败，抛出异常
            throw new MyException("当前活动太过火爆，请刷新后重试！");
        }
        /**
         *  业务代码。。。
         */
        /**
         * 解锁
         */
        redisLock.unlock(key,String.valueOf(value));
    }

}
