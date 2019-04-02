package br.com.dasa.print.core.oracle.model;

import java.io.Serializable;

//@Entity
//@Table(name = "EmpImg")
public class EmpImg implements Serializable {

    //@Id
    //@Column(name = "EmpImg_codigo")
    private Integer codigo;
    //@Column(name = "EmpImg_nome")
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
