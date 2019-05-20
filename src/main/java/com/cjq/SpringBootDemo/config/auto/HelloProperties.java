package com.cjq.SpringBootDemo.config.auto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "hello")
//如果这里添加了@Component注解，那么在自动配置类的时候就不用添加@EnableConfigurationProperties(HelloProperties.class)注解
//@Component
public class HelloProperties {

    private String msg="default";//现在我们在配置文件写hello.msg=world,因为简单就不再展示;如果那么默认为default.

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}