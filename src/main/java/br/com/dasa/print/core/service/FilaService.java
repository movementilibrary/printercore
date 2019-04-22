package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.redis.model.Impressora;
import br.com.dasa.print.core.type.MensagemErroType;
import br.com.dasa.print.core.type.MensagemInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilaService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * Responsável por criar fila no Rabbit
     * @param impressora
     * @throws InternalServerException
     */
    public void novaFila(Impressora impressora) {
        try {
            LOGGER.info(MensagemInfoType.CRIANDO_FILA.getMensagem().concat( " {} "), impressora.getId());
            this.rabbitAdmin.declareQueue(new Queue(impressora.getId()));
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_CRIAR_FILA.getMensagem().concat( " {} {} "), impressora.getId(), e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
    }

    /**
     * Responsável por apagar Fila no Rabbit
     * @param id
     * @throws InternalServerException
     */
    public void apagaFila(String id){
        try {
            LOGGER.info( MensagemInfoType.APAGANDO_FILA.getMensagem(), id);
            this.rabbitAdmin.deleteQueue(id);
        }catch (Exception e){
            LOGGER.error( MensagemErroType.ERRO_APAGAR_FILA.getMensagem().concat(" {} {} "), id,  e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
    }

}
