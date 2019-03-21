package com.dasa.configurations;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.port}")
	private String port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	private Connection connection;
	private Channel channel;

	@Bean
	public Connection getConnection() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			factory.setPort(Integer.valueOf(port));
			factory.setUsername(username);
			factory.setPassword(password);
			connection = factory.newConnection();
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean
	public Channel getChannel() throws IOException {
		channel = getConnection().createChannel();
		return channel;
	}

	@PreDestroy
	public void onDestroy() throws Exception {

		try {
			System.out.println("Finalizando conexão");
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
