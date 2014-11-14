package com.mq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitUtil {
	public static ConnectionFactory factory = new ConnectionFactory();
	public static void main(String[] args) {
		factory.setUsername("userName");
		factory.setPassword("password");
		factory.setVirtualHost("rusen_smtp_mq");
		factory.setHost("192.168.100.88");
		factory.setPort(2121);
		Connection conn = null;
		Channel channel = null;
		try {
			conn = factory.newConnection();
			channel = conn.createChannel();
			byte[] messageBodyBytes = "消息内容".getBytes();
			channel.basicPublish("emailQueue", "rk", null, messageBodyBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				channel.close();
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
