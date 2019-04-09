package br.com.dasa.print.core.redis.service;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;


import br.com.dasa.print.core.redis.model.Impressao;
import br.com.dasa.print.core.redis.model.TipoEtiqueta;
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
	public String imprimir(Impressao impressao) {
		log.info("Imprimindo");
		log.info(impressao.getConteudoImpressao());
		String texto = impressao.getConteudoImpressao();

		int etqCont = texto.replaceAll("[^\250]", "").length();
		String[] risk = texto.split("\250");
		StringBuilder strHexa = new StringBuilder();

		String strInfoEtiqueta = buildInfoEtiqueta(impressao.getTipoEtiqueta());

		for (int i = 0; i < etqCont; ++i) {
			try {
				strHexa.append(formataEPL2(strInfoEtiqueta, risk[i]));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		log.debug("label result:\n{}", strHexa.toString());
		return strHexa.toString();
	}

	private String buildInfoEtiqueta(TipoEtiqueta tipoEtiqueta) {
		int formSize = (int) tipoEtiqueta.getHeight() * DOTS_PER_MILIMETER;
		int gapSize =  (int) tipoEtiqueta.getGap() * DOTS_PER_MILIMETER;
		int printerAreaSize = (int) tipoEtiqueta.getWidth() * DOTS_PER_MILIMETER;

		String header = String.format("JF%sQ%d,%d%sq%d%sN%s",
				LINE_FEED, formSize, gapSize,
				LINE_FEED, printerAreaSize, LINE_FEED, LINE_FEED);

		log.debug("header label:\n{}", header);
		return header;
	}

	/**
	 * CHAR	DEC	OCTAL
	 * ¾	190	276		=> caractere indicativo para quebra de linha em etiqueta
	 * ¨	168	250		=> caractere indicativo para fim de etiqueta
	 * @param strAbrir
	 * @param texto
	 * @return
	 */
	private String formataEPL2(String strAbrir, String texto) {
		String[] linhas = texto.split(Character.toString((char) 190));
		if (linhas[0].equals("Imagem")) {
			// imprimeImagem(linhas);
			return null;
		} else {
			String strHexa = "";

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

			String strFim = "P1" + LINE_FEED;
			strHexa = strHexa + strAbrir;
			strHexa = strHexa + "A225,10,1,3,1,1,N," + ASPAS + linhas[1] + ASPAS + LINE_FEED;
			strHexa = strHexa + "A200,10,1,3,1,1,N," + ASPAS + linhas[2] + ASPAS + LINE_FEED;
			strHexa = strHexa + "A175,10,1,3,1,1,N," + ASPAS + linhas[3] + ASPAS + LINE_FEED;
			if (linhas[0].equals("C128") && linhas[4].length() > 8) {
				strHexa = strHexa + "A150,10,1,1,1,1,N," + ASPAS + linhas[4] + ASPAS + LINE_FEED;
			} else {
				strHexa = strHexa + "A150,10,1,3,1,1,N," + ASPAS + linhas[4] + ASPAS + LINE_FEED;
			}

			if (linhas[0].equals("C128")) {
				strHexa = strHexa + "A100,10,1,3,1,1,N," + ASPAS + linhas[6] + ASPAS + LINE_FEED;
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				if (linhas[6].contains("DL")) {
					strHexa = strHexa + "A100,10,1,3,1,1,R," + ASPAS + linhas[6] + ASPAS + LINE_FEED;
				} else {
					strHexa = strHexa + "A100,10,1,3,1,1,N," + ASPAS + linhas[6] + ASPAS + LINE_FEED;
				}
			}

			if (linhas[0].equals("I2o5") || linhas[0].equals("DAS")) {
				strHexa = strHexa + "B80,90,1," + strProg + ",2,6,64,N," + ASPAS + linhas[5] + ASPAS + LINE_FEED;
			}

			if (linhas[0].equals("C128")) {
				strHexa = strHexa + "B80,10,1," + strProg + ",2,6,64,N," + ASPAS + linhas[5] + ASPAS + LINE_FEED;
			}

			strHexa = strHexa + "A125,10,1,3,1,1,N," + ASPAS + linhas[7] + ASPAS + LINE_FEED;
			strHexa = strHexa + strFim;

			return strHexa;
		}
	}

	private String asciiToChar(int intValor) {
		String aChar = (new Character((char) intValor)).toString();
		return aChar;
	}

	public static void main(String[] args) {
		PrinterService service = new PrinterService();

		Impressao impressao = new Impressao("DAP",
				"I2o5¾        FINAL DE LOTE         ¾Testes Processos de Atendiment¾888/1184¾QTD REC: 2  LEITO: 11¾¾NUM CHAMADA: null¾COLETADOR: ¾¨I2o5¾        FINAL DE LOTE         ¾Testes Processos de Atendiment¾888/1184¾QTD REC: 2  LEITO: 11¾¾NUM CHAMADA: null¾COLETADOR: ¾¨",
				TipoEtiqueta.PORTRAIT);
		service.imprimir(impressao);
	}

}
