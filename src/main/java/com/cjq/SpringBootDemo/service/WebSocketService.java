package com.cjq.SpringBootDemo.service;


import org.springframework.stereotype.Service;

/**
 * 该类主要用于测试MyWebSocket类中无法注入bean
 */
@Service
public class WebSocketService {

    public String wiredSuccess(){
        /**
         * 此处可以根据客户端发来的消息进行相关的业务处理
         */
        return "成功注入";
    }
}
