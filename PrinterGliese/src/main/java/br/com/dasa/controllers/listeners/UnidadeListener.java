package br.com.dasa.controllers.listeners;

import java.util.List;

import br.com.dasa.jsons.UnidadeJson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UnidadeListener implements ChangeListener<Number>{

	private UnidadeJson unidade;
	private List<UnidadeJson> lista;

	public UnidadeListener(UnidadeJson unidade, List<UnidadeJson> lista) {
		this.unidade = unidade;
		this.lista = lista;
		
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		this.unidade = lista.get(newValue.intValue());
		
	}

}
