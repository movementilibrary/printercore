package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.oracle.model.Pc;
import br.com.dasa.print.core.oracle.repository.PcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnidadeService.class);

    @Autowired
    private PcRepository pcRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Responsável por listar Unidades pela identificacao
     * @author Michel Marciano
     * @param codigoEmpresa
     * @exception InternalServerException
     * @return listaUnidadePorIdentificacao
     *
     */
    public List<Pc> listaUnidadePorCodigoEmpresa(String codigoEmpresa) {
        List<Pc> listaUnidadePorCodigo = null;
        try {
            LOGGER.info("Listando Unidade por codigoEmpresa {} ", codigoEmpresa);
            listaUnidadePorCodigo = pcRepository.listaUnidadePorCodigoEmpresa(codigoEmpresa);
        } catch (Exception e) {
            LOGGER.error("Erro ao listar unidade", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return listaUnidadePorCodigo;
    }

    /**
     * Responsável por criar lista de impressora por unidade
     * @param unidade
     * @param identificacao
     * @author Michel Marciano
     */
    public void criaListaImpressoraPorUnidade(String unidade, String identificacao) {
        try {
            LOGGER.info("Inserindo impressora {} da unidade {} ", identificacao, unidade );
            redisTemplate.opsForList().leftPush(unidade, identificacao);
        }catch (Exception e){
            LOGGER.error("Não foi possivel inserir impressora {} da unidade {} ", identificacao, unidade, e);

        }

    }

    /**
     * Responsável por listar impressoras por unidade
     * @param unidade
     * @return listaImpressoraPorUnidade
     * @author Michel Marciano
     */
    public List<String> listaImpressorasPorUnidade(String unidade ){
        List listaImpressoraPorUnidade = null;
        try{
            LOGGER.info("Listando impressoras da unidade {} ", unidade);
            listaImpressoraPorUnidade = redisTemplate.opsForList().range(unidade, 0, -1);

        }catch (Exception e){
            LOGGER.error("Não foi possivel listar impressoras da unidade {}  ", unidade,  e);
        }
        return listaImpressoraPorUnidade;
    }


    /**
     * Rsponsável por deletar impressoras por unidade
     * @param unidade
     * @param identificacao
     * @author Michel Marciano
     */
    public void deletaValorLista(String unidade, String identificacao) {
        try {
            LOGGER.info("Excluindo impressora {}  da unidade {} ", identificacao, unidade);
            redisTemplate.opsForList().remove(unidade, 0, identificacao);

        }catch (Exception e ){
            LOGGER.error("Não foi possivel excluir impressora {} da unidade {} ", identificacao, unidade, e);
        }

    }

}
