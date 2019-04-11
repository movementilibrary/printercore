package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.oracle.model.EmpImg;
import br.com.dasa.print.core.service.EmpresaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "empresas")
public class EmpresaResource {

    @Autowired
    private EmpresaService empImgService;

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
}