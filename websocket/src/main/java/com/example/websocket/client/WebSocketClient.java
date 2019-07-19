package com.example.websocket.client;

import com.example.websocket.utils.Log;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;

/**
 *方式一：通过注解方式来接收服务端的消息
 */
@ClientEndpoint
@Component
public class WebSocketClient {
    @OnOpen
    public void onOpen(Session session) {
        Log.info("成功连接到服务端");
        try {
            //测试文本发送
            //session.getBasicRemote().sendText("hello server,first meeting.");
            //测试二进制发送
            session.getBasicRemote().sendBinary( ByteBuffer.wrap("hello server,first meeting.".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Log.info("收到服务端发来消息："+message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
