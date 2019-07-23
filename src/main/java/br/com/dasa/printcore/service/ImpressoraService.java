package br.com.dasa.printcore.service;

import br.com.dasa.printcore.exception.InternalServerException;
import br.com.dasa.printcore.exception.ResourceNotFoundException;
import br.com.dasa.printcore.redis.model.CalibraImpressora;
import br.com.dasa.printcore.redis.model.Impressora;
import br.com.dasa.printcore.redis.repository.ImpressoraRepository;
import br.com.dasa.printcore.type.MensagemErroType;
import br.com.dasa.printcore.type.MensagemInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImpressoraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressoraService.class);

    @Autowired
    private ImpressoraRepository impressoraRepository;

    @Autowired
    private FilaService filaService;

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Responsável por criar impressora, o metodo sempre vai criar Id em tempo de execucao e verificar se já
     * existe uma impressora com o mesmo id no redis, caso esteja ele somente atualiza a hora da impressora,
     * essa hora será utilizada para o Job de Exclusão, caso nao exista ele cria uma fila no RabbitMQ por
     * ID (unidade + macaddres) e adiciona a impressora na lista de impressora por unidade.
     *
     * @param impressora
     * @throws InternalServerException
     * @author Michel Marciano
     */

    public Impressora criaImpressora(Impressora impressora) {
        Impressora impressoraCriada = null;

        try {
             if(!this.impressoraRepository.findById(impressora.getId()).isPresent()){

                //filaService.novaFila(impressora);
                unidadeService.criaListaImpressoraPorUnidade(impressora);
            }
            LOGGER.info(MensagemInfoType.SALVANDO_IMPRESSORA.getMensagem().concat(" {} "), impressora.getId());
            impressoraCriada = this.impressoraRepository.save(atualizaHoraImpressora(impressora));


        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_SALVAR_IMPRESSORA.getMensagem().concat(" {} "), impressora.getId(), e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
        return impressoraCriada;
    }


    /**
     * Responsável por apagar fila Rabbit, caso a busca da impressora encontre a impressora a mesma será excluida
     * do RabbitMQ e será removida da lista de unidade e excluida do Redis, esse metodo será utilizado pelo Job
     *
     * @param id
     * @throws ResourceNotFoundException
     * @author Michel Marciano
     */
    @CacheEvict(cacheNames = "impressao", key = "#id")
    public void excluiImpressora(String id) {
        try {

            Impressora impressora = buscaImpressoraPeloId(id);

            //filaService.apagaFila(impressora.getId());

            unidadeService.excluiImpressora(id);

            LOGGER.info(MensagemInfoType.DELETANDO_IMPRESSORA.getMensagem().concat(" {} "), impressora.getId());
            this.impressoraRepository.delete(impressora);

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_DELETAR_IMPRESSORA.getMensagem().concat(" {} "), e.getMessage());
            throw new ResourceNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * Responsável por listar impressora pelo id
     *
     * @param id
     * @return impressoraPeloMacaddress
     * @throws ResourceNotFoundException
     * @author Michel Marciano
     */
    @Cacheable(cacheNames = "impressao", key = "#id")
    public Impressora buscaImpressoraPeloId(String id) {
        Optional<Impressora> impressoraPeloMacaddress = null;
        try {
            LOGGER.info(MensagemInfoType.BUSCANDO_IMPRESSORAS.getMensagem().concat("{}"), id);
            impressoraPeloMacaddress = this.impressoraRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_IMPRESSORAS.getMensagem().concat("{}"), e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
        return impressoraPeloMacaddress.orElseThrow(() -> new ResourceNotFoundException(MensagemInfoType.IMPRESSORA_NOT_FOUND.getMensagem()));
    }

    /**
     * Responsável por retornar todas as impressoras ou somente impressora por id, caso o id nao seja nulo será
     * realizado a busca da impressora a adicionado a lista de impressoras, caso contrario retorna uma lista de
     * imressoras ativas
     *
     * @return listaImpressoras
     * @throws InternalServerException
     * @author Michel Marciano
     */
    public List<Impressora> listaTodasImpressoras(String id) {
        List<Impressora> listaImpressoras = new ArrayList<>();
        try {
            if (id == null) {
                LOGGER.info(MensagemInfoType.BUSCANDO_IMPRESSORAS.getMensagem());
                this.impressoraRepository.findAll().forEach(impressora -> listaImpressoras.add(impressora));
            } else {
                listaImpressoras.add(this.impressoraRepository.findById(id).get());
            }

        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_BUSCAR_IMPRESSORAS.getMensagem(), e);
            throw new InternalServerException(e.getMessage(), e);
        }
        return listaImpressoras;
    }


    /**
     * Metodo Responsável por atualizar hora impressora, atualizacao da hora é necessária para que o Job não exclua
     * impressora
     *
     * @param impressora
     * @return
     */
    public static Impressora atualizaHoraImpressora(Impressora impressora) {
        LOGGER.info(MensagemInfoType.ATUALIZANDO_HORARIO_IMPRESSORA.getMensagem().concat(" {} "), impressora.getId());
        impressora.setUltimaAtualizacao(LocalDateTime.now().minusHours(4));
        return impressora;
    }


    /**
     * Responsável por Resetr Impressora
     *
     * @throws InterruptedException
     */
    public void calibraImpressao(CalibraImpressora calibraImpressora) {

//        AppletPrinter printer = new AppletPrinter();
//        printer.selecionaImpressora("Intermec");
        String strReset = "\u0002\u001bT\u0003     \u0002 ENTER TEST MODE      \u0003 \u0002D\u0003      \u0002 SET FACTORY DEFAULT  \u0003 \u0002R\u0003      \u0002 EXIT TEST MODE       \u0003 ";

//        try {
//            DocPrintJob dpj = this.printService.createPrintJob();
//            InputStream stream = new ByteArrayInputStream(strReset.getBytes());
//            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//            Doc doc = new SimpleDoc(stream, flavor, (DocAttributeSet) null);
//            dpj.print(doc, new HashPrintRequestAttributeSet());
//            Thread.sleep(20000L);
//        } catch (PrintException var9) {
//            var9.printStackTrace();
//        }
//
        String strHexa = "\u0002\u000fF5\u0003    \u0002 SET TOP OF FORM   \u0003\u0002\u000fD108\u0003  \u0002 SET SKIP ON \u0003\u0002\u000ff0\u0003    \u0002 SET LABEL REST    \u0003\u0002\u000fn1\u0003    \u0002 SET LABEL RETRACT \u0003\u0002\u001bP\u0003\u0002E3;F3;H0,LINHA 2;f0;o010,005;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,025;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,045;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,065;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d0,30;h1;w1;b0;r0;B6,       ;f0;o090,135;c2,1;d1,14;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E4;F4;H0,LINHA 2;f0;o010,006;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,026;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,046;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,066;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d0,30;h1;w1;b0;r0;B6,       ;f0;o010,120;c6,0;d1,16;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E5;F5;H0,LINHA 2;f0;o010,005;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,025;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,045;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,065;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,085;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d1,30;h1;w1;b1;r0;B6,       ;f0;o090,135;c2,1;d1,14;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E6;F6;H0,LINHA 2;f0;o010,006;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,026;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,046;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,066;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d0,30;h1;w1;b0;r0;B6,       ;f0;o090,135;c2,0;d1,16;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E7;F7;H0,LINHA 2;f0;o010,006;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,026;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,046;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,066;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d1,30;h1;w1;b1;r0;B6,       ;f0;o090,135;c2,0;d1,16;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bE3\u0018 E3 PADRAO INT 2of5 (12D + DV)\u0017\u0003\u0002\u001bE4\u0018 E4 PADRAO CODE 128 (Alfanum)\u0017\u0003";

//        try {
//            DocPrintJob dpj = this.printService.createPrintJob();
//            InputStream stream = new ByteArrayInputStream(strHexa.getBytes());
//            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//            Doc doc = new SimpleDoc(stream, flavor, (DocAttributeSet) null);
//            dpj.print(doc, new HashPrintRequestAttributeSet());
//        } catch (PrintException var8) {
//            var8.printStackTrace();
//        }

        String conteudo = strReset.concat(",").concat(strHexa);
        rabbitTemplate.convertAndSend(criaIdImpressora(calibraImpressora.getUnidade(), calibraImpressora.getMacaddress()), conteudo);
    }


    /**
     * Metodo Responsável por criar Id Impressora, o Id será utilizado na criacao da Fila do RabbitMq
     *
     * @return
     */
    public String criaIdImpressora(String unidade, String macaddress) {
        return unidade.concat("-").concat(macaddress);
    }


}
