package br.com.dasa.print.core.service;

import br.com.dasa.print.core.model.Impressao;
import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.model.Impressora;
import br.com.dasa.print.core.repository.ImpressoraRepository;
import br.com.dasa.print.core.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ImpressaoService {

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

            LOGGER.info("Atualizando Horario Impressora {} ", impressora);
            Utils.atualizaHoraImpressora(impressora.get());

            LOGGER.info("Salvando impressora  {}  no Banco de Dados", impressora.get().getIdentificacao());
            impressoraRepository.save(impressora.get());

            LOGGER.info("Enviando Mensagem {} impressao ", impressao);
            rabbitTemplate.convertAndSend(impressao.getIdentificacao(), impressao.getConteudoImpressao());

        } catch (Exception e) {
            LOGGER.error("Erro ao enviar mensagem fila {} ", impressao.getIdentificacao());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
