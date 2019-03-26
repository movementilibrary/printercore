package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.ResourceNotFoundException;
import br.com.dasa.print.core.model.Impressora;
import br.com.dasa.print.core.model.Unidade;
import br.com.dasa.print.core.repository.UnidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnidadeService.class);

    @Autowired
    private UnidadeRepository unidadeRepository;

    /**
     * Responsável por listar Unidades pela identificacao
     * @author Michel Marciano
     * @param identificacao
     * @exception ResourceNotFoundException
     * @return listaUnidadePorIdentificacao
     *
     */
    public List<Unidade> listaUnidadePorIdentificacao(Integer identificacao) {
        List<Unidade> listaUnidadePorIdentificacao = null;
        try {
            LOGGER.info("Listando Unidade por id {} ", identificacao);
            listaUnidadePorIdentificacao = this.unidadeRepository.findByIdentificacao(identificacao);
        } catch (Exception e) {
            LOGGER.error("Erro ao listar unidade", e.getMessage());
        }
        return listaUnidadePorIdentificacao;
    }
}
