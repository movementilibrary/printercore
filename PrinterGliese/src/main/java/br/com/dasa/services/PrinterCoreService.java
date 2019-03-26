package br.com.dasa.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.dasa.helpers.SOHelper;
import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.ImpressoraJson;
import br.com.dasa.jsons.UnidadeJson;

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
	
	public List<UnidadeJson> buscarUnidades(String codEmpresa){
		
		ResponseEntity<UnidadeJson[]> response = requestService.get(printerCoreUrl.concat("/empresas/").concat(
				codEmpresa).concat("/unidades"), UnidadeJson[].class); 
		
		if(response.getStatusCodeValue() == 200) {	
			return Arrays.asList(response.getBody());
		} else {
			log.error("Unidades não encontradas para a Empresa: ".concat(codEmpresa));
			return new ArrayList<>(); 
		}
	}
	
	public List<EmpresaJson> buscarEmpresas(){
		
		ResponseEntity<EmpresaJson[]> response = requestService.get(printerCoreUrl.concat("/empresas"), EmpresaJson.class); 
		
		if(response.getStatusCodeValue() == 200) {	
			return Arrays.asList(response.getBody());
		} else {
			log.error("Empresas não encontrada");
			return new ArrayList<>(); 
		}
	}
}
