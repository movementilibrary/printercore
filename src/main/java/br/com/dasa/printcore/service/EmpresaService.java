package br.com.dasa.printcore.service;

import br.com.dasa.printcore.exception.InternalServerException;
import br.com.dasa.printcore.oracle.model.EmpImg;
import br.com.dasa.printcore.oracle.model.Pc;
import br.com.dasa.printcore.oracle.repository.EmpImgRepository;
import br.com.dasa.printcore.oracle.repository.PcRepository;
import br.com.dasa.printcore.type.MensagemErroType;
import br.com.dasa.printcore.type.MensagemInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    private EmpImgRepository empImgRepository;

    @Autowired
    private PcRepository pcRepository;

    /**
     * Metodo Responsável por Lista todas as Empresas
     * @author Michel Marciano
     * @return listaTodasEmpImgs
     * @exception InternalServerException
     */
    public List<EmpImg> listaTodasEmpresas() {
        List<EmpImg> listaTodasEmpImgs = null;
        try {
            LOGGER.info(MensagemInfoType.BUSCANDO_IMPRESSORAS.getMensagem());
            listaTodasEmpImgs = empImgRepository.listaEmpresas();

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_EMPRESAS.getMensagem(),  e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
        return listaTodasEmpImgs;
    }

    /**
     * Responsável por listar Unidades pela identificacao
     *
     * @param id
     * @return listaUnidadePorIdentificacao
     * @throws InternalServerException
     * @author Michel Marciano
     */
    public List<Pc> listaUnidadePorCodigoEmpresa(String id) {
        List<Pc> listaUnidadePorCodigo = null;
        try {
            LOGGER.info(MensagemInfoType.BUSCANDO_UNIDADE_POR_EMPRESA.getMensagem().concat("{}"), id);
            listaUnidadePorCodigo = pcRepository.listaUnidadePorCodigoEmpresa(id);
            listaUnidadePorCodigo.forEach(unidade -> unidade.setNome(unidade.getMnemonico().concat(" - ").concat(unidade.getNome())));

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_IMPRESSORA_POR_UNIDADE.getMensagem().concat("{}"), e.getMessage(), e);
            throw new InternalServerException(e.getMessage(), e);
        }
        return listaUnidadePorCodigo;
    }

}
