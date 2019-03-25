package com.dasa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dasa.jsons.MensagemJson;
import com.dasa.jsons.QueueJson;
import com.dasa.services.RabbitMQQueuesService;

@Controller
@RequestMapping("/api/queue")
public class MessageController {

    @Autowired
    private RabbitMQQueuesService rabbitMQQueueService;

    @Autowired
    public MessageController(RabbitMQQueuesService rabbitMQQueueService){
        this.rabbitMQQueueService = rabbitMQQueueService;

    }
    
    @GetMapping("/{queueId}")
    public ResponseEntity queueExiste(@PathVariable String queueId) {
    	
    	rabbitMQQueueService.criarFila(queueId);
    	return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity criarMensagem(@RequestBody MensagemJson json){

        this.rabbitMQQueueService.enviarMsgs(json);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity criarFila(@RequestBody QueueJson json){

        rabbitMQQueueService.criarFila(json.getNome());
        return new ResponseEntity(HttpStatus.CREATED); 
    }
    
    @DeleteMapping
    public ResponseEntity apagarFila(@RequestBody QueueJson json) {
    	rabbitMQQueueService.apagarFila(json.getNome());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
