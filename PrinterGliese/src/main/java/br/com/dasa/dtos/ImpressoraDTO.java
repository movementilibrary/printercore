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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeRede == null) ? 0 : nomeRede.hashCode());
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
		ImpressoraDTO other = (ImpressoraDTO) obj;
		if (nomeRede == null) {
			if (other.nomeRede != null)
				return false;
		} else if (!nomeRede.equals(other.nomeRede))
			return false;
		return true;
	}
	
	
}
