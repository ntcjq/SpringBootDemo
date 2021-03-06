package com.cjq.SpringBootDemo.service;


import com.cjq.SpringBootDemo.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RedisLockService {

    private int allCount = 1000;
    private int sellCount = 0;

    AtomicInteger atomicIntegerD = new AtomicInteger(1000);

    AtomicInteger atomicIntegerI = new AtomicInteger();

    /**
     * 过期时间 ms
     */
    private final long EXPIRE_TIME = 1*1000;

    @Autowired
    private RedisLock redisLock;


    public void sellTickets(String ticketId) {

        /**
         * 加锁
         */
        long value = System.currentTimeMillis() + EXPIRE_TIME;
        //此处key根据业务需要来定义
        if (!redisLock.lock(ticketId, String.valueOf(value))) {
            //获取锁失败，抛出异常
            throw new MyException("当前活动太过火爆，请刷新后重试！");
        }
        /**
         * 业务代码
         */
        allCount--;
        sellCount++;
        /**
         * 解锁
         */
        redisLock.unlock(ticketId, String.valueOf(value));
    }

    public Map<String, Integer> currentTickets() {
        Map<String, Integer> map = new HashMap<>();
        map.put("allCount", allCount);
        map.put("sellCount", sellCount);
        return map;
    }


    public void atomicTest(){
        atomicIntegerD.getAndDecrement();
        atomicIntegerI.getAndIncrement();
    }


    public Map<String, Integer> atomics() {
        Map<String, Integer> map = new HashMap<>();
        map.put("atomicIntegerD", atomicIntegerD.get());
        map.put("atomicIntegerI", atomicIntegerI.get());
        return map;
    }
    public static void main(String[] args){
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());
    }
}
