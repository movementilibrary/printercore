package br.com.dasa.printcore.redis.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ImpressoraDto {


    private String macaddress;
    private String unidade;
    private String empresa;
    private String nome;

    public ImpressoraDto() {

    }

    public ImpressoraDto(String macaddress, String unidade, String empresa, String nome) {
        this.macaddress = macaddress;
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
