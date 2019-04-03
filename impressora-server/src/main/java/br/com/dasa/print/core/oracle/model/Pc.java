package br.com.dasa.print.core.oracle.model;

import java.io.Serializable;

//@Entity
public class Pc implements Serializable {

    private String mnemonico;
    
    private String empresa;

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
