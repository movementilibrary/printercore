package br.com.dasa.controllers.components;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dasa.helpers.ImpressoraHelper;
import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.UnidadeJson;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@Component
public class BodyComponent {

	private static final Logger log = LoggerFactory.getLogger(BodyComponent.class);

	@Autowired
	private ImpressoraHelper impressoraHelper;
	private String impressoraSelecionada; 
	private EmpresaJson empresaSelecionada; 
	private UnidadeJson unidadeSelecionada; 

	public VBox getBody() {

		VBox box = new VBox();
		box.getChildren().add(getSelects());
		box.getChildren().add(getSeparator());

		return box;

	}

	private Separator getSeparator() {
		final Separator separator = new Separator();
		separator.setValignment(VPos.CENTER);
		return separator;
	}

	private HBox getSelects() {
		HBox panel = new HBox();
		try {
			panel.getChildren().add(
					getComponenteSelect(getLabelSelect("Impressora"), getSelectImpressoras(), new Insets(0, 0, 0, 0)));
			panel.getChildren()
					.add(getComponenteSelect(getLabelSelect("Empresas"), getSelectEmpresas(), new Insets(0, 0, 0, 50)));
			panel.getChildren()
					.add(getComponenteSelect(getLabelSelect("Unidades"), getSelectUnidades(), new Insets(0, 0, 0, 50)));
			panel.getChildren().add(getButtonSelect()); 
			panel.setPadding(new Insets(60, 10, 20, 10));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return panel;
	}

	private StackPane getButtonSelect() {
		
		StackPane pane = new StackPane(); 
		
		Button button = new Button("Salvar"); 
		pane.setPadding(new Insets(39, 0, 0, 30));
		pane.getChildren().add(button);
		return pane;
	}

	private Label getLabelSelect(String name) {
		Label label = new Label(name);
		label.setFont(Font.font("Comic Sans", 20));
		label.setPadding(new Insets(0, 5, 10, 0));
		return label;
	}

	private VBox getComponenteSelect(Label label, ChoiceBox select, Insets insets) {
		VBox box = new VBox();
		box.getChildren().add(label);
		box.getChildren().add(select);
		if (insets != null) {
			box.setPadding(insets);
		}
		return box;
	}

	private ChoiceBox getSelectUnidades() {
		ArrayList<String> lista = new ArrayList<>();
		lista.add("Paulista");
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(lista));
		cb.setPrefSize(150, 20);
		return cb;
	}

	private ChoiceBox getSelectEmpresas() {
		ArrayList<String> lista = new ArrayList<>();
		lista.add("Delboni");
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(lista));
		cb.setPrefSize(150, 20);
		return cb;
	}

	private ChoiceBox getSelectImpressoras() {
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(impressoraHelper.getImpressoras()));
		cb.setPrefSize(150, 20);
		return cb;
	}
}
