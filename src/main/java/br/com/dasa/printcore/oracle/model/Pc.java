package br.com.dasa.printcore.oracle.model;

import java.io.Serializable;

public class Pc implements Serializable {

    private String mnemonico;
    
    private String nome;

    public Pc() { }

    public Pc(String nome, String mnemonico) {
        this.nome = nome;
        this.mnemonico = mnemonico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMnemonico() {
        return mnemonico;
    }

    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }
}
