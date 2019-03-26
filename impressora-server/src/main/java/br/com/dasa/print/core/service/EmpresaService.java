package br.com.dasa.print.core.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.model.Empresa;
import br.com.dasa.print.core.repository.EmpresaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Metodo Responsável por Lista todas as Empresas
     * @author Michel Marciano
     * @return retornaTodasEmpresas
     * @exception InternalServerException
     */
    public List<Empresa> listaTodasEmpresas() {
        List<Empresa> listaTodasEmpresas = null;
        try {
            LOGGER.info("Buscando Empresas...");
            listaTodasEmpresas = this.empresaRepository.findAll();

        } catch (Exception e) {
            LOGGER.error("Erro ao Buscar empresas ", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return listaTodasEmpresas;

    }
}
