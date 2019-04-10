package br.com.dasa.print.core.redis.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Impressora")
public class Impressora implements Serializable{

   
	@Id
    private String identificacao;
    private LocalDateTime ultimaAtualizacao;
    private String unidade;
    private String empresa;
    private String nome;

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