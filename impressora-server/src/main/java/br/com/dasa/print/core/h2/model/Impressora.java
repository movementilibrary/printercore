package br.com.dasa.print.core.h2.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Impressora")
public class Impressora implements Serializable{

   
    //@NotNull
	@Id
    private String identificacao;
    private LocalDateTime ultimaAtualizacao;
    //@NotNull
    private String unidade;
    //@NotNull
    private String nome;
    //@NotNull
    private String empresa;

    public Impressora() { }


    public Impressora(String identificacao, LocalDateTime ultimaAtualizacao, String unidade, String nome, String empresa) {
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

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
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