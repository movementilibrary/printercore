package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.oracle.model.Pc;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.model.Unidade;
import br.com.dasa.print.core.service.UnidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "unidades")
public class UnidadeResource {

    @Autowired
    private UnidadeService pcService;

    @Autowired
    private UnidadeService unidadeService;



    @GetMapping(value = "/{unidade}/impressoras")
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar Impressoras por unidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Unidade> listaImpressoras(@PathVariable String unidade) {
        return unidadeService.listaImpressorasPorUnidade(unidade);

    }

//
//    @DeleteMapping(value = "/{id}/impressora")
//    @ApiOperation(httpMethod = "DELETE", value = "Responsável por deletar Impressora por Unidade")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Sucesso"),
//            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
//            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
//    })
//    public void deletaImpressora(@PathVariable  String id) {
//        unidadeService.excluiImpressora(id);
//
//    }
}
