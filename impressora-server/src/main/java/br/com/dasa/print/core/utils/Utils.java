package br.com.dasa.print.core.utils;

import br.com.dasa.print.core.h2.model.Impressora;

import java.time.LocalDateTime;
import java.util.Date;

public class Utils {

    /**
     * Metodo Responsável por executar ultima atualizacao
     * @param impressora
     * @return
     */
    public static Impressora atualizaHoraImpressora(Impressora impressora) {

        impressora.setUltimaAtualizacao(LocalDateTime.now());
        return impressora;
    }

}
