package br.com.dasa.printcore.redis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Unidade implements Serializable {

    private String macaddress;
    private String nome;

    public Unidade() {

    }

    public Unidade(String macaddress,String nome) {
        this.macaddress = macaddress;
        this.nome = nome;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
