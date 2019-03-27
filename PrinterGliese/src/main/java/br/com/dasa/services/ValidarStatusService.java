package br.com.dasa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.dasa.helpers.DadosImpressaoHelper;

@Service
public class ValidarStatusService {
	
	private static final Logger log = LoggerFactory.getLogger(ValidarStatusService.class);
	
	@Autowired
	private PrinterCoreService printerCoreService;
	@Autowired
	private DadosImpressaoHelper dadosImpressaoHelper; 

	
	@Scheduled(fixedRate = 60000 * 120)
	public void validarStatus() {
		try {
			if(dadosImpressaoHelper.validarDadosImpressoraPreenchidos()) {
				printerCoreService.mostrarClientComoAtivo();
			}
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
