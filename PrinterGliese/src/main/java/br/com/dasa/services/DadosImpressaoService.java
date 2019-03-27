package br.com.dasa.services;

import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.util.StringUtils;

import br.com.dasa.consumers.ConsumerMQ;
=======

>>>>>>> 99dee9c8fae28b89d35ac48b9065ef2f2918c2d4
import br.com.dasa.dtos.ImpressoraDTO;
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
<<<<<<< HEAD
	private PrinterCoreService printerCoreService;
	@Autowired
	private ConsumerMQ consumerMQ; 
=======
	private PrinterCoreService printerCoreService; 
>>>>>>> 99dee9c8fae28b89d35ac48b9065ef2f2918c2d4
	
	public void salvarDadosImpressao(ImpressoraDTO impressoraDTO, EmpresaJson empresaJson, UnidadeJson unidadeJson) {
		try {
			Properties props = fileHelper.getProperties(urlPropertiesImpressao);
			fileHelper.salvarProperties(urlPropertiesImpressao, props, getMapaProperties(empresaJson, unidadeJson));
			salvarAlteracoesNoPrinterCore(impressoraDTO, empresaJson, unidadeJson);
<<<<<<< HEAD
			consumerMQ.consome();
=======
>>>>>>> 99dee9c8fae28b89d35ac48b9065ef2f2918c2d4
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void salvarAlteracoesNoPrinterCore(ImpressoraDTO impressoraDTO, EmpresaJson empresaJson,
			UnidadeJson unidadeJson) throws Exception {
<<<<<<< HEAD
		ImpressoraJson json = new ImpressoraJson(soHelper.getMacAddress(), StringUtils.isEmpty(impressoraDTO.getNome()) ? impressoraDTO.getNomeRede() : impressoraDTO.getNome() , empresaJson.getCod(), unidadeJson.getMnemonico());
=======
		ImpressoraJson json = new ImpressoraJson(soHelper.getMacAddress(), impressoraDTO.getNome(), empresaJson.getCod(), unidadeJson.getMnemonico());
>>>>>>> 99dee9c8fae28b89d35ac48b9065ef2f2918c2d4
		printerCoreService.criarDadosParaImpressao(json);
	}

	private HashMap<String, String> getMapaProperties(EmpresaJson empresaJson, UnidadeJson unidadeJson) {
		HashMap<String, String> mapaProperties = new HashMap<>();
		mapaProperties.put("cod.unidade", unidadeJson.getMnemonico());
		mapaProperties.put("cod.empresa", empresaJson.getCod());
		return mapaProperties;
	}
}
