package br.com.dasa.print.core.redis.service;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {

	private static final Logger log = LoggerFactory.getLogger(PrinterService.class);

	private PrintService printService;

	@PostConstruct
	public void setup() {
		try {
			DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, (AttributeSet) null);
			for (PrintService p : ps) {
				if (p.getName().equals("Zebra")) {
					printService = p;
					break;
				}
			}
		} catch (Exception e) {
			log.error("Erro ao obter servico de impressora", e);
		}
	}

	@Async
	public String imprimir(String texto) {
		log.info("Imprimindo");
		log.info(texto);

		int etqCont = texto.replaceAll("[^¨]", "").length();
		String[] risk = texto.split("¨");
		StringBuilder strHexa = new StringBuilder();

		for (int i = 0; i < etqCont; ++i) {
			try {
				log.info("Any printer");
				strHexa.append(formataZebra(risk[i]));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}


		return strHexa.toString();
	}

	public String formataAscII(String texto) {
		String strHexa = "";
		String[] linhas = texto.split("¾");

		for (int i = 0; i < linhas.length; ++i) {
			strHexa = strHexa + linhas[i] + "\n\r";
		}

		strHexa = strHexa + "\n\r\t";
		return strHexa;
	}

	private String formataZebra(String texto) {
		String[] linhas = texto.split("¾");
		if (linhas[0].equals("Imagem")) {
			// imprimeImagem(linhas);
			return null;
		} else {
			String strHexa = "";
			String strAspas = asciiToChar(34);
			String strPula = asciiToChar(10);
			String strProg = new String();
			if (linhas[0].equals("I2o5")) {
				strProg = "2C";
			}

			if (linhas[0].equals("DAS")) {
				strProg = "2";
			}

			if (linhas[0].equals("C128")) {
				strProg = "1";
			}

			String strAbrir = "JF" + strPula + "Q440,25" + strPula + "q248" + strPula + "N" + strPula;
			String strFim = "P1" + strPula;
			strHexa = strHexa + strAbrir;
			strHexa = strHexa + "A225,10,1,3,1,1,N," + strAspas + linhas[1] + strAspas + strPula;
			strHexa = strHexa + "A200,10,1,3,1,1,N," + strAspas + linhas[2] + strAspas + strPula;
			strHexa = strHexa + "A175,10,1,3,1,1,N," + strAspas + linhas[3] + strAspas + strPula;
			if (linhas[0].equals("C128") && linhas[4].length() > 8) {
				strHexa = strHexa + "A150,10,1,1,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
			} else {
				strHexa = strHexa + "A150,10,1,3,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
			}

			if (linhas[0].equals("C128")) {
				strHexa = strHexa + "A100,10,1,3,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				if (linhas[6].contains("DL")) {
					strHexa = strHexa + "A100,10,1,3,1,1,R," + strAspas + linhas[6] + strAspas + strPula;
				} else {
					strHexa = strHexa + "A100,10,1,3,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
				}
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				strHexa = strHexa + "B80,90,1," + strProg + ",2,6,64,N," + strAspas + linhas[5] + strAspas + strPula;
			}

			if (linhas[0].equals("C128")) {
				strHexa = strHexa + "B80,10,1," + strProg + ",2,6,64,N," + strAspas + linhas[5] + strAspas + strPula;
			}

			strHexa = strHexa + "A125,10,1,3,1,1,N," + strAspas + linhas[7] + strAspas + strPula;
			strHexa = strHexa + strFim;

			return strHexa;
		}
	}

	private String asciiToChar(int intValor) {
		String aChar = (new Character((char) intValor)).toString();
		return aChar;
	}

}
