package com.cjq.SpringBootDemo.config;


import com.cjq.SpringBootDemo.domain.Girl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GirlConfig {

    @Bean
    @ConfigurationProperties(prefix = "girl")
    public Girl setGirl() {
        Girl girl = new Girl();
        return girl;
    }
}
