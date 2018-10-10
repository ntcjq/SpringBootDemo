package com.cjq.SpringBootDemo.service;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@ServerEndpoint("/myWebSocketServer/{userno}")
@Component
public class MyWebSocketServer {

    public static WebSocketService webSocketService;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     *  concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static ConcurrentHashMap<String, MyWebSocketServer> webSocketMap = new ConcurrentHashMap<String, MyWebSocketServer>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 当前发消息的人员编号
     */
    private String userno = "";

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userno") String param, Session session, EndpointConfig config) {
        System.out.println("有新连接加入(用户："+param+")！");
        /**
         * //接收到发送消息的人员编号
         */
        userno = param;
        this.session = session;
        /**
         * 加入map中
         */
        webSocketMap.put(param, this);
        /**
         * 在线数加1
         */
        addOnlineCount();
        System.out.println("当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!userno.equals("")) {
            /**
             * 从map中删除
             */
            webSocketMap.remove(userno);
            /**
             * 在线数减1
             */
            subOnlineCount();
            System.out.println("用户"+userno+"关闭连接！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端"+userno+"的消息:" + message);
        final String HeartBeat = "HeartBeat";
        if(HeartBeat.equals(message)){
            //心跳测试,返回HeartBeat
            try {
                session.getBasicRemote().sendText("HeartBeat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String backInfo = webSocketService.wiredSuccess();
            System.out.println("调用service返回的结果："+backInfo);
            try {
                session.getBasicRemote().sendText("来自服务端的回复:收到");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 给指定的人发送消息
     * @param message
     */
    public void sendToOne(String sendUserno,String message) {
        String now = getNowTime();
        try {
            if (webSocketMap.get(sendUserno) != null) {
                webSocketMap.get(sendUserno).sendMessage(now+"服务端发来消息："+ message);
            } else {
                System.out.println("当前用户不在线");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给所有人发消息
     * @param message
     */
    public void sendToAll(String message) {
        String now = getNowTime();
        //遍历HashMap
        for (String key : webSocketMap.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (!userno.equals(key)) {
                    webSocketMap.get(key).sendMessage(now+"服务端发来消息："+ message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ConcurrentHashMap<String, MyWebSocketServer> getAllOnlineUser(){
        return webSocketMap;
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    private String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocketServer.onlineCount--;
    }


}