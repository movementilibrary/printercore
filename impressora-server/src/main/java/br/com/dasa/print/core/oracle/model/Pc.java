package br.com.dasa.print.core.oracle.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Pc implements Serializable {

    @Id
    @Column(name = "Pc_Empresa")
    private String empresa;
    @Column(name = "Pc_Mnemonico")
    private String mnemonico;

    public Pc() { }

    public Pc(String empresa, String mnemonico) {
        this.empresa = empresa;
        this.mnemonico = mnemonico;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getMnemonico() {
        return mnemonico;
    }

    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }
}
