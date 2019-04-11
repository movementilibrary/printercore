package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.oracle.model.Pc;
import br.com.dasa.print.core.oracle.repository.PcRepository;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.model.Unidade;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objm;

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
     * @param impressora
     * @author Michel Marciano
     */
    public void criaListaImpressoraPorUnidade(Impressora impressora) {
        try {

            LOGGER.info("Inserindo impressora {} na lista da unidade {} ", impressora.getIdentificacao(), impressora.getUnidade());
            redisTemplate.opsForList().leftPush(impressora.getUnidade(),  objm.writeValueAsString(new Unidade(impressora.getIdentificacao(),impressora.getUnidade())) );
        }catch (Exception e){
            LOGGER.error("Não foi possivel inserir impressora {} na lista da unidade {} ", impressora.getIdentificacao(), impressora.getUnidade(), e);

        }

    }

    /**
     * Responsável por listar impressora por unidade
     * @param unidade
     * @return listaImpressoraPorUnidade
     * @author Michel Marciano
     */
    public List<Unidade> listaImpressorasPorUnidade(Unidade unidade){
        List listaImpressoraPorUnidade = null;
        List<Unidade> uni = null;

        try{
            LOGGER.info("Listando impressoras da unidade {} ", unidade);
           listaImpressoraPorUnidade = redisTemplate.opsForList().range(unidade.getNome(), 0, -1);

            uni = objm.readValue(listaImpressoraPorUnidade.toString() ,new TypeReference<List<Unidade>>(){});


        }catch (Exception e){
            LOGGER.error("Não foi possivel listar impressoras da unidade {}  ", unidade,  e);
        }
        return uni;
    }


    /**
     * Rsponsável por deletar impressora por unidade
     * @param impressora
     * @author Michel Marciano
     */
    public void excluindoImpressora(Impressora impressora) {
        try {

            String unidade = objm.writeValueAsString(new Unidade(impressora.getIdentificacao(), impressora.getUnidade()));

            LOGGER.info("Excluindo impressora {}  da unidade {} ", impressora.getIdentificacao(), impressora.getUnidade());
            redisTemplate.opsForList().remove(unidade, 1, unidade);

        }catch (Exception e ){
            LOGGER.error("Não foi possivel excluir impressora {} da unidade {} ", impressora.getIdentificacao(), impressora.getUnidade(), e);
        }

    }

}
