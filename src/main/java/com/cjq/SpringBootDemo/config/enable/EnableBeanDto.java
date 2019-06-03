package com.cjq.SpringBootDemo.config.enable;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class EnableBeanDto {
    @Scheduled(cron = "0 0/5 18 * * ?")
    public void test(){
        System.out.println("================EnableBeanDto"+new Date());
    }
}
