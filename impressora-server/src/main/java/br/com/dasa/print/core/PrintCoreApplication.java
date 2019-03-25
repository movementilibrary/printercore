package br.com.dasa.print.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableRabbit
public class PrintCoreApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrintCoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PrintCoreApplication.class, args);
		LOGGER.info("Printer Server started!");
	}

}
