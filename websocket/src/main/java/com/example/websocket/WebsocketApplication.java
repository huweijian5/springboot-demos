package com.example.websocket;

import com.example.websocket.client.MyTextWebSocketHandler;
import com.example.websocket.client.WebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);

		//测试客户端连接到服务端进行会话
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		String uri = "ws://localhost:8081/websocket/xiaoming";

		//第一种连接方式
//		container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
//		StandardWebSocketClient client = new StandardWebSocketClient(container);
//		MyTextWebSocketHandler handler=new MyTextWebSocketHandler();
//		WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(client, handler, uri);
//		connectionManager.start();


		//第二种连接方式
		try {
			Session session = container.connectToServer(WebSocketClient.class, new URI(uri)); // 连接会话
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
