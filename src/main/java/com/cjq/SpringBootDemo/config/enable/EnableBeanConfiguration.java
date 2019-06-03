package com.cjq.SpringBootDemo.config.enable;

import org.springframework.context.annotation.Bean;

public class EnableBeanConfiguration {

    @Bean
    public EnableBeanDto enbaleBeanDto() {
        return new EnableBeanDto();
    }

    @Bean
    public EnableBeanVo enbaleBeanVo() {
        return new EnableBeanVo();
    }
}
