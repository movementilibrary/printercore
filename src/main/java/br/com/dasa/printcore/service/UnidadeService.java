package br.com.dasa.printcore.service;

import java.util.List;

import br.com.dasa.printcore.type.MensagemErroType;
import br.com.dasa.printcore.type.MensagemInfoType;
import br.com.dasa.printcore.redis.model.Impressora;
import br.com.dasa.printcore.redis.model.Unidade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UnidadeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnidadeService.class);


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objm;

    @Autowired
    private ImpressoraService impressoraService;



    /**
     * Responsável por criar lista de impressora por unidade
     *
     * @param impressora
     * @author Michel Marciano
     */
    public void criaListaImpressoraPorUnidade(Impressora impressora) {
        try {
            LOGGER.info(MensagemInfoType.INSERINDO_IMPRESSORA_POR_UNIDADE.getMensagem().concat(" {} "), impressora.getNome());
            redisTemplate.opsForList().leftPush(impressora.getUnidade(), objm.writeValueAsString(new Unidade(impressora.getId(), impressora.getNome())));
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_INSERIR_IMPRESSORA_POR_UNIDADE.getMensagem().concat(" {} "), impressora.getUnidade(), e.getMessage(), e);
        }
    }

    /**
     * Responsável por listar impressora por unidade
     *
     * @param unidade
     * @return listaImpressoraPorUnidade
     * @author Michel Marciano
     */
    public List<Unidade> listaImpressorasPorUnidade(String unidade) {
        List listaImpressoraPorUnidade = null;
        List<Unidade> listaImpressora = null;

        try {
            LOGGER.info( MensagemInfoType.BUSCANDO_IMPRESSORA_POR_UNIDADE.getMensagem().concat(" {} "), unidade);
            listaImpressoraPorUnidade = redisTemplate.opsForList().range(unidade, 0, -1);
            listaImpressora = objm.readValue(listaImpressoraPorUnidade.toString(), new TypeReference<List<Unidade>>() {
            });

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_IMPRESSORA_POR_UNIDADE.getMensagem().concat("{}"), unidade, e);
        }
        return listaImpressora;
    }


    /**
     * Responsável por deletar impressora por unidade
     *
     * @param id
     * @author Michel Marciano
     */
    public void excluiImpressora(String id) {
        Impressora impressora = null;
        try {

            impressora = impressoraService.buscaImpressoraPeloId(id);

            String unidade = objm.writeValueAsString(new Unidade(impressora.getId(), impressora.getNome()));

            LOGGER.info(MensagemInfoType.EXCLUINDO_IMPRESSORA_POR_UNIDADE.getMensagem().concat(" {} "), impressora.getId(), impressora.getNome());
            redisTemplate.opsForList().remove(impressora.getUnidade(), 1, unidade);

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_EXCLUIR_IMPRESSORA_POR_UNIDADE.getMensagem().concat(" {} "), impressora.getId(), impressora.getUnidade(), e);

        }

    }

}
