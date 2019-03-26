package br.com.dasa.print.core.service;

import br.com.dasa.print.core.repository.ImpressoraRepository;
import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.model.Impressora;
import br.com.dasa.print.core.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImpressoraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressoraService.class);

    @Autowired
    private ImpressoraRepository impressoraRepository;

    @Autowired
    private ImpressaoService rabbitQueueService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * Responsável por criar mensagem Impressora
     *
     * @param impressora
     * @return
     */
    public Impressora criaImpressora(Impressora impressora) {
        Impressora retornoImpressoraCriada = null;
        String retornoCriacaoFila = null;
        try {
            LOGGER.info("Criando fila {}  no RabbbitMq", impressora.getIdentificacao());
            rabbitAdmin.declareQueue(new Queue(impressora.getIdentificacao()));

            LOGGER.info("Atualizando Horario Impressora {} ", impressora);
            Impressora impressoraAtualizada = Utils.atualizaHoraImpressora(impressora);

            LOGGER.info("Salvando impressora  {}  no Banco de Dados", impressora.getIdentificacao());
            retornoImpressoraCriada = impressoraRepository.save(impressoraAtualizada);

        } catch (Exception e) {
            LOGGER.error("Erro ao salvar impressora {}", impressora.getIdentificacao() + e.getMessage());
            throw new InternalServerException(e.getMessage());

        }
        return retornoImpressoraCriada;
    }

    /**
     * Responsável por apagar fila Rabbit
     *
     * @param identificacao
     */
    public void apagaImpressora(String identificacao) {
        try {
            Optional<Impressora> impressora = Optional.ofNullable(listaImpressoraPelaIdentificacao(identificacao));
            LOGGER.info("Deletando impressora pela identificacao ", impressora.get().getIdentificacao());
            rabbitAdmin.deleteQueue(identificacao);
            impressoraRepository.delete(impressora.get());

        } catch (Exception e) {
            LOGGER.error("Erro ao deletar impressora", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }

    }

    /**
     * Responsável por listar Impressoras por unidade
     *
     * @param unidade
     */
    public List<Impressora> listaImpressorasPorUnidade(String unidade) {
        List<Impressora> retornoListaImpressoraPelaUnidade = null;
        try {
            LOGGER.info("Listando Impressora pela unidade {} ", unidade);
            retornoListaImpressoraPelaUnidade = impressoraRepository.findByUnidade(unidade);
        } catch (Exception e) {
            LOGGER.error("Erro ao listar impressoras", e.getMessage());
        }
        return retornoListaImpressoraPelaUnidade;
    }

    /**
     * Responsável por listar impressora pela Identificacao
     *
     * @return
     */
    public Impressora listaImpressoraPelaIdentificacao(String identificacao) {
        Optional<Impressora> retornoListaImpressoraPelaIdentificacao = null;
        try {
            LOGGER.info("Buscando impressora pela identificacao {} ", identificacao);
            retornoListaImpressoraPelaIdentificacao = impressoraRepository.findByIdentificacao(identificacao);
        } catch (Exception e) {
            LOGGER.error("Erro ao realizar busca da impressora", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return retornoListaImpressoraPelaIdentificacao.orElseThrow(() -> new ResourceNotFoundException("Não foi possivel encontrar impressora"));
    }

    /**
     * Responsável por retornar todas as impressoras
     *
     * @return
     */
    public List<Impressora> listaTodasImpressoras() {
        List<Impressora> retornaTodasImpressoras = null;
        try {
            LOGGER.info("Buscando impressoras...");
            retornaTodasImpressoras = impressoraRepository.findAll();

        } catch (Exception e) {
            LOGGER.error("Erro ao Buscar impressoras ", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return retornaTodasImpressoras;
    }


}
