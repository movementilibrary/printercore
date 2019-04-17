package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.redis.model.Impressora;
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
     *
     * @param impressora
     */
    public void novaFila(Impressora impressora) {
        try {
            LOGGER.info("Criando fila {}  no RabbbitMq", impressora.getMacaddress());
            this.rabbitAdmin.declareQueue(new Queue(impressora.getMacaddress()));
        } catch (Exception e) {
            LOGGER.error("Erro ao criar impressora {} {}", impressora.getMacaddress(), e.getMessage(), e);
            throw new InternalServerException(e.getMessage(), e);
        }
    }

    public void apagaFila(String identificacao){
        try {
            LOGGER.info("Apagando fila {}", identificacao);
            this.rabbitAdmin.deleteQueue(identificacao);
        }catch (Exception e){
            LOGGER.error("Erro ao apagar impressora {} {}", identificacao, e.getMessage(), e);
            throw new InternalServerException("Erro ao apagar impressora", e);
        }
    }

}
