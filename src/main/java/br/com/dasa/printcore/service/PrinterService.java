package br.com.dasa.printcore.service;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;


import br.com.dasa.printcore.redis.model.Impressao;
import br.com.dasa.printcore.type.TipoEtiquetaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {

	private static final Logger log = LoggerFactory.getLogger(PrinterService.class);
	private static final String ASPAS = Character.toString((char)  34);
	private static final String LINE_FEED = Character.toString((char)  10);;
	public static final int DOTS_PER_MILIMETER = 8;

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
	public String convertToEPL2(Impressao impressao) {
		log.info("Imprimindo");
		log.info(impressao.getConteudoImpressao());
		String texto = impressao.getConteudoImpressao();

		int etqCont = texto.replaceAll("[^\250]", "").length();
		String[] risk = texto.split("\250");
		StringBuilder strHexa = new StringBuilder();

		for (int i = 0; i < etqCont; ++i) {
			try {
				strHexa.append(formataEPL2(impressao.getTipoEtiqueta(), risk[i]));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		log.debug("label result:\n{}", strHexa.toString());
		return strHexa.toString();
	}

	/**
	 * CHAR	DEC	OCTAL
	 * ¾	190	276		=> caractere indicativo para quebra de linha em etiqueta
	 * ¨	168	250		=> caractere indicativo para fim de etiqueta
	 * @param tipoEtiqueta
	 * @param texto
	 * @return conteudo EPL2
	 */
	private String formataEPL2(TipoEtiquetaType tipoEtiqueta, String texto) {
		String header = buildInfoEtiqueta(tipoEtiqueta);
		if (tipoEtiqueta == TipoEtiquetaType.PORTRAIT) {
			return portraitEPL(header, texto);
		} else {
			return landscapeEPL(header, texto);
		}
	}

	private String buildInfoEtiqueta(TipoEtiquetaType tipoEtiqueta) {
		int formSize = (int) tipoEtiqueta.getHeight() * DOTS_PER_MILIMETER;
		int gapSize =  (int) tipoEtiqueta.getGap() * DOTS_PER_MILIMETER;
		int printerAreaSize = (int) tipoEtiqueta.getWidth() * DOTS_PER_MILIMETER;

		String header = String.format("JF%sQ%d,%d%sq%d%sN%s",
				LINE_FEED, formSize, gapSize,
				LINE_FEED, printerAreaSize, LINE_FEED, LINE_FEED);

		log.debug("header label:\n{}", header);
		return header;
	}

	private String portraitEPL(String strAbrir, String texto) {
		String[] linhas = texto.split(Character.toString((char) 190));
		if (linhas[0].equals("Imagem")) {
			return imprimeImagem(linhas);
		} else {
			String strHexa = strAbrir;
			String strAspas = "\"";
			String strPula = "\n";
			String strProg = getStrProg(linhas[0]);

			String strFim = "P1" + strPula;
			strHexa += "A225,10,1,3,1,1,N," + strAspas + linhas[1] + strAspas + strPula;
			strHexa += "A200,10,1,3,1,1,N," + strAspas + linhas[2] + strAspas + strPula;
			strHexa += "A175,10,1,3,1,1,N," + strAspas + linhas[3] + strAspas + strPula;
			if (linhas[0].equals("C128") && linhas[4].length() > 8) {
				strHexa += "A150,10,1,1,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
			} else {
				strHexa += "A150,10,1,3,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
			}

			if (linhas[0].equals("C128")) {
				strHexa += "A100,10,1,3,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				if (linhas[6].contains("DL")) {
					strHexa += "A100,10,1,3,1,1,R," + strAspas + linhas[6] + strAspas + strPula;
				} else {
					strHexa += "A100,10,1,3,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
				}
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				strHexa += "B80,90,1," + strProg + ",2,6,64,N," + strAspas + linhas[5] + strAspas + strPula;
			}

			if (linhas[0].equals("C128")) {
				strHexa += "B80,10,1," + strProg + ",2,6,64,N," + strAspas + linhas[5] + strAspas + strPula;
			}

			strHexa += "A125,10,1,3,1,1,N," + strAspas + linhas[7] + strAspas + strPula;
			strHexa += strFim;
			return strHexa;
		}
	}

	private String landscapeEPL(String strAbrir, String texto) {
		String[] linhas = texto.split(Character.toString((char) 190));
		if (linhas[0].equals("Imagem")) {
			return imprimeImagem(linhas);
		} else {
			String strHexa = strAbrir;
			String strAspas = "\"";
			String strPula = "\n";
			String strProg = getStrProg(linhas[0]);

			String strFim = "P1" + strPula;
			strHexa += "A10,10,0,3,1,1,N," + strAspas + linhas[1] + strAspas + strPula;
			strHexa += "A10,35,0,3,1,1,N," + strAspas + linhas[2] + strAspas + strPula;
			strHexa += "A10,60,0,3,1,1,N," + strAspas + linhas[3] + strAspas + strPula;
			if (linhas[0].equals("C128") && linhas[4].length() > 8) {
				strHexa += "A10,85,0,1,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
			} else {
				strHexa += "A10,85,0,3,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
			}

			if (linhas[0].equals("C128")) {
				strHexa += "A10,135,0,3,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
			} else {
				if (linhas[6].contains("DL")) {
					strHexa += "A10,135,0,3,1,1,R," + strAspas + linhas[6] + strAspas + strPula;
				} else {
					strHexa += "A10,135,0,3,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
				}
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				strHexa += "B10,160,0," + strProg + ",2,6,64,N," + strAspas + linhas[5] + strAspas + strPula;
			}

			if (linhas[0].equals("C128")) {
				strHexa += "B10,160,0," + strProg + ",2,6,64,N," + strAspas + linhas[5] + strAspas + strPula;
			}

			strHexa += "A10,110,0,3,1,1,N," + strAspas + linhas[7] + strAspas + strPula + strFim;
			return strHexa;
		}
	}

	public String imprimeImagem(String[] linhas) {
		String strHexa = "";
		String strAspas = "\"";
		String strPula = "\n";
		String strAbrir = "JF" + strPula + "Q280,25" + strPula + "q720" + strPula + "N" + strPula;
		String strFim = "P1" + strPula;
		strHexa += strAbrir;
		strHexa += "B315,225,2,1,2,7,40,B," + strAspas + linhas[9] + strAspas + strPula;
		strHexa += "A700,50,2,2,1,1,N," + strAspas + linhas[8] + strAspas + strPula;
		strHexa += "A700,75,2,2,1,1,N," + strAspas + linhas[7] + strAspas + strPula;
		strHexa += "A700,100,2,2,1,1,N," + strAspas + linhas[6] + strAspas + strPula;
		strHexa += "A700,125,2,2,1,1,N," + strAspas + linhas[5] + strAspas + strPula;
		strHexa += "A700,150,2,2,1,1,N," + strAspas + linhas[4] + strAspas + strPula;
		strHexa += "A700,175,2,2,1,1,N," + strAspas + linhas[3] + strAspas + strPula;
		strHexa += "A700,200,2,2,1,1,N," + strAspas + linhas[2] + strAspas + strPula;
		strHexa += "LO310,225,390,5" + strPula;
		strHexa += "A700,250,2,2,1,1,N," + strAspas + linhas[1] + strAspas + strPula;
		strHexa += strFim;
		strHexa += "B400,250,2,1,2,7,40,B," + strAspas + linhas[9] + strAspas + strPula;
		strHexa += "A700,50,2,2,1,1,N," + strAspas + linhas[2] + strAspas + strPula;
		strHexa += "A700,125,2,2,1,1,N," + strAspas + linhas[7] + strAspas + strPula;
		strHexa += "A700,175,2,2,1,1,N," + strAspas + linhas[1] + strAspas + strPula;
		strHexa += strFim;
		return strHexa;
	}


	private String getStrProg(String linha) {
		if (linha.equals("DAS")) {
			return "2";
		}
		if (linha.equals("C128")) {
			return "1";
		}
		return "2C";
	}

}
