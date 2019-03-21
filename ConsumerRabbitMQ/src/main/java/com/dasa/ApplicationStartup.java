package com.dasa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.dasa.consumers.ConsumerMQ;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
    private ConsumerMQ consumerMQ; 

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        consumerMQ.consome();
    }

}