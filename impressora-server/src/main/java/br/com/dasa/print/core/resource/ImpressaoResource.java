package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.redis.model.Impressao;
import br.com.dasa.print.core.redis.service.ImpressaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImpressaoResource {

    @Autowired
    private ImpressaoService impressaoService;

    @PostMapping(value="/impressao")
    @ApiOperation(httpMethod = "POST", value = "Responsável por enviar mensagem para impressão")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public void solicitaImpressao(@RequestBody Impressao impressao) {
        impressaoService.preparaConteudoAntesImpressao(impressao);
    }

}
