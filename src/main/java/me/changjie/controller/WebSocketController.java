package me.changjie.controller;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入
 * Created by ChangJie on 2019-03-07.
 */
@ServerEndpoint(value = "/websocket",configurator = SpringConfigurator.class)
public class WebSocketController {

    private static AtomicInteger onlineCount = new AtomicInteger(0);


    private static CopyOnWriteArraySet<WebSocketController> chatRoomSet = new CopyOnWriteArraySet<WebSocketController>();

    /**
     * 与客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) throws Exception {
        this.session = session;
        //加入聊天室
        chatRoomSet.add(this);
        //在线数加1
        onlineCount.incrementAndGet();
        broadcastToAll("有新连接加入");
        System.out.println("有新连接加入！当前在线人数为" + onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws Exception {
        chatRoomSet.remove(this);
        onlineCount.decrementAndGet();
        broadcastToAll("有一连接关闭");
        System.out.println("有一连接关闭！当前在线人数为" + onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println("来自客户端的消息:" + message);
        broadcastToAll(message);
    }

    private void broadcastToAll(String message) throws Exception {
        for (WebSocketController client : chatRoomSet){
            client.getSession().getBasicRemote().sendText(message);
        }
    }

}
