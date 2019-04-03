package br.com.dasa.print.core.redis.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.repository.ImpressoraRepository;

@Service
public class ImpressoraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressoraService.class);

    @Autowired
    private ImpressoraRepository impressoraRepository;

    @Autowired
    private FilaService filaService;

    /**
     * Responsável por criar mensagem Impressora
     * @author Michel Marciano
     * @param impressora
     * @throws InternalServerException
     */
    @CachePut(cacheNames = "impressao",  key="#impressora?.identificacao")
    public Impressora criaImpressora(Impressora impressora) {
        Impressora impressoraCriada = null;
        try {
            filaService.novaFila(impressora);

            LOGGER.info("Salvando impressora {} ", impressora.getIdentificacao());
            impressoraCriada = this.impressoraRepository.save(atualizaHoraImpressora(impressora));

        } catch (Exception e) {
            LOGGER.error("Erro ao salvar impressora {}", impressora.getIdentificacao() + e.getMessage());
            throw new InternalServerException(e.getMessage());

        }
        return impressoraCriada;
    }

    /**
     * Responsável por apagar fila Rabbit
     * @author Michel Marciano
     * @param identificacao
     * @throws ResourceNotFoundException
     */
    @CacheEvict(cacheNames = "impressao", key = "#identificacao")
    public void apagaImpressora(String identificacao) {
        try {
            Optional<Impressora> impressora = Optional.ofNullable(listaImpressoraPelaIdentificacao(identificacao));

            filaService.apagaFila(impressora.get().getIdentificacao());

            LOGGER.info("Deletando impressora pela identificacao {} ", impressora.get().getIdentificacao());
            this.impressoraRepository.delete(impressora.get());

        } catch (Exception e) {
            LOGGER.error("Erro ao apagar impressora", e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

    /**
     * Responsável por listar Impressoras por unidade
     * @author Michel Marciano
     * @param unidade
     * @throws ResourceNotFoundException
     * @return impressoraPelaUnidade
     *
     */
    public List<Impressora> listaImpressorasPorUnidade(String unidade) {
        List<Impressora> impressoraPelaUnidade = null;
        try {
            LOGGER.info("Listando Impressora pela unidade {} ", unidade);
            impressoraPelaUnidade = impressoraRepository.findByUnidade(unidade);
        } catch (Exception e) {
            LOGGER.error("Erro ao listar impressoras", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return impressoraPelaUnidade;
    }

    /**
     * Responsável por listar impressora pela Identificacao
     * @author Michel Marciano
     * @return impressoraPelaIdentificacao
     * @param identificacao
     * @throws ResourceNotFoundException
     */
    @Cacheable(cacheNames = "impressao", key="#identificacao")
    public Impressora listaImpressoraPelaIdentificacao(String identificacao) {
        Optional<Impressora> impressoraPelaIdentificacao = null;
        try {
            LOGGER.info("Buscando impressora pela identificacao {} ", identificacao);
            impressoraPelaIdentificacao = this.impressoraRepository.findById(identificacao);
        } catch (Exception e) {
            LOGGER.error("Erro ao realizar busca da impressora", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return impressoraPelaIdentificacao.orElseThrow(() -> new ResourceNotFoundException("Não foi possivel encontrar impressora"));
    }

    /**
     * Responsável por retornar todas as impressoras
     * @author Michel Marciano
     * @return listaImpressoras
     * @throws InternalServerException
     *
     */
    public List<Impressora> listaTodasImpressoras() {
        List<Impressora> listaImpressoras = new ArrayList<>();
        try {
            LOGGER.info("Buscando impressoras...");
            this.impressoraRepository.findAll().forEach(impressora -> listaImpressoras.add(impressora));

        } catch (Exception e) {
            LOGGER.error("Erro ao Buscar impressoras ", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return listaImpressoras;
    }


    /**
     * Metodo Responsável por executar ultima atualizacao
     * @param impressora
     * @return
     */
    public static Impressora atualizaHoraImpressora(Impressora impressora) {
        LOGGER.info("Atualizando horario impressora {} ", impressora);
        impressora.setUltimaAtualizacao(LocalDateTime.now().minusHours(4));
        return impressora;
    }


}
