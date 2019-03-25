package br.com.dasa.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.dasa.helpers.SOHelper;
import br.com.dasa.jsons.ImpressoraJson;

@Service
public class PrinterCoreService {

	private static final Logger log = LoggerFactory.getLogger(PrinterCoreService.class);

	@Autowired
	private RequestService requestService;
	@Autowired
	private SOHelper soHelper;

	@Value("${printer.core.url}")
	private String printerCoreUrl;

	private String macAddress;

	@PostConstruct
	public void iniciar() {
		try {
			this.macAddress = soHelper.getMacAddress();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void mostrarClientComoAtivo() {
		requestService.get(printerCoreUrl.concat("/api/queue/").concat(this.macAddress), String.class);
	}
	
	public void criarDadosParaImpressao(ImpressoraJson json) {
		requestService.post(printerCoreUrl.concat("/api/queue"), json, ImpressoraJson.class); 
	}
}
