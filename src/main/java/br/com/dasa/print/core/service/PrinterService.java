package br.com.dasa.print.core.service;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;


import br.com.dasa.print.core.redis.model.Impressao;
import br.com.dasa.print.core.type.TipoEtiquetaType;
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
		log.info(impressao.getConteudo_impressao());
		String texto = impressao.getConteudo_impressao();

		int etqCont = texto.replaceAll("[^\250]", "").length();
		String[] risk = texto.split("\250");
		StringBuilder strHexa = new StringBuilder();

		for (int i = 0; i < etqCont; ++i) {
			try {
				strHexa.append(formataEPL2(impressao.getTipo_etiqueta(), risk[i]));
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
	 * @param tipoEtiquetaType
	 * @param texto
	 * @return conteudo EPL2
	 */
	private String formataEPL2(TipoEtiquetaType tipoEtiquetaType, String texto) {
		String header = buildInfoEtiqueta(tipoEtiquetaType);
		if (tipoEtiquetaType == TipoEtiquetaType.PORTRAIT) {
			return portraitEPL(header, texto);
		} else {
			return landscapeEPL(header, texto);
		}
	}

	private String buildInfoEtiqueta(TipoEtiquetaType tipoEtiquetaType) {
		int formSize = (int) tipoEtiquetaType.getHeight() * DOTS_PER_MILIMETER;
		int gapSize =  (int) tipoEtiquetaType.getGap() * DOTS_PER_MILIMETER;
		int printerAreaSize = (int) tipoEtiquetaType.getWidth() * DOTS_PER_MILIMETER;

		String header = String.format("JF%sQ%d,%d%sq%d%sN%s",
				LINE_FEED, formSize, gapSize,
				LINE_FEED, printerAreaSize, LINE_FEED, LINE_FEED);

		log.debug("header label:\n{}", header);
		return header;
	}

	private String portraitEPL(String header, String texto) {
		String[] linhas = texto.split(Character.toString((char) 190));
		if (linhas[0].equals("Imagem")) {
			// imprimeImagem(linhas);
			return null;
		} else {
			String strHexa = header;
			String strProg = getStrProg(linhas[0]);
			int x = 225;
			int xdelta = 25;
			String asciiTemplate = "A%d,10,1,2,1,1,N,\"%s\"\n";
			for(int i = 1; i < linhas.length; i++) {
				if (i == linhas.length - 1) {
					strHexa += "B" + x + ",10,1," + strProg + ",2,6,64,N," + ASPAS + linhas[5] + ASPAS + LINE_FEED;
				} else {
					strHexa += String.format(asciiTemplate, x, linhas[i]);
				}
				x -= xdelta;
			}
			strHexa += "P1\n";
			return strHexa;
		}
	}

	private String landscapeEPL(String header, String texto) {
		String[] linhas = texto.split(Character.toString((char) 190));
		if (linhas[0].equals("Imagem")) {
			// imprimeImagem(linhas);
			return null;
		} else {
			String strHexa = header;
			String strProg = getStrProg(linhas[0]);
			int y = 10;
			int ydelta = 25;
			String asciiTemplate = "A10,%d,0,2,1,1,N,\"%s\"\n";
			String strFim = "P1\n";
			for (int i = 1; i < linhas.length; i++) {
				if (i == linhas.length - 1) {
					strHexa += "B10,155,0," + strProg + ",2,6,64,N," + ASPAS + linhas[5] + ASPAS + LINE_FEED;
				} else {
					strHexa += String.format(asciiTemplate, y, linhas[1]);
				}
				y += ydelta;
			}
			strHexa += strFim;
			return strHexa;
		}
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

	private String asciiToChar(int intValor) {
		String aChar = (new Character((char) intValor)).toString();
		return aChar;
	}

	public static void main(String[] args) {
		PrinterService service = new PrinterService();

		Impressao impressao = new Impressao("DAP",
				"I2o5¾        FINAL DE LOTE         ¾Testes Processos de Atendiment¾888/1184¾QTD REC: 2  LEITO: 11¾¾NUM CHAMADA: null¾COLETADOR: ¾¨I2o5¾        FINAL DE LOTE         ¾Testes Processos de Atendiment¾888/1184¾QTD REC: 2  LEITO: 11¾¾NUM CHAMADA: null¾COLETADOR: ¾¨",
				TipoEtiquetaType.PORTRAIT);
		service.convertToEPL2(impressao);
	}

}
