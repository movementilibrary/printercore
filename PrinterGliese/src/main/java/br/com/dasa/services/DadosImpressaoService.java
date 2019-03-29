package br.com.dasa.services;

import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.dasa.consumers.ConsumerMQ;
import br.com.dasa.controllers.components.LogComponent;
import br.com.dasa.dtos.ImpressoraDTO;
import br.com.dasa.enums.LogEnum;
import br.com.dasa.helpers.FileHelper;
import br.com.dasa.helpers.SOHelper;
import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.ImpressoraJson;
import br.com.dasa.jsons.UnidadeJson;

@Service
public class DadosImpressaoService {

	private static final Logger log = LoggerFactory.getLogger(DadosImpressaoService.class);
	
	@Value("${properties.impressao}")
	private String urlPropertiesImpressao;
	@Autowired
	private FileHelper fileHelper;
	@Autowired
	private SOHelper soHelper; 
	@Autowired
	private PrinterCoreService printerCoreService;
	@Autowired
	private ConsumerMQ consumerMQ; 
	@Autowired
	private LogComponent logComponent; 

	
	public void salvarDadosImpressao(ImpressoraDTO impressoraDTO, EmpresaJson empresaJson, UnidadeJson unidadeJson) {
		try {
			Properties props = fileHelper.getProperties(urlPropertiesImpressao);
			fileHelper.salvarProperties(urlPropertiesImpressao, props, getMapaProperties(impressoraDTO, empresaJson, unidadeJson));
			salvarAlteracoesNoPrinterCore(impressoraDTO, empresaJson, unidadeJson);
			
			consumerMQ.consome();
			logComponent.addLog("Salvando Impressão", LogEnum.INFO);
		} catch (Exception e) {
			logComponent.addLog("Erro ao salvar dados de impressão: ".concat(e.getMessage()), LogEnum.ERROR);
			log.error(e.getMessage(), e);
		}
	}

	private void salvarAlteracoesNoPrinterCore(ImpressoraDTO impressoraDTO, EmpresaJson empresaJson,
			UnidadeJson unidadeJson) throws Exception {

		ImpressoraJson json = new ImpressoraJson(soHelper.getMacAddress(), StringUtils.isEmpty(impressoraDTO.getNome()) ? impressoraDTO.getNomeRede() : impressoraDTO.getNome() , empresaJson.getCod(), unidadeJson.getMnemonico());

		printerCoreService.criarDadosParaImpressao(json);
	}

	private HashMap<String, String> getMapaProperties(ImpressoraDTO impressoraDTO, EmpresaJson empresaJson, UnidadeJson unidadeJson) {
		HashMap<String, String> mapaProperties = new HashMap<>();
		mapaProperties.put("cod.unidade", unidadeJson.getMnemonico());
		mapaProperties.put("cod.empresa", empresaJson.getCod());
		mapaProperties.put("nome.impressora", impressoraDTO.getNomeRede());
		return mapaProperties;
	}
}
