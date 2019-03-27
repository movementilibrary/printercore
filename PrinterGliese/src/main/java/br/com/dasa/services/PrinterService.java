package br.com.dasa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {

	private static final Logger log = LoggerFactory.getLogger(PrinterService.class);
	
	@Async
	public void imprimir(String texto) {
		log.info("Imprimindo");
		log.info(texto);
	}
}
