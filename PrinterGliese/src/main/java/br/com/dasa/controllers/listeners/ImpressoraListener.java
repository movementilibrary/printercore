package br.com.dasa.controllers.listeners;

import java.util.List;

import br.com.dasa.dtos.ImpressoraDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ImpressoraListener implements ChangeListener<Number>{

	private ImpressoraDTO impressora;
	private List<ImpressoraDTO> lista;

	public ImpressoraListener(ImpressoraDTO impressora, List<ImpressoraDTO> lista) {
		this.impressora = impressora;
		this.lista = lista;
		
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> ov, Number value, Number newValue) {
		this.impressora = lista.get(newValue.intValue());
		
	}

}
