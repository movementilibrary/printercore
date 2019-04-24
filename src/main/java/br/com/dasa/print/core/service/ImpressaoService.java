package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.redis.model.Impressao;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.redis.repository.ImpressoraRepository;
import br.com.dasa.print.core.type.MensagemErroType;
import br.com.dasa.print.core.type.MensagemInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ImpressaoService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressaoService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PrinterService printerService;


    /**
     * Metodo responsável por preparar mensagem antes de enviar para o Rabbit, partindo do gliese a mensagem será
     * preparada e emviada para impressao
     *
     * @param impressao
     * @throws InternalServerException
     * @author Michel Marciano
     */
    public void preparaConteudoAntesImpressao(Impressao impressao) {
        try {
            LOGGER.info(MensagemInfoType.PREPARANDO_CONTEUDO_PARA_IMPRESSAO.getMensagem());
            String conteudoEPL = printerService.convertToEPL2(impressao);
            solicitaImpressao(impressao.getImpressora(), conteudoEPL);
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_PREPARANDO_CONTEUDO_PARA_IMPRESSAO.getMensagem().concat(" {} "), e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
    }

    /**
     * Responsável por enviar mensagem para fila
     *
     * @throws InternalServerException
     * @author Michel Marciano
     */
    public void solicitaImpressao(String idImpressora, String conteudo) {
        try {
            LOGGER.info(MensagemInfoType.ENVIANDO_IMPRESSAO_PARA_IMPRESSORA.getMensagem().concat(" {} "), idImpressora);
            rabbitTemplate.convertAndSend(idImpressora, conteudo);
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_ENVIAR_IMPRESSAO_IMPRESSORA.getMensagem().concat(" {} "), idImpressora, e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
    }

}
