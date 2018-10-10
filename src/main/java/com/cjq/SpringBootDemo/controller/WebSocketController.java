package com.cjq.SpringBootDemo.controller;


import com.cjq.SpringBootDemo.service.MyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "webSocket")
public class WebSocketController {

    @Autowired
    private MyWebSocketServer myWebSocketServer;


    @RequestMapping("/view")
    public String view(String userno){
        return "webSocket";
    }


    @RequestMapping("/sendToAll")
    @ResponseBody
    public String sendToAll(String msg){
        myWebSocketServer.sendToAll(msg);
        return msg;
    }


    @RequestMapping("/sendToOne")
    @ResponseBody
    public String sendToOne(String userno,String msg){
        myWebSocketServer.sendToOne(userno,msg);
        return userno+":"+msg;
    }

    @RequestMapping("/getAllOnlineUser")
    @ResponseBody
    public Set getAllOnlineUser(){
        ConcurrentHashMap<String, MyWebSocketServer> users = myWebSocketServer.getAllOnlineUser();
        Set<String> set = users.keySet();

        return set;
    }

}
