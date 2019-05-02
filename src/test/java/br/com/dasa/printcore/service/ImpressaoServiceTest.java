package br.com.dasa.printcore.service;

import br.com.dasa.printcore.exception.ResourceNotFoundException;
import br.com.dasa.printcore.redis.model.Impressao;
import br.com.dasa.printcore.redis.model.Impressora;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
public class ImpressaoServiceTest {

    @Autowired
    private ImpressaoService impressaoService;

    @Autowired
    private ImpressoraService impressoraService;

    private Impressao impressao;
    private Impressora impressora;

    @Before
    public void setup(){
        impressao = new Impressao();
        impressao.setConteudoImpressao("I2o5?        FINAL DE LOTE         ?Testes Processos de Atendiment?888/1184?QTD REC: 2  LEITO: 11??NUM CHAMADA: null?COLETADOR: ??I2o5?        FINAL DE LOTE         ?Outra etiqueta?888/1184?QTD REC: 2  LEITO: 11??NUM CHAMADA: null?COLETADOR: ??");
        impressao.setImpressora("BRA-0-0-0-0-0-0");


        impressora = new Impressora();
        impressora.setEmpresa("Alta");
        impressora.setMacaddress("0-0-0-0-0-0");
        impressora.setNome("Portaria");
        impressora.setUnidade("BRA");
        impressora.setId("BRA-0-0-0-0-0-0");
    }


    @Test
    public void deveCriarImpressora(){
        Impressora impressoraCriada = impressoraService.criaImpressora(impressora);
        assertEquals(impressoraCriada.getEmpresa(), "Alta");
        assertEquals(impressoraCriada.getId(), "BRA-0-0-0-0-0-0");
    }

    @Test
    public void devePrepararConteudoImpressao (){
        impressaoService.preparaConteudoAntesImpressao(impressao);
        assertEquals("BRA-0-0-0-0-0-0", impressao.getImpressora());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deveExcluirImpressora(){
        impressoraService.excluiImpressora("BRA-0-0-0-0-0-0");
        deveRetornarImpressoraPeloId();
    }

    public void deveRetornarImpressoraPeloId(){
        Impressora impressora = impressoraService.buscaImpressoraPeloId("BRA-0-0-0-0-0-0");

    }
}
