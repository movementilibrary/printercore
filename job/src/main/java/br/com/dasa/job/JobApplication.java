package br.com.dasa.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JobApplication.class, args);
		LOGGER.info("Job Iniciado com Sucesso ");
	}

}
