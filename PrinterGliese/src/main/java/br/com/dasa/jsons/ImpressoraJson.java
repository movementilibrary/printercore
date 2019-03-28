package br.com.dasa.jsons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImpressoraJson implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("identificacao")
	private String fila;
	@JsonProperty("unidade")
	private String codUnidade;
	@JsonProperty("empresa")
	private String codEmpresa;
	@JsonProperty("nome")
	private String nomeImpressora; 

	public ImpressoraJson(String macAddress, String nomeImpressora, String codEmpresa, String codUnidade) {
		this.fila = macAddress; 
		this.nomeImpressora = nomeImpressora;
		this.codEmpresa = codEmpresa; 
		this.codUnidade = codUnidade; 
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public String getCodUnidade() {
		return codUnidade;
	}

	public void setCodUnidade(String codUnidade) {
		this.codUnidade = codUnidade;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getNomeImpressora() {
		return nomeImpressora;
	}

	public void setNomeImpressora(String nomeImpressora) {
		this.nomeImpressora = nomeImpressora;
	}

}
