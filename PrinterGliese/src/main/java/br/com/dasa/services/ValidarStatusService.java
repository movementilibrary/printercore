package br.com.dasa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.dasa.controllers.components.LogComponent;
import br.com.dasa.enums.LogEnum;
import br.com.dasa.helpers.DadosImpressaoHelper;
import javafx.application.Platform;

@Service
public class ValidarStatusService {

	private static final Logger log = LoggerFactory.getLogger(ValidarStatusService.class);

	@Autowired
	private PrinterCoreService printerCoreService;
	@Autowired
	private DadosImpressaoHelper dadosImpressaoHelper;
	@Autowired
	private LogComponent logComponent;

	@Scheduled(fixedRate = 60000 * 120)
	public void validarStatus() {
		try {
			if (dadosImpressaoHelper.validarDadosImpressoraPreenchidos()) {
				Platform.runLater(() -> {
					logComponent.addLog("Validando status no Printer Core", LogEnum.INFO);
				});
				printerCoreService.mostrarClientComoAtivo();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Platform.runLater(() -> {
				logComponent.addLog("Erro ao validar status no Printer Core: ".concat(e.getMessage()), LogEnum.ERROR);
			});
		}
	}
}
