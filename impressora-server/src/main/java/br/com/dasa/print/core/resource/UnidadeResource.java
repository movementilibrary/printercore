package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.model.Empresa;
import br.com.dasa.print.core.model.Unidade;
import br.com.dasa.print.core.service.EmpresaService;
import br.com.dasa.print.core.service.UnidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UnidadeResource {

    @Autowired
    private UnidadeService unidadeService;

    @GetMapping(value="/unidade/{identificacao}")
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar todas as empresas'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Unidade> listaUnidadePelaIdentificacao(@PathVariable Integer identificacao) {
        return this.unidadeService.listaUnidadePorIdentificacao(identificacao);
    }
}
