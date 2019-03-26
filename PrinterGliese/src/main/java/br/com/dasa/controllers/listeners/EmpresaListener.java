package br.com.dasa.controllers.listeners;

import java.util.List;

import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.UnidadeJson;
import br.com.dasa.services.PrinterCoreService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class EmpresaListener implements ChangeListener<Number> {

	private EmpresaJson empresa;
	private final List<EmpresaJson> lista;
	private final PrinterCoreService printerCoreService;
	private final ChoiceBox selectUnidades;

	public EmpresaListener(EmpresaJson empresa, List<EmpresaJson> lista, ChoiceBox selectUnidades,
			PrinterCoreService printerCoreService) {
		this.empresa = empresa;
		this.lista = lista;
		this.selectUnidades = selectUnidades; 
		this.printerCoreService = printerCoreService; 
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

		this.empresa = lista.get(newValue.intValue());
		selectUnidades.getItems().clear();
		if (this.empresa != null) {
			selectUnidades.getItems().addAll(FXCollections.observableArrayList(printerCoreService.getUnidades(this.empresa.getCod()))); 
		}
	}

}
