package br.com.dasa.print.core.resource;

import br.com.dasa.print.core.service.ImpressoraService;
import br.com.dasa.print.core.redis.model.Impressora;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/impressora")
public class ImpressoraResource {

    @Autowired
    private ImpressoraService impressoraService;


    @GetMapping(value = "/macaddress/{macaddress}")
    @ApiOperation(httpMethod = "GET", value = "Responsável por retornar Impressora pelo macaddress")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public Impressora listaImpressoraPelaIdentificacao(@PathVariable String macaddress) {
        return impressoraService.buscaImpressoraPeloMacaddress(macaddress);

    }

    @GetMapping()
    @ApiOperation(httpMethod = "GET", value = "Responsável por retornar Todas as Impressoras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public List<Impressora> listaTodasImpressoras() {
        return impressoraService.listaTodasImpressoras();
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


    @DeleteMapping(value = "/macaddress/{macaddress}")
    @ApiOperation(httpMethod = "DELETE", value = "Responsável por apagar Impressora")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 404, message = "O recurso requisitado não foi encontrado"),
            @ApiResponse(code = 500, message = "Um erro interno foi detectado")
    })
    public void deletaImpressora(@PathVariable String macaddress) {
        impressoraService.excluiImpressora(macaddress);
    }


}
