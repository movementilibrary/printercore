package br.com.dasa.print.core.redis.model;

public class CalibraImpressora {

    private String macaddress;
    private String unidade;

    public CalibraImpressora(String macaddress, String unidade) {
        this.macaddress = macaddress;
        this.unidade = unidade;
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
}
