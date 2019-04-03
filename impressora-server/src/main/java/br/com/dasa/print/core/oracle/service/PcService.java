package br.com.dasa.print.core.oracle.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.oracle.model.Pc;
import br.com.dasa.print.core.oracle.repository.PcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PcService.class);

    @Autowired
    private PcRepository pcRepository;

    /**
     * Responsável por listar Unidades pela identificacao
     * @author Michel Marciano
     * @param codigoEmpresa
     * @exception InternalServerException
     * @return listaUnidadePorIdentificacao
     *
     */
    public List<Pc> listaUnidadePorCodigoEmpresa(String codigoEmpresa) {
        List<Pc> listaUnidadePorCodigo = null;
        try {
            LOGGER.info("Listando Unidade por codigoEmpresa {} ", codigoEmpresa);
            listaUnidadePorCodigo = pcRepository.listaUnidadePorCodigoEmpresa(codigoEmpresa);
        } catch (Exception e) {
            LOGGER.error("Erro ao listar unidade", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return listaUnidadePorCodigo;
    }

}
