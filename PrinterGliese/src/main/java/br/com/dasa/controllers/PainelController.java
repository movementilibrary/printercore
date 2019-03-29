package br.com.dasa.controllers;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dasa.consumers.ConsumerMQ;
import br.com.dasa.controllers.components.BodyComponent;
import br.com.dasa.controllers.components.LogComponent;
import br.com.dasa.enums.LogEnum;
import br.com.dasa.helpers.DadosImpressaoHelper;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@Component
public class PainelController {

	private static final Logger log = LoggerFactory.getLogger(PainelController.class);

	@Autowired
	private DadosImpressaoHelper dadosImpressaoHelper;
	@Autowired
	private ConsumerMQ consumerMQ;
	@Autowired
	private BodyComponent bodyComponent;
	@Autowired
	private LogComponent logComponent;

	@PostConstruct
	public void iniciar() {
		try {
			boolean dadosImpressaoPreenchidos = dadosImpressaoHelper.validarDadosImpressoraPreenchidos();
			if (dadosImpressaoPreenchidos) {
				consumerMQ.consome();
			} else {
				logComponent.addLog("Os dados de impressão não foram preenchidos", LogEnum.WARN);
			}
		} catch (Exception e) {
			logComponent.addLog("Erro ao recuperar dados de impressão", LogEnum.ERROR);
			log.error(e.getMessage(), e);
		}
	}

	public VBox getPanel() {
		VBox box = new VBox();
		box.getChildren().add(getHeader());
		box.getChildren().add(bodyComponent.getBody());
		box.getChildren().add(logComponent.getLogComponent());
		box.setPrefSize(680, 750);

		return box;
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
