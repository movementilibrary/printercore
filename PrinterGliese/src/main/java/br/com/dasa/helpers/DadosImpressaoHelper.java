package br.com.dasa.helpers;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.dasa.exceptions.ConfigurationNotFoundException;

@Component
public class DadosImpressaoHelper {

	private static final Logger log = LoggerFactory.getLogger(DadosImpressaoHelper.class);

	@Value("${properties.impressao}")
	private String urlPropertiesImpressao;
	@Autowired
	private FileHelper fileHelper;

	private String codUnidade;

	private String codEmpresa;

	public DadosImpressaoHelper() {

	}

	public DadosImpressaoHelper(String urlPropertiesImpressao, FileHelper fileHelper) {
		this.urlPropertiesImpressao = urlPropertiesImpressao;
		this.fileHelper = fileHelper;
	}

	public boolean validarDadosImpressoraPreenchidos() {
		try {
			Properties prop = fileHelper.getProperties(urlPropertiesImpressao);
			codUnidade = prop != null && prop.containsKey("cod.unidade")
					? String.valueOf(prop.get("cod.unidade"))
					: "";
			codEmpresa = prop != null && prop.containsKey("cod.empresa")
					? String.valueOf(prop.get("cod.empresa"))
					: "";
			return !(codEmpresa.isEmpty() || codUnidade.isEmpty());
		} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new ConfigurationNotFoundException("Arquivo de configuração não encontrado para o aplicativo, o arquivo deve existir na pasta c: gliese/impressao.properties", e);  
		}

	}
	
	public String codUnidade() {
		return codUnidade; 
	}
	
	public String codEmpresa() {
		return codEmpresa; 
	}

}
