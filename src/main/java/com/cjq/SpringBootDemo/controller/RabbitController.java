package com.cjq.SpringBootDemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cuijq
 */
@RestController
@RequestMapping("mq")
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public String sendMessage(@RequestParam(value = "msg",defaultValue = "你好呀！") String msg){

        for(int i = 0;i<10;i++){
             //AMQP default  此时routingKey == queue name
//              rabbitTemplate.convertAndSend("hello",msg + "HELLO" + i);
            //exchange 和 routingKey两个都要完整匹配的队列才能收到
//            rabbitTemplate.convertAndSend("Cui.DirectExchange","helloRouting",msg+ "Hello" + i);
            //exchange绑定的所有队列都能收到，和routingKey无关
//            rabbitTemplate.convertAndSend("Cui.FanoutExchange","Cui.queue",msg+ "Cui.queue" + i);
            //exchange绑定的queue的routingKey模糊匹配的队列才能收到
//            rabbitTemplate.convertAndSend("Cui.TopicExchange","Cui.jia.queue",msg+ "Topic" + i);
//            rabbitTemplate.convertAndSend("Cui.TopicExchange","Cui.queue",msg+ "Topic" + i);

            rabbitTemplate.convertAndSend("Cui.TopicExchange","Cui.jia.",msg+ "Topic" + i);
        }
        return "Success";
    }

}
