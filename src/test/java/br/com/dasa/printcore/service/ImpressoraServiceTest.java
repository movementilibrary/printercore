package br.com.dasa.printcore.service;

import br.com.dasa.printcore.exception.ResourceNotFoundException;
import br.com.dasa.printcore.redis.model.Impressora;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
public class ImpressoraServiceTest {

    @Autowired
    private ImpressoraService impressoraService;

    private Impressora impressora;

    @Before
    public void setup (){
        impressora = new Impressora();
        impressora.setEmpresa("Alta");
        impressora.setMacaddress("0-0-0-0-0-0");
        impressora.setNome("Portaria");
        impressora.setUnidade("BRA");
        impressora.setId("BRA-0-0-0-0-0-0");
    }


    @Test
    public void deveCriarId(){
        String impressoraId = impressoraService.criaIdImpressora(impressora.getUnidade(), impressora.getMacaddress());
        assertEquals("BRA-0-0-0-0-0-0", impressoraId);
    }

    @Test
    public void deveCriarImpressora(){
        Impressora impressoraCriada = impressoraService.criaImpressora(impressora);
        assertEquals(impressoraCriada.getEmpresa(), "Alta");
        assertEquals(impressoraCriada.getId(), "BRA-0-0-0-0-0-0");
    }

    @Test
    public void deveListaImpressoraPorCodigodaEmpresa(){
        List<Impressora> impressoras = impressoraService.listaTodasImpressoras(impressora.getId());
        assertEquals(1, impressoras.size());
    }



    @Test(expected = ResourceNotFoundException.class)
    public void deveRetornarImpressoraPeloId(){
        Impressora impressora = impressoraService.buscaImpressoraPeloId("BRA-0-0-0-0-0-0");
        assertEquals(impressora.getId(), "BRA-0-0-0-0-0-0");
    }


    @Test
    public void deveExcluirImpressora(){
        impressoraService.excluiImpressora("BRA-0-0-0-0-0-0");
    }





}
