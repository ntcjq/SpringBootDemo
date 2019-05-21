package com.cjq.SpringBootDemo.config.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//这里就是前面说的，这个注解读入我们的配置对象类
@EnableConfigurationProperties(HelloProperties.class)
//@ConditionalOnClass注解存在的意义在于判断类加载器中是否存在HelloService这个类，如果存在的话会在Spring容器中加载HelloAutoConfiguration配置类；否则不会加载。
//注意：上面说的是在Spring容器中加载HelloAutoConfiguration这个配置类，不是在Spring容器中加载HelloService这个类。
//两种写法：
//@ConditionalOnClass(name="com.cjq.SpringBootDemo.config.auto.HelloService")
@ConditionalOnClass(HelloService.class)
public class HelloAutoConfiguration {

    @Autowired
    private HelloProperties helloProperties;

    @Bean
    //这个配置就是SpringBoot可以优先使用自定义Bean的核心所在，如果Spring容器中没有我们的自定义Bean才会像下面这样去创建一个新的Bean
    @ConditionalOnMissingBean(HelloService.class)
    public HelloService auto(){
        HelloService helloService =new HelloService();
        helloService.setMsg(helloProperties.getMsg());
        return helloService;
    }

}
