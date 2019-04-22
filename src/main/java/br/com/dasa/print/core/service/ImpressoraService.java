package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.repository.ImpressoraRepository;
import br.com.dasa.print.core.type.MensagemErroType;
import br.com.dasa.print.core.type.MensagemInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImpressoraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressoraService.class);

    @Autowired
    private ImpressoraRepository impressoraRepository;

    @Autowired
    private FilaService filaService;

    @Autowired
    private UnidadeService unidadeService;

    /**
     * Responsável por criar mensagem Impressora
     * @author Michel Marciano
     * @param impressora
     * @throws InternalServerException
     */
    @CachePut(cacheNames = "impressao",  key="#impressora?.id")
    public Impressora criaImpressora(Impressora impressora) {
        Impressora impressoraCriada = null;
        try {

            //TODO: Verificar se impressora existe antes de criar
            filaService.novaFila(criaIdImpressora(impressora));

            unidadeService.criaListaImpressoraPorUnidade(impressora);

            LOGGER.info(MensagemInfoType.SALVANDO_IMPRESSORA.getMensagem().concat(" {} "), impressora.getMacaddress());
            impressoraCriada = this.impressoraRepository.save(atualizaHoraImpressora(impressora));

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_SALVAR_IMPRESSORA.getMensagem().concat(" {} ") , impressora.getId(), e.getMessage());
            throw new InternalServerException(e.getMessage());

        }
        return impressoraCriada;
    }




    /**
     * Responsável por apagar fila Rabbit
     * @author Michel Marciano
     * @param id
     * @throws ResourceNotFoundException
     */
    @CacheEvict(cacheNames = "impressao", key = "#id")
    public void excluiImpressora(String id) {
        try {

            Impressora impressora = buscaImpressoraPeloId(id);

            filaService.apagaFila(impressora.getId());

            unidadeService.excluiImpressora(id);

            LOGGER.info(MensagemInfoType.DELETANDO_IMPRESSORA.getMensagem().concat(" {} "), impressora.getId());
            this.impressoraRepository.delete(impressora);

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_DELETAR_IMPRESSORA.getMensagem().concat(" {} "), e.getMessage());
            throw new ResourceNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * Responsável por listar impressora pelo macaddress
     * @author Michel Marciano
     * @return impressoraPeloMacaddress
     * @param id
     * @throws ResourceNotFoundException
     */
    @Cacheable(cacheNames = "impressao", key="#id")
    public Impressora buscaImpressoraPeloId(String id) {
        Optional<Impressora> impressoraPeloMacaddress= null;
        try {
            LOGGER.info(MensagemInfoType.BUSCANDO_IMPRESSORAS.getMensagem().concat("{}"), id);
            impressoraPeloMacaddress = this.impressoraRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_IMPRESSORAS.getMensagem().concat("{}"), e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
        return impressoraPeloMacaddress.orElseThrow(() -> new ResourceNotFoundException(MensagemInfoType.IMPRESSORA_NOT_FOUND.getMensagem()));
    }

    /**
     * Responsável por retornar todas as impressoras
     * @author Michel Marciano
     * @return listaImpressoras
     * @throws InternalServerException
     *
     */
    public List<Impressora> listaTodasImpressoras(String id) {
        List<Impressora> listaImpressoras = new ArrayList<>();
        try {
            if(id == null){
                LOGGER.info(MensagemInfoType.BUSCANDO_IMPRESSORAS.getMensagem());
                this.impressoraRepository.findAll().forEach(impressora -> listaImpressoras.add(impressora));
            }else{
                listaImpressoras.add(buscaImpressoraPeloId(id));
            }

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_IMPRESSORAS.getMensagem(), e);
            throw new InternalServerException(e.getMessage(), e);
        }
        return listaImpressoras;
    }


    /**
     * Metodo Responsável por executar ultima atualizacao
     * @param impressora
     * @return
     */
    public static Impressora atualizaHoraImpressora(Impressora impressora) {
        LOGGER.info( MensagemInfoType.ATUALIZANDO_HORARIO_IMPRESSORA.getMensagem().concat(" {} ") , impressora.getId());
        impressora.setUltima_atualizacao(LocalDateTime.now().minusHours(4));
        return impressora;
    }


    /**
     * Metodo Responsável por criar Id Impressora
     * @param impressora
     * @return
     */
    public static Impressora criaIdImpressora(Impressora impressora){
        impressora.setId(impressora.getUnidade().concat("-").concat(impressora.getMacaddress()));
        return impressora;
    }



}
