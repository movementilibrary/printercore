package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.redis.dto.ImpressoraDto;
import br.com.dasa.print.core.redis.model.Unidade;
import br.com.dasa.print.core.service.ImpressoraService;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/impressoras")
public class ImpressoraResource {

    @Autowired
    private ImpressoraService impressoraService;

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private ImpressoraService impressaoService;


    @GetMapping()
    @ApiOperation(httpMethod = "GET", value = "Responsável por retornar Todas as Impressoras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Impressora> listaTodasImpressoras(@RequestParam (required=false) String id) {
        return impressoraService.listaTodasImpressoras(id);
    }


    @PostMapping
    @ApiOperation(httpMethod = "POST", value = "Responsável por Criar Impressora")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public ResponseEntity<Impressora> criaImpressora(@RequestBody Impressora impressora) {
        return new ResponseEntity(impressoraService.criaImpressora(impressora), HttpStatus.CREATED);
    }

    @PostMapping(value = "calibra")
    @ApiOperation(httpMethod = "POST", value = "Responsável por calibrar impressora")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public void calibraImpressora(@RequestBody Impressora impressora) {
        impressaoService.calibraImpressao(impressora);
    }
    @DeleteMapping(value = "/{id}")
    @ApiOperation(httpMethod = "DELETE", value = "Responsável por apagar Impressora")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public void deletaImpressora(@PathVariable String id) {
        impressoraService.excluiImpressora(id);
    }


}
