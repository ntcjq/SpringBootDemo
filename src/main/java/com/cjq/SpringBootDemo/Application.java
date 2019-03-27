package com.cjq.SpringBootDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * 启动类的扫描默认是扫描与该启动类同包以及其子包下的类
 * 如何自定义自动扫描的包的位置？
 * 例：
 * //自定义自动扫描的包
 * @ComponentScan(basePackages = {"com.test1","com.test2"})
 * @author v.cuijq
 */

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ServletComponentScan //配置druid必须加的注解，如果不加，访问页面打不开，filter和servlet、listener之类的需要单独进行注册才能使用，spring boot里面提供了该注解起到注册作用
//@EnableTransactionManagement   //启用事务  有些说可以不加 ,有些说要加(亲测不加也能实现事务管理) 等同于xml配置方式的 <tx:annotation-driven>
@MapperScan("com.cjq.SpringBootDemo.mapper") //默认配置下mybatis的mapper的扫描
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 120)//redis实现session共享  ，maxInactiveIntervalInSeconds: 设置Session失效时间，使用Redis Session之后，原Boot的server.session.timeout属性不再生效
public class Application extends WebMvcConfigurationSupport {


        @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println("=================使用的事务管理器类型：" + platformTransactionManager.getClass().getName());
        return new Object();
    }

    //这里配置静态资源文件的路径导包都是默认的直接导入就可以
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
