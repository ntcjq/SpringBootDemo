package com.cjq.SpringBootDemo.controller;

import com.cjq.SpringBootDemo.domain.LinkType;
import com.cjq.SpringBootDemo.domain.LinkUrl;
import com.cjq.SpringBootDemo.domain.Result;
import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.repository.LinkTypeRepository;
import com.cjq.SpringBootDemo.repository.LinkUrlRepository;
import com.cjq.SpringBootDemo.service.RedisLockService;
import com.cjq.SpringBootDemo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "redis")
public class RedisController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;


    @Autowired
    private RedisLockService redisLockService;


    @RequestMapping(value = "session")
    public Result session(HttpServletRequest request, HttpSession session){
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return ResultUtil.success("8888"+":"+session.getId());
    }


    @RequestMapping(value = "str")
    public Result str(){
        Random random = new Random();
        stringRedisTemplate.opsForValue().set("user:name","cjq"+random.nextInt(100));
        return ResultUtil.success();
    }
    @RequestMapping(value = "obj")
    public Result obj() throws Exception{
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set("com.domain", user);
        operations.set("com.domain.expire", user,1,TimeUnit.SECONDS);
        Thread.sleep(1000);
        //redisTemplate.delete("com.domain.expire");
        boolean exists=redisTemplate.hasKey("com.domain.expire");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        return ResultUtil.success();
    }


    /**
     * 测试分布式锁
     */
    @RequestMapping("testLock")
    @ResponseBody
    public void testLock(){
        String ticketId = "TESTKEY";
        redisLockService.sellTickets(ticketId);
    }

    /**
     * 查看测试结果
     * @return
     */
    @RequestMapping("testLockResult")
    public @ResponseBody Map<String,Integer> testLockResult(){
        return redisLockService.currentTickets();
    }



}
