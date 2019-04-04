package br.com.dasa.print.core.redis.model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;


@RedisHash("Impressao")
public class Impressao implements Serializable {

    private String impressora;
    private String conteudoImpressao;

    public Impressao() { }

    public Impressao(String impressora, String conteudoImpressao) {
        this.impressora = impressora;
        this.conteudoImpressao = conteudoImpressao;
    }


    public String getImpressora() {
        return impressora;
    }

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }

    public String getConteudoImpressao() {
        return conteudoImpressao;
    }

    public void setConteudoImpressao(String conteudoImpressao) {
        this.conteudoImpressao = conteudoImpressao;
    }
}
