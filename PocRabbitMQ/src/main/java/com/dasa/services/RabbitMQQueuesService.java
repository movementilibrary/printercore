package com.dasa.services;

import com.dasa.jsons.MensagemJson;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQQueuesService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    public RabbitMQQueuesService(RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin){
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
    }


    public void criarFila(String nome){
        rabbitAdmin.declareQueue(new Queue(nome));
    }
    

    public void apagarFila(String nome){
        rabbitAdmin.deleteQueue(nome);
    }

    public void enviarMsgs(MensagemJson json){
        rabbitTemplate.convertAndSend(json.getQueue(), json.getMsg());
    }

}
