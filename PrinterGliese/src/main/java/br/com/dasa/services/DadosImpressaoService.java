package br.com.dasa.services;

import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.dasa.dtos.ImpressoraDTO;
import br.com.dasa.helpers.FileHelper;
import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.UnidadeJson;

@Service
public class DadosImpressaoService {

	private static final Logger log = LoggerFactory.getLogger(DadosImpressaoService.class);
	
	@Value("${properties.impressao}")
	private String urlPropertiesImpressao;
	@Autowired
	private FileHelper fileHelper;

	public void salvarDadosImpressao(ImpressoraDTO impressoraDTO, EmpresaJson empresaJson, UnidadeJson unidadeJson) {
		try {
			Properties props = fileHelper.getProperties(urlPropertiesImpressao);
			fileHelper.salvarProperties(urlPropertiesImpressao, props, getMapaProperties(empresaJson, unidadeJson));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private HashMap<String, String> getMapaProperties(EmpresaJson empresaJson, UnidadeJson unidadeJson) {
		HashMap<String, String> mapaProperties = new HashMap<>();
		mapaProperties.put("cod.unidade", unidadeJson.getMnemonico());
		mapaProperties.put("cod.empresa", empresaJson.getCod());
		return mapaProperties;
	}
}
