package br.com.dasa.controllers.listeners;

import java.util.List;

import org.springframework.beans.BeanUtils;

import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.UnidadeJson;
import br.com.dasa.services.PrinterCoreService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class EmpresaListener implements ChangeListener<Number> {

	private final EmpresaJson empresa;
	private final List<EmpresaJson> lista;
	private final List<UnidadeJson> listaUnidade; 
	private final PrinterCoreService printerCoreService;
	private final ChoiceBox selectUnidades;

	public EmpresaListener(EmpresaJson empresa, List<EmpresaJson> lista, List<UnidadeJson> listaUnidade, ChoiceBox selectUnidades,
			PrinterCoreService printerCoreService) {
		this.empresa = empresa;
		this.lista = lista;
		this.selectUnidades = selectUnidades; 
		this.printerCoreService = printerCoreService; 
		this.listaUnidade = listaUnidade;
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

		BeanUtils.copyProperties(lista.get(newValue.intValue()), this.empresa);
		
		selectUnidades.getItems().clear();
		this.listaUnidade.clear();
		if (this.empresa != null) {
			this.listaUnidade.addAll(printerCoreService.getUnidades(this.empresa.getCod())); 
			selectUnidades.getItems().addAll(FXCollections.observableArrayList(this.listaUnidade)); 
		}
	}

}
