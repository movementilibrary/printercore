package br.com.dasa.print.core.oracle.service;

import br.com.dasa.print.core.exception.InternalServerException;
import br.com.dasa.print.core.oracle.model.EmpImg;
import br.com.dasa.print.core.oracle.repository.EmpImgRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpImgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpImgService.class);

    @Autowired
    private EmpImgRepository empImgRepository;

    /**
     * Metodo Responsável por Lista todas as Empresas
     * @author Michel Marciano
     * @return retornaTodasEmpresas
     * @exception InternalServerException
     */
    public List<EmpImg> listaTodasEmpresas() {
        List<EmpImg> listaTodasEmpImgs = null;
        try {
            LOGGER.info("Buscando Empresas...");
            listaTodasEmpImgs = empImgRepository.listaEmpresas();

        } catch (Exception e) {
            LOGGER.error("Erro ao Buscar empresas ", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return listaTodasEmpImgs;

    }
}
