package br.com.dasa.printcore.oracle.model;

import java.io.Serializable;

public class EmpImg implements Serializable {

    private Integer codigo;
    private String nome;


    public EmpImg() { }

    public EmpImg(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
