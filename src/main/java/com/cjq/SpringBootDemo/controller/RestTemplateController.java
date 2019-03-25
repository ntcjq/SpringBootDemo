package com.cjq.SpringBootDemo.controller;

import com.cjq.SpringBootDemo.domain.Girl;
import com.cjq.SpringBootDemo.domain.Result;
import com.cjq.SpringBootDemo.util.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: cuijq
 */
@RestController
public class RestTemplateController {

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("rest")
    public Result rest(){
        String getUrl = "http://localhost:8888/girl/getGirls";

        ResponseEntity<List> forEntity = restTemplate.getForEntity(getUrl, List.class);
        List<Girl> forObject = restTemplate.getForObject(getUrl, List.class);


        //post传递对象时，直接传是接收不到的，
        //解决方式
        //一、MultiValueMap<String, Object> girl = new LinkedMultiValueMap<String, Object>();//之一用add方法添加属性
        //二、请求的方法加@RequestBody来接收对象

        String postUrl = "http://localhost:8888/girl/addGirl";
        MultiValueMap<String, Object> girl = new LinkedMultiValueMap<String, Object>();
        girl.add("cupSize","D");
        girl.add("name","LiLi");
        girl.add("age",19);
//        Girl girl = new Girl();  //直接这样接收不到
//        girl.setCupSize("D");
//        girl.setName("Lili");
//        girl.setAge(19);

        ResponseEntity<Result> resultResponseEntity = restTemplate.postForEntity(postUrl, girl, Result.class);

        Result result = restTemplate.postForObject(postUrl, girl, Result.class);


        return ResultUtil.success();
    }


}
