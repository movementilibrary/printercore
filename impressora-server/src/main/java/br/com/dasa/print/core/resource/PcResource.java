package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.oracle.model.Pc;
import br.com.dasa.print.core.oracle.service.PcService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PcResource {

    @Autowired
    private PcService pcService;

    @GetMapping(value="/unidade/{codigoEmpresa}")
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar todas as unidades'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Pc> listaUnidadePorCodigoEmpresa(@PathVariable String codigoEmpresa) {
        return this.pcService.listaUnidadePorCodigoEmpresa(codigoEmpresa);
    }
}