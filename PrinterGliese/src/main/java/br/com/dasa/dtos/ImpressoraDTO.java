package br.com.dasa.dtos;

import java.io.Serializable;

public class ImpressoraDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeRede;
	private String nome;

	public ImpressoraDTO() {
		
	}
	
	public ImpressoraDTO(String nomeRede, String nome) {
		this.nomeRede = nomeRede; 
		this.nome = nome; 
	}
	
	public String getNomeRede() {
		return nomeRede;
	}

	public void setNomeRede(String nomeRede) {
		this.nomeRede = nomeRede;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return this.nomeRede; 
	}
}
