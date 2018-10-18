package com.example.websocket.client;


import com.example.websocket.utils.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * 方式二：通过处理器来接收服务端的消息
 */
@Configuration
@Component
public class MyTextWebSocketHandler extends TextWebSocketHandler {

    /**
     * 建立连接成功，准备使用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Log.info("成功连接到服务端");
        try {
            session.sendMessage(new TextMessage("hello server,first meeting."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收并处理消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Log.info("收到服务端发来消息："+message.getPayload());
    }

    /**
     * 连接关闭后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Log.warn("与服务端断开连接");
        super.afterConnectionClosed(session, status);
    }

    /**
     * 传输错误
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }


}