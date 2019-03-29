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

import br.com.dasa.controllers.components.LogComponent;
import br.com.dasa.enums.LogEnum;
import br.com.dasa.helpers.SOHelper;
import br.com.dasa.services.PrinterService;
import javafx.application.Platform;

@Component
public class ConsumerMQ {
	
	private static final Logger log = LoggerFactory.getLogger(ConsumerMQ.class);

	@Autowired
	private SOHelper sohelper; 
	@Autowired
	private Channel channel; 
	@Autowired
	private PrinterService printerService; 
	@Autowired
	private LogComponent logComponent; 
	
	private String macAddress; 
	
	@PostConstruct
	public void iniciar() {
		try {
			macAddress = sohelper.getMacAddress();
			logComponent.addLog("Recuperando o MACADDRESS", LogEnum.INFO);
		} catch (Exception e) {
			logComponent.addLog("Erro ao recuperar MAC ADDRESS", LogEnum.ERROR);
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
		            
		            Platform.runLater(
		            		  () -> {
		            			  logComponent.addLog(message, LogEnum.INFO);
		            		  }
		            		);
		            
		            
		         //   printerService.imprimir(message);
		     }
		};
		
		try {
			logComponent.addLog("Iniciando consumer de Impressão", LogEnum.INFO);
			channel.basicConsume(macAddress, true, consumer);
			
		} catch (IOException e) {
			logComponent.addLog("Erro ao iniciar Consumer de impressão", LogEnum.ERROR);
			log.error(e.getMessage(), e);
		}
	}
}
