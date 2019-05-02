package br.com.dasa.printcore.resource;
import br.com.dasa.printcore.redis.model.Impressora;
import br.com.dasa.printcore.redis.repository.ImpressoraRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration
public class ImpressoraResourceTest {


//    private Impressora impressora;
//
//    @LocalServerPort
//    private int port;
//
//    @Before
//    public void setUp() throws Exception {
//
//        impressora =  new Impressora();
//        impressora.setEmpresa("Delboni");
//        impressora.setMacaddress("0-0-0-0-0-");
//        impressora.setUnidade("BRA");
//        impressora.setNome("Recepcao");
//    }


//    @Test
//    public void deveCriarImpressora(){
//        int accept = given()
//                .header("Accept", "application/json")
//                .contentType("application/json")
//                .body(impressora)
//                .when().
//                        post("/impressoras")
//                .andReturn()
//                .statusCode();
//        assertEquals(201, accept);
//    }

//    @Test
//    public void deveRetornarImpressora(){
//        JsonPath path = given()
//                .header("Accept", "application/json")
//                .get("/impressoras")
//                .andReturn()
//                .jsonPath();
//
//        Impressora impressora = path.getObject("impressora", Impressora.class);
//        Impressora impressora1 = new Impressora ();
//        assertEquals(impressora1,impressora );
//    }
//
}
