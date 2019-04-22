package br.com.dasa.print.core.redis.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("Impressora")
public class Impressora implements Serializable{

   
	@Id
	private String id;
    private String macaddress;
    private LocalDateTime ultima_atualizacao;
    private String unidade;
    private String empresa;
    private String nome;

    public Impressora() { }

    public Impressora(String id, String macaddress, LocalDateTime ultima_atualizacao, String unidade, String empresa, String nome) {
        this.id = id;
        this.macaddress = macaddress;
        this.ultima_atualizacao = ultima_atualizacao;
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

    public LocalDateTime getUltima_atualizacao() {
        return ultima_atualizacao;
    }

    public void setUltima_atualizacao(LocalDateTime ultima_atualizacao) {
        this.ultima_atualizacao = ultima_atualizacao;
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