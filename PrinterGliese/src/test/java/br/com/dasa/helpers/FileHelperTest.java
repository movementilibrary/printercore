package br.com.dasa.helpers;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class FileHelperTest {

	private String url = "C://gliese//teste//impressora.properties";

	@Test
	public void testSalvarPropertiesDadosNulo() {

		try {
			FileHelper fileHelper = new FileHelper();
			Properties props = fileHelper.getProperties(url);
			fileHelper.salvarProperties(url, props, getMapaProperties(null, null, null));
			
			Properties propsRetorno = fileHelper.getProperties(url);
			Assert.assertTrue(propsRetorno.containsKey("cod.unidade") && String.valueOf(propsRetorno.get("cod.unidade")).isEmpty());
			Assert.assertTrue(propsRetorno.containsKey("cod.empresa") && String.valueOf(propsRetorno.get("cod.empresa")).isEmpty());
			Assert.assertTrue(propsRetorno.containsKey("nome.impressora") && String.valueOf(propsRetorno.get("nome.impressora")).isEmpty());
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSalvarPropertiesComSucesso() {
		try {
			
			String nomeImpressora = "Argox"; 
			String codUnidade = "123"; 
			String codEmpresa = "1"; 
			
			FileHelper fileHelper = new FileHelper();
			Properties props = fileHelper.getProperties(url); 
			fileHelper.salvarProperties(url, props, getMapaProperties(nomeImpressora, codUnidade, codEmpresa));
			
			Properties propsRetorno = fileHelper.getProperties(url);
			Assert.assertTrue(propsRetorno.containsKey("cod.unidade") && String.valueOf(propsRetorno.get("cod.unidade")).equals(codUnidade));
			Assert.assertTrue(propsRetorno.containsKey("cod.empresa") && String.valueOf(propsRetorno.get("cod.empresa")).equals(codEmpresa));
			Assert.assertTrue(propsRetorno.containsKey("nome.impressora") && String.valueOf(propsRetorno.get("nome.impressora")).equals(nomeImpressora));
			
		}catch(Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	private Map<String, String> getMapaProperties(String nomeImpressora, String codUnidade, String codEmpresa) {
		HashMap<String, String> mapa = new HashMap<>();
		mapa.put("cod.unidade", codUnidade);
		mapa.put("cod.empresa", codEmpresa);
		mapa.put("nome.impressora", nomeImpressora);
		return mapa;
	}



}
