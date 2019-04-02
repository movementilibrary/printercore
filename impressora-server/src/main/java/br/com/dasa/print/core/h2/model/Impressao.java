package br.com.dasa.print.core.h2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;


@RedisHash("Impressao")
public class Impressao implements Serializable {

    private String identificacao;
    private String conteudoImpressao;

    public Impressao(String identificacao, String conteudoImpressao) {
        this.identificacao = identificacao;
        this.conteudoImpressao = conteudoImpressao;
    }


    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getConteudoImpressao() {
        return conteudoImpressao;
    }

    public void setConteudoImpressao(String conteudoImpressao) {
        this.conteudoImpressao = conteudoImpressao;
    }
}
