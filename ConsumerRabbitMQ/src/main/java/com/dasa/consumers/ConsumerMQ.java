package com.dasa.consumers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dasa.helpers.SOHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class ConsumerMQ {

	@Autowired
	private SOHelper sohelper; 
	
	@Autowired
	private Channel channel; 
	

	public void consome() {
		Consumer consumer = new DefaultConsumer(channel) {
		    @Override
		     public void handleDelivery(
		        String consumerTag,
		        Envelope envelope, 
		        AMQP.BasicProperties properties, 
		        byte[] body) throws IOException {
		  
		            String message = new String(body, "UTF-8");
		            System.out.println(message);
		     }
		};
		System.out.println(sohelper.getMacAddress());
		try {
			
			channel.basicConsume("r3po4", true, consumer);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
