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
@RequestMapping(value = "unidade")
public class UnidadeResource {

    @Autowired
    private UnidadeService pcService;

    @Autowired
    private UnidadeService unidadeService;

    @GetMapping(value="/empresa/{codigoEmpresa}")
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar todas as unidades por empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Pc> listaUnidadePorCodigoEmpresa(@PathVariable String codigoEmpresa) {
        return this.pcService.listaUnidadePorCodigoEmpresa(codigoEmpresa);
    }

    @GetMapping(value = "/impressora/{unidade}")
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar Impressoras por unidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Unidade> listaImpressoras(Unidade unidade) {
        return unidadeService.listaImpressorasPorUnidade(unidade);

    }


    @DeleteMapping()
    @ApiOperation(httpMethod = "DELETE", value = "Responsável por deletar Impressora por Unidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public void deletaImpressora(@RequestBody  Impressora impressora) {
        unidadeService.excluindoImpressora(impressora);

    }
}
