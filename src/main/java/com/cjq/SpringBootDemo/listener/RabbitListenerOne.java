package com.cjq.SpringBootDemo.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * 消费者只需要关心Queue名称
 * @Author: cuijq
 */
@Component
@RabbitListener(queues = {"hello"})
public class RabbitListenerOne {

    @RabbitHandler
    public void process(String receivedMsg){
        System.out.println("RabbitListenerOne received "+receivedMsg);
    }

}
