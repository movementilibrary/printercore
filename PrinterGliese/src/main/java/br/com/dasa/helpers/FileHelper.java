package br.com.dasa.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class FileHelper {

	public Properties getProperties(String url) throws IOException {
		Properties props = new Properties();

		FileInputStream file = new FileInputStream(url);

		props.load(file);

		return props;
	}

	public void salvarProperties(String url, Properties props, Map<String, String> mapaProperties) throws IOException {
		mapaProperties.keySet().forEach(k -> props.setProperty(k, mapaProperties.get(k)));
		salvarProperties(url, props);
	}

	private void salvarProperties(String url, Properties props) throws IOException {
		File file = new File(url);
		FileOutputStream fos = new FileOutputStream(file);
		props.store(fos, "Configuracoes impressora");
	}

}
