package br.com.dasa.controllers.listeners;

import java.util.List;

import br.com.dasa.jsons.EmpresaJson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class EmpresaListener implements ChangeListener<Number>{

	private EmpresaJson empresa;
	private List<EmpresaJson> lista; 
	
	public EmpresaListener(EmpresaJson empresa, List<EmpresaJson> lista) {
		this.empresa = empresa; 
		this.lista = lista; 
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		
		this.empresa = lista.get(newValue.intValue()); 
	}

}
