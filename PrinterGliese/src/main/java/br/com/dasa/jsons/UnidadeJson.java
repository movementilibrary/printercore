package br.com.dasa.jsons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class UnidadeJson implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome; 
	private String mnemonico;
	
	public UnidadeJson() {
		
	}
	
	public UnidadeJson(String nome, String mnemonico) {
		this.nome = nome; 
		this.mnemonico = mnemonico; 
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMnemonico() {
		return mnemonico;
	}
	public void setMnemonico(String mnemonico) {
		this.mnemonico = mnemonico;
	} 
	
	@Override
	public String toString() {
		return this.nome; 
	}

}
