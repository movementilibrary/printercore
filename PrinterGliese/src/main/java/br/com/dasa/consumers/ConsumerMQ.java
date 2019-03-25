package br.com.dasa.consumers;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import br.com.dasa.helpers.SOHelper;

@Component
public class ConsumerMQ {
	
	private static final Logger log = LoggerFactory.getLogger(ConsumerMQ.class);

	@Autowired
	private SOHelper sohelper; 
	@Autowired
	private Channel channel; 
	
	private String macAddress; 
	
	@PostConstruct
	public void iniciar() {
		try {
			macAddress = sohelper.getMacAddress();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
	}

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
		
		try {
			channel.basicConsume(macAddress, true, consumer);
		} catch (IOException e) {
			
			log.error(e.getMessage(), e);
		}
	}
}
