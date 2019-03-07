package me.changjie.controller;

import me.changjie.common.UserInfoConstant;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入
 * Created by ChangJie on 2019-03-07.
 */
@ServerEndpoint(value = "/websocket/{userId}",configurator = SpringConfigurator.class)
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

        String userName = getUserName(session);

        this.session = session;
        //加入聊天室
        chatRoomSet.add(this);
        //在线数加1
        onlineCount.incrementAndGet();

        broadcastToAll(LocalDateTime.now() +" "+ userName+" 加入了群聊");

        System.out.println(userName+"加入了群聊！当前在线人数为" + onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws Exception {

        String userName = getUserName(session);

        chatRoomSet.remove(this);
        onlineCount.decrementAndGet();

        broadcastToAll(LocalDateTime.now() +" "+ userName+" 退出了群聊");
        System.out.println(userName+"退出了群聊！当前在线人数为" + onlineCount.get());
    }

    private String getUserName(Session session){
        Map<String, String> map = session.getPathParameters();
        Integer userId = Integer.valueOf(map.get("userId"));
        String userName = UserInfoConstant.idNameMap.get(userId);
        return userName;
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {

        String userName = getUserName(session);

        System.out.println("来自客户端的消息:" + message);
        broadcastToAll(LocalDateTime.now()+" "+ userName + ":" + message);
    }

    private void broadcastToAll(String message) throws Exception {
        for (WebSocketController client : chatRoomSet){
            client.getSession().getBasicRemote().sendText(message);
        }
    }

}
