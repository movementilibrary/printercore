package com.dasa;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class PocRabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocRabbitMqApplication.class, args);
	}

}
