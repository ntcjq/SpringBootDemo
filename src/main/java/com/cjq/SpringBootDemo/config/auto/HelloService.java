package com.cjq.SpringBootDemo.config.auto;

import org.springframework.stereotype.Component;

//这里很重要，如果我们添加了@Component注解,那么SpringBoot会优先使用我们配置的这个Bean，如果不添加，则使用HelloAutoConfiguration的创建的HelloService
//@Component
public class HelloService {

    private String msg = "service";//如果自动配置没有读入成功，那么为默认值

    public String say() {
        return "hello " + msg;
    }//为我们服务的方法

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}