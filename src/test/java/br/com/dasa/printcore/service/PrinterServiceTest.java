package br.com.dasa.printcore.service;

import br.com.dasa.printcore.redis.model.Impressao;
import br.com.dasa.printcore.type.TipoEtiquetaType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrinterServiceTest {

    private PrinterService service;

    @Before
    public void init() {
        service = new PrinterService();
    }

    @Test
    public void convertToEPL2() {
        Impressao impressao = new Impressao("DAP",
                "I2o5�        FINAL DE LOTE         �Testes Processos de Atendiment�888/1184�QTD REC: 2  LEITO: 11��NUM CHAMADA: null�COLETADOR: ��",
                TipoEtiquetaType.PORTRAIT);
        String epl = service.convertToEPL2(impressao);
        assertTrue(epl.contains("Q440,24"));
    }

    @Test
    public void convertToEPL2Landscape() {
        Impressao impressao = new Impressao("DAP",
                "I2o5�        FINAL DE LOTE         �Testes Processos de Atendiment�888/1184�QTD REC: 2  LEITO: 11��NUM CHAMADA: null�COLETADOR: ��",
                TipoEtiquetaType.LANDSCAPE);
        String epl = service.convertToEPL2(impressao);
        assertTrue(epl.contains("240,24"));
    }

}