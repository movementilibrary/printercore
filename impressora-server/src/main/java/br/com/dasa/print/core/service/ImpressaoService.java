package br.com.dasa.print.core.service;

import br.com.dasa.print.core.model.Impressao;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpressaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressaoService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ImpressoraService impressoraService;

    /**
     * Metodo responsável por enviar mensagem para fila
     *
     * @param impressao
     */
    public void enviarMensagemImpressao(Impressao impressao) {
        try {
            impressoraService.listaImpressoraPelaIdentificacao(impressao.getIdentificacao());
            LOGGER.info("Enviando Mensagem {} impressao ", impressao);
            rabbitTemplate.convertAndSend(impressao.getIdentificacao(), impressao.getConteudoImpressao());
        } catch (Exception e) {
            LOGGER.error("Erro ao enviar mensagem fila {} ", impressao.getIdentificacao());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
