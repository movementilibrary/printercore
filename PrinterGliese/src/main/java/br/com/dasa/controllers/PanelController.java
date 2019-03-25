package br.com.dasa.controllers;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dasa.consumers.ConsumerMQ;
import br.com.dasa.helpers.DadosImpressaoHelper;
import br.com.dasa.helpers.ImpressoraHelper;
import br.com.dasa.services.PrinterCoreService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@Component
public class PanelController {

	private static final Logger log = LoggerFactory.getLogger(PanelController.class);


	
	@Autowired
	private ImpressoraHelper impressoraHelper;
	@Autowired
	private PrinterCoreService printerCoreService;
	@Autowired
	private DadosImpressaoHelper dadosImpressaoHelper; 
	@Autowired
	private ConsumerMQ consumerMQ;
	
	private boolean dadosImpressaoPreenchidos;  


	@PostConstruct
	public void iniciar() {
		try {
			this.dadosImpressaoPreenchidos = dadosImpressaoHelper.validarDadosImpressoraPreenchidos(); 
			if(dadosImpressaoPreenchidos) {
				consumerMQ.consome();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	

	public VBox getPanel() {
		VBox box = new VBox();
		box.getChildren().add(getHeader());
		box.getChildren().add(getBody());
		box.setPrefSize(680, 750);

		return box;
	}

	private HBox getBody() {
		HBox panel = new HBox();
		try {
			panel.getChildren().add(getComponenteImpressoras()); 
			
			panel.setPadding(new Insets(60, 10, 20, 10));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return panel;
	}

	private VBox getComponenteImpressoras() {
		VBox box = new VBox(); 
		
		box.getChildren().add(getLabelImpressora());
		box.getChildren().add(getSelectImpressoras());
		
		return box;
	}



	private ChoiceBox getSelectUnidades() {
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(new ArrayList<>()));
		cb.setPrefSize(150, 20);
		return cb;
	}



	private ChoiceBox getSelectEmpresas() {
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(new ArrayList<>()));
		cb.setPrefSize(150, 20);
		return null;
	}



	private ChoiceBox getSelectImpressoras() {
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(impressoraHelper.getImpressoras()));
		cb.setPrefSize(150, 20);
		return cb;
	}

	private Label getLabelImpressora() {
		Label label = new Label("Impressora");
		label.setFont(Font.font("Comic Sans", 20));
		
		label.setPadding(new Insets(0, 5, 10, 0));
		return label;
	}

	private StackPane getHeader() {
		StackPane panel = new StackPane();

		panel.setStyle("-fx-background-color: #00FFFF;");
		panel.getChildren().add(getLabel());

		return panel;
	}

	private Label getLabel() {

		Label label = new Label("DASA");
		label.setTextFill(Color.web("#0000FF"));
		label.setFont(Font.font("Comic Sans", 50));
		return label;
	}
}
