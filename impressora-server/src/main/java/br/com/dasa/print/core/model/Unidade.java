package br.com.dasa.print.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Unidade implements Serializable {

    @Id
    private Integer identificacao;
    private String nome;
    private String mnemocico;


    public Unidade(Integer identificacao, String nome, String mnemocico) {
        this.identificacao = identificacao;
        this.nome = nome;
        this.mnemocico = mnemocico;
    }

    public Integer getIdentificacao() {
        return identificacao;
    }

    public void setId(Integer id) {
        this.identificacao = identificacao;
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
