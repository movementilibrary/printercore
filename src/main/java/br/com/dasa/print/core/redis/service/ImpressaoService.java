package br.com.dasa.print.core.redis.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.redis.model.Impressao;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.repository.ImpressoraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImpressaoService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressaoService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PrinterService printerService;


    /**
     * Metodo responsável por enviar mensagem para fila
     *
     * @param impressao
     * @throws InternalServerException
     * @author Michel Marciano
     */
    public void preparaConteudoAntesImpressao(Impressao impressao) {
        try {
            LOGGER.info("Preparando conteudo para impressao");
            printerService.imprimir(impressao.getConteudoImpressao());

            solicitaImpressao(impressao);
        } catch (Exception e) {
            LOGGER.error("Erro ao preparar conteudo para impressao ", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
    }

    /**
     * Responsável por enviar mensagem para fila

     * @exception InternalServerException
     * @author Michel Marciano
     */
    public void solicitaImpressao(Impressao impressao) {
        try {
            LOGGER.info("Enviando impressao para impresssora {}", impressao.getImpressora());
            rabbitTemplate.convertAndSend(impressao.getImpressora(), impressao.getConteudoImpressao());
        } catch (Exception e) {
            LOGGER.error("Erro ao enviar mensagem para impressora {} ", impressao.getImpressora());
            throw new InternalServerException(e.getMessage());
        }
    }


}
