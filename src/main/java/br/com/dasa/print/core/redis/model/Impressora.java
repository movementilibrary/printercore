package br.com.dasa.print.core.redis.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("Impressora")
//TODO: Colocar bean validation e escrever testes para validacao
public class Impressora implements Serializable{

   
	@Id
    private String macaddress;
	private String id;
    private LocalDateTime ultimaAtualizacao;
    private String unidade;
    private String empresa;
    private String nome;

    public Impressora() { }

    public Impressora(String macaddress, String id, LocalDateTime ultimaAtualizacao, String unidade, String empresa, String nome) {
        this.macaddress = macaddress;
        this.id = id;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.unidade = unidade;
        this.empresa = empresa;
        this.nome = nome;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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