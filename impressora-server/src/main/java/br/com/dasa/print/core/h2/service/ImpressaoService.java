package br.com.dasa.print.core.h2.service;

import br.com.dasa.print.core.h2.model.Impressao;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.h2.model.Impressora;
import br.com.dasa.print.core.h2.repository.ImpressoraRepository;
import br.com.dasa.print.core.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImpressaoService {

    @Autowired
    private PrinterService printerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressaoService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ImpressoraService impressoraService;

    @Autowired
    private ImpressoraRepository impressoraRepository;

    /**
     * Metodo responsável por enviar mensagem para fila
     * @author Michel Marciano
     * @param impressao
     * @throws ResourceNotFoundException
     */
    public void solicitaImpressao(Impressao impressao) {
        try {
            Optional<Impressora> impressora = Optional.ofNullable(impressoraService.listaImpressoraPelaIdentificacao(impressao.getIdentificacao()));

            LOGGER.info("Salvando impressora  {}  no Banco de Dados", impressora.get().getIdentificacao());
            impressoraRepository.save(impressora.get());

            String layoutImpressao = printerService.imprimir(impressao.getConteudoImpressao());

            LOGGER.info("Enviando Mensagem {} impressao ", impressao);
            rabbitTemplate.convertAndSend(impressao.getIdentificacao(), layoutImpressao);

        } catch (Exception e) {
            LOGGER.error("Erro ao enviar mensagem fila {} ", impressao.getIdentificacao());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }



}
