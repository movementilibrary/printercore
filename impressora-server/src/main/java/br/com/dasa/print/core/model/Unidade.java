package br.com.dasa.print.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Unidade implements Serializable {

    @Id
    private Integer id;
    private String nome;
    private String mnemocico;


    public Unidade(Integer id, String nome, String mnemocico) {
        this.id = id;
        this.nome = nome;
        this.mnemocico = mnemocico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMnemocico() {
        return mnemocico;
    }

    public void setMnemocico(String mnemocico) {
        this.mnemocico = mnemocico;
    }
}
