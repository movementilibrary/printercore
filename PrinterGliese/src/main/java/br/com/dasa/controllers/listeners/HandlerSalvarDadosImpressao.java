package br.com.dasa.controllers.listeners;

import br.com.dasa.dtos.ImpressoraDTO;
import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.UnidadeJson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HandlerSalvarDadosImpressao implements EventHandler<ActionEvent>{

	private final ImpressoraDTO impressoraSelecionada;
	private final EmpresaJson empresaSelecionada;
	private final UnidadeJson unidadeSelecionada;

	public HandlerSalvarDadosImpressao(ImpressoraDTO impressoraSelecionada, EmpresaJson empresaSelecionada, UnidadeJson unidadeSelecionada) {
		this.impressoraSelecionada = impressoraSelecionada;
		this.empresaSelecionada = empresaSelecionada;
		this.unidadeSelecionada = unidadeSelecionada;
	
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		
		
	}

}
