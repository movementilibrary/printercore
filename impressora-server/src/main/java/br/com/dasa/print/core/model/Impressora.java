package br.com.dasa.print.core.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Impressora implements Serializable {

    @Id
    private String identificacao;
    private Date ultimaAtualizacao;
    private String unidade;

    public Impressora(){}

    public Impressora(String identificacao, Date ultimaAtualizacao, String unidade) {
        this.identificacao = identificacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.unidade = unidade;

    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

}