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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mnemonico == null) ? 0 : mnemonico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadeJson other = (UnidadeJson) obj;
		if (mnemonico == null) {
			if (other.mnemonico != null)
				return false;
		} else if (!mnemonico.equals(other.mnemonico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nome; 
	}

}
