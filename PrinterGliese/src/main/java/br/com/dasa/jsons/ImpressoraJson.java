package br.com.dasa.jsons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImpressoraJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fila;
	@JsonProperty("mnemonico")
	private String codUnidade;
	@JsonProperty("cod_empresa")
	private String codEmpresa;
	@JsonProperty("nome_impressora")
	private String nomeImpressora; 

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
