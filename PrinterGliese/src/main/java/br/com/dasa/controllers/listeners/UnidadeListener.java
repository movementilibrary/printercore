package br.com.dasa.controllers.listeners;

import java.util.List;

import org.springframework.beans.BeanUtils;

import br.com.dasa.jsons.UnidadeJson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UnidadeListener implements ChangeListener<Number> {

	private final UnidadeJson unidade;
	private List<UnidadeJson> lista;

	public UnidadeListener(UnidadeJson unidade, List<UnidadeJson> lista) {
		this.unidade = unidade;
		this.lista = lista;

	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if ((!lista.isEmpty()) && newValue.intValue() > -1) {
			BeanUtils.copyProperties(lista.get(newValue.intValue()), this.unidade);
		}else if(newValue.intValue() < 0) {
			BeanUtils.copyProperties(new UnidadeJson(), this.unidade);
		}
	}

}
