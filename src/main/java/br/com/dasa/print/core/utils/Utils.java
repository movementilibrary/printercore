package br.com.dasa.print.core.utils;

import br.com.dasa.print.core.redis.model.Impressora;

public class Utils {


    /**
     * Metodo Responsável por criar Id Impressora
     * @param impressora
     * @return
     */
    public static Impressora criaIdImpressora(Impressora impressora){
        impressora.setId(impressora.getUnidade().concat("-").concat(impressora.getNome()));
        return impressora;
    }
}
