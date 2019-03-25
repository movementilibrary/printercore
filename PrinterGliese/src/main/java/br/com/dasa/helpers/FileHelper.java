package br.com.dasa.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	
}
