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
            session.getBasicRemote().sendText("hello server,first meeting.");
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