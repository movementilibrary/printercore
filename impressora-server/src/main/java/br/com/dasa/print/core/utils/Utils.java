package br.com.dasa.print.core.utils;

import br.com.dasa.print.core.model.Impressora;

import java.util.Date;

public class Utils {

    /**
     * Metodo Responsável por executar ultima atualizacao
     * @param impressora
     * @return
     */
    public static Impressora atualizaHoraImpressora(Impressora impressora) {

        impressora.setUltimaAtualizacao(new Date());
        return impressora;
    }

}
