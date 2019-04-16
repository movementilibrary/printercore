package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.repository.ImpressoraRepository;
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
    @CachePut(cacheNames = "impressao",  key="#impressora?.macaddress")
    public Impressora criaImpressora(Impressora impressora) {
        Impressora impressoraCriada = null;
        try {
            filaService.novaFila(impressora);

            unidadeService.criaListaImpressoraPorUnidade(impressora);

            LOGGER.info("Salvando impressora {} ", impressora.getMacaddress());
            impressoraCriada = this.impressoraRepository.save(atualizaHoraImpressora(impressora));

        } catch (Exception e) {
            LOGGER.error("Erro ao salvar impressora {}", impressora.getMacaddress() + e.getMessage());
            throw new InternalServerException(e.getMessage());

        }
        return impressoraCriada;
    }


    /**
     * Responsável por apagar fila Rabbit
     * @author Michel Marciano
     * @param macaddress
     * @throws ResourceNotFoundException
     */
    @CacheEvict(cacheNames = "impressao", key = "#macaddress")
    public void excluiImpressora(String macaddress) {
        try {
            Optional<Impressora> impressora = Optional.ofNullable(listaImpressoraPeloMacaddress(macaddress));

            filaService.apagaFila(impressora.get().getMacaddress());

            LOGGER.info("Deletando impressora pelo macaddress {} ", impressora.get().getMacaddress());
            this.impressoraRepository.delete(impressora.get());

        } catch (Exception e) {
            LOGGER.error("Erro ao apagar impressora", e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

    }


    /**
     * Responsável por listar impressora pelo macaddress
     * @author Michel Marciano
     * @return impressoraPeloMacaddress
     * @param macaddress
     * @throws ResourceNotFoundException
     */
    @Cacheable(cacheNames = "impressao", key="#macaddress")
    public Impressora listaImpressoraPeloMacaddress(String macaddress) {
        Optional<Impressora> impressoraPeloMacaddress= null;
        try {
            LOGGER.info("Buscando impressora pelo macaddress {} ", macaddress);
            impressoraPeloMacaddress = this.impressoraRepository.findById(macaddress);
        } catch (Exception e) {
            LOGGER.error("Erro ao realizar busca da impressora", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return impressoraPeloMacaddress.orElseThrow(() -> new ResourceNotFoundException("Não foi possivel encontrar impressora"));
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
