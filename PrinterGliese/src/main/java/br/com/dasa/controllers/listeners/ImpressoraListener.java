package br.com.dasa.controllers.listeners;

import java.util.List;

import org.springframework.beans.BeanUtils;

import br.com.dasa.dtos.ImpressoraDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ImpressoraListener implements ChangeListener<Number>{

	private final ImpressoraDTO impressora;
	private List<ImpressoraDTO> lista;

	public ImpressoraListener(ImpressoraDTO impressora, List<ImpressoraDTO> lista) {
		this.impressora = impressora;
		this.lista = lista;
		
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> ov, Number value, Number newValue) {
		if ((!lista.isEmpty()) && newValue.intValue() > -1) {
			BeanUtils.copyProperties(lista.get(newValue.intValue()), this.impressora);
		}else if(newValue.intValue() < 0) {
			BeanUtils.copyProperties(new ImpressoraDTO(), this.impressora);
		}
	}

}
