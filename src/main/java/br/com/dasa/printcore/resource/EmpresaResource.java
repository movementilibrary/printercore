package br.com.dasa.printcore.resource;

import br.com.dasa.printcore.oracle.model.EmpImg;
import br.com.dasa.printcore.oracle.model.Pc;
import br.com.dasa.printcore.service.EmpresaService;
import br.com.dasa.printcore.service.UnidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "empresas")
public class EmpresaResource {

    @Autowired
    private EmpresaService empImgService;

    @Autowired
    private UnidadeService pcService;

    @GetMapping()
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar todas as empresas'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<EmpImg> listaTodasEmpresas() {
        return this.empImgService.listaTodasEmpresas();
    }


    @GetMapping(value="/{id}/unidades")
    @ApiOperation(httpMethod = "GET", value = "Responsável por listar todas as unidades por empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Pc> listaUnidadePorCodigoEmpresa(@PathVariable String id) {
        return this.empImgService.listaUnidadePorCodigoEmpresa(id);
    }
}
