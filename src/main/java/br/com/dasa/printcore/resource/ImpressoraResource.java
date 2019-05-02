package br.com.dasa.printcore.resource;

import java.util.List;

import br.com.dasa.printcore.redis.model.CalibraImpressora;
import br.com.dasa.printcore.redis.model.Impressora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.dasa.printcore.service.ImpressoraService;
import br.com.dasa.printcore.service.UnidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

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
    public ResponseEntity<Impressora> criaImpressora(@Valid @RequestBody Impressora impressora) {
        impressora.setId(impressaoService.criaIdImpressora(impressora.getUnidade(),impressora.getMacaddress()));
        return new ResponseEntity(impressoraService.criaImpressora(impressora), HttpStatus.CREATED);
    }

    @PostMapping(value = "calibra")
    @ApiOperation(httpMethod = "POST", value = "Responsável por calibrar impressora")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public void calibraImpressora(@RequestBody CalibraImpressora calibraImpressora) {
        impressaoService.calibraImpressao(calibraImpressora);
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
