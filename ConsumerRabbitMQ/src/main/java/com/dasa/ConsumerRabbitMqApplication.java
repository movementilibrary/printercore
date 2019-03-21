package com.dasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConsumerRabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerRabbitMqApplication.class, args);
	}

}
