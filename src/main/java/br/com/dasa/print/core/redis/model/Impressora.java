package br.com.dasa.print.core.redis.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;

@RedisHash("Impressora")
public class Impressora implements Serializable{

   
	@Id
	private String id;
    private String macaddress;

    @JsonProperty("ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    private String unidade;
    private String empresa;
    private String nome;

    public Impressora() { }

    public Impressora(String id, String macaddress, LocalDateTime ultimaAtualizacao, String unidade, String empresa, String nome) {
        this.id = id;
        this.macaddress = macaddress;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.unidade = unidade;
        this.empresa = empresa;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}