package br.com.dasa.print.core.h2.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Impressora implements Serializable {

    @Id
    @NotNull
    private String identificacao;
    private Date ultimaAtualizacao;
    @NotNull
    private String unidade;
    @NotNull
    private String nome;
    @NotNull
    private String empresa;

    public Impressora() { }


    public Impressora(@NotNull String identificacao, Date ultimaAtualizacao, @NotNull String unidade, @NotNull String nome, @NotNull String empresa) {
        this.identificacao = identificacao;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.unidade = unidade;
        this.nome = nome;
        this.empresa = empresa;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}