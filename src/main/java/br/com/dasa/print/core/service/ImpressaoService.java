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

import java.util.Optional;

@Service
public class ImpressaoService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ImpressaoService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PrinterService printerService;


    /**
     * Metodo responsável por enviar mensagem para fila
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
            LOGGER.error(MensagemErroType.ERRO_PREPARANDO_CONTEUDO_PARA_IMPRESSAO.getMensagem().concat( " {} "), e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
    }

    /**
     * Responsável por enviar mensagem para fila

     * @exception InternalServerException
     * @author Michel Marciano
     */
    public void solicitaImpressao(String idImpressora, String conteudo) {
        try {
            LOGGER.info(MensagemInfoType.ENVIANDO_IMPRESSAO_PARA_IMPRESSORA.getMensagem().concat( " {} "), idImpressora);
            rabbitTemplate.convertAndSend(idImpressora, conteudo);
        } catch (Exception e) {
            LOGGER.error(MensagemErroType.ERRO_ENVIAR_IMPRESSAO_IMPRESSORA.getMensagem().concat(" {} "), idImpressora, e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
    }


    public void calibraImpressao()throws InterruptedException {

//            AppletPrinter printer = new AppletPrinter();
//            printer.selecionaImpressora("Intermec");
//            String strReset = "\u0002\u001bT\u0003     \u0002 ENTER TEST MODE      \u0003 \u0002D\u0003      \u0002 SET FACTORY DEFAULT  \u0003 \u0002R\u0003      \u0002 EXIT TEST MODE       \u0003 ";
//
//            try {
//                DocPrintJob dpj = this.printService.createPrintJob();
//                InputStream stream = new ByteArrayInputStream(strReset.getBytes());
//                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc doc = new SimpleDoc(stream, flavor, (DocAttributeSet)null);
//                dpj.print(doc, new HashPrintRequestAttributeSet());
//                Thread.sleep(20000L);
//            } catch (PrintException var9) {
//                var9.printStackTrace();
//            }
//
//            String strHexa = "\u0002\u000fF5\u0003    \u0002 SET TOP OF FORM   \u0003\u0002\u000fD108\u0003  \u0002 SET SKIP ON \u0003\u0002\u000ff0\u0003    \u0002 SET LABEL REST    \u0003\u0002\u000fn1\u0003    \u0002 SET LABEL RETRACT \u0003\u0002\u001bP\u0003\u0002E3;F3;H0,LINHA 2;f0;o010,005;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,025;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,045;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,065;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d0,30;h1;w1;b0;r0;B6,       ;f0;o090,135;c2,1;d1,14;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E4;F4;H0,LINHA 2;f0;o010,006;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,026;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,046;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,066;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d0,30;h1;w1;b0;r0;B6,       ;f0;o010,120;c6,0;d1,16;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E5;F5;H0,LINHA 2;f0;o010,005;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,025;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,045;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,065;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,085;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d1,30;h1;w1;b1;r0;B6,       ;f0;o090,135;c2,1;d1,14;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E6;F6;H0,LINHA 2;f0;o010,006;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,026;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,046;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,066;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d0,30;h1;w1;b0;r0;B6,       ;f0;o090,135;c2,0;d1,16;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bP\u0003\u0002E7;F7;H0,LINHA 2;f0;o010,006;c20;d0,30;h1;w1;b0;r0;H1,       ;f0;o010,026;c20;d0,30;h1;w1;b0;r0;H2,       ;f0;o010,046;c20;d0,30;h1;w1;b0;r0;H3,       ;f0;o010,066;c20;d0,30;h1;w1;b0;r0;H4,       ;f0;o010,086;c20;d0,30;h1;w1;b0;r0;H5,LINHA 1;f0;o010,110;c20;d1,30;h1;w1;b1;r0;B6,       ;f0;o090,135;c2,0;d1,16;h80;i1;w2;H7,LINHA 1;f0;o010,124;c20;d0,30;h1;w1;b0;r0;\u0003\u0002R\u0003\u0002\u001bE3\u0018 E3 PADRAO INT 2of5 (12D + DV)\u0017\u0003\u0002\u001bE4\u0018 E4 PADRAO CODE 128 (Alfanum)\u0017\u0003";
//
//            try {
//                DocPrintJob dpj = this.printService.createPrintJob();
//                InputStream stream = new ByteArrayInputStream(strHexa.getBytes());
//                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc doc = new SimpleDoc(stream, flavor, (DocAttributeSet)null);
//                dpj.print(doc, new HashPrintRequestAttributeSet());
//            } catch (PrintException var8) {
//                var8.printStackTrace();
//            }
//
        }


}
