package com.example.websocket.server;

import com.example.websocket.utils.Log;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket服务
 */
@ServerEndpoint(value = "/websocket/{username}")//端点路径，用户可以通过ws://ip:port/websocket/{username}访问，其中username为用户自定义内容
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("username") String user, Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        Log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("你好 " + user + ",欢迎使用!");
        } catch (IOException e) {
            Log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        Log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 接收文本数据，注意接收文本和二进制是分开的，二进制的数据不会调到这里
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage(maxMessageSize=5_000_000)
    public void onMessage(String message,Session session) {
        String user = session.getPathParameters().get("username");
        Log.info("收到来自客户端 " + user + " 的文本消息:" + message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 接收二进制数据，注意接收文本和二进制是分开的，文本的数据不会调到这里
     * 注意maxMessageSize如果不设置的话，那默认是8KB,只要超过这个大小，那么消息便无法进到这里
     * 并且没有任何提示信息，因此这个坑要注意些
     * @param messages
     * @param session
     */
    @OnMessage(maxMessageSize=5_000_000)
    public void onMessage(byte[] messages, Session session)  {
        String user = session.getPathParameters().get("username");
        Log.info("收到来自客户端 " + user + " 的二进制消息:" + messages);
        try {
            session.getBasicRemote().sendBinary(ByteBuffer.wrap(messages));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        Log.error("发生错误");
        error.printStackTrace();
    }


    private void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendToAll(String message) {
        Log.info("sendToAll:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                Log.error("sessionId="+item.session.getId()+" send failed:"+e.getMessage());
                continue;
            }
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
