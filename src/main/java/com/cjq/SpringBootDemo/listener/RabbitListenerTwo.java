package com.cjq.SpringBootDemo.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: cuijq
 */
@Component
@RabbitListener(queues = {"Cui.queue1"})
public class RabbitListenerTwo {

    @RabbitHandler
    public void process(String receivedMsg){
        System.out.println("RabbitListenerTwo received "+receivedMsg);
    }

}
