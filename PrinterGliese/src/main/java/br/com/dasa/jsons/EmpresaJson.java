package br.com.dasa.jsons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class EmpresaJson implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome; 
	private String cod;
	
	public EmpresaJson() {
		
	}
	
	public EmpresaJson(String nome, String cod) {
		this.nome = nome; 
		this.cod = cod;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	} 
	
}
