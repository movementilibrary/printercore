package br.com.dasa.print.core.resource.ImpressoraResourceTest;

import br.com.dasa.print.core.model.Impressora;
import br.com.dasa.print.core.repository.ImpressoraRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ImpressoraResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ImpressoraRepository impressoraRepository;

    @Autowired
    private MockMvc mockMvc;

    private Impressora impressora;

//    @Before
//    public void setUp() {
//        impressora =  new Impressora();
//        impressora.setIdentificacao("Impress01");
//        impressora.setUnidade("Delboni01");
//    }
//
//    @Test
//    public void criaImpressoraDeveRetornar201(){
//        ResponseEntity<Impressora> create = restTemplate.postForEntity("/impressora", impressora, Impressora.class);
//        assertThat(create.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//       }
//
//    @Test
//    public void deletaImpressoraDeveRetornar200(){
//        BDDMockito.doNothing().when().
//        ResponseEntity<Impressora> create = restTemplate.postForEntity("/impressora", impressora, Impressora.class);
//        restTemplate.exchange("/impressora/identificacao/{identificacao}" , DELETE, null, String.class , "Imprees01");
//    }
//
//
//    @Test
//    public void listaImpressoraPelaIdentificacao(){
//        BDDMockito.when(impressoraRepository.findByIdentificacao(impressora.getIdentificacao())).thenReturn(impressora);
//        ResponseEntity<Impressora> listaImpressora = restTemplate.getForEntity("/impressora/identificacao/{identificacao}", Impressora.class, "Impress01");
//        assertThat(listaImpressora.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertEquals("Delboni01", listaImpressora.getBody().getUnidade().equals("Delboni01"));
//
//    }



}
