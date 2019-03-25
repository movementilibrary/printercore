package br.com.dasa.print.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
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
