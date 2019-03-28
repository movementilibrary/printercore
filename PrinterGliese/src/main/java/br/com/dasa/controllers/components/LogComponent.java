package br.com.dasa.controllers.components;

import org.springframework.stereotype.Component;

import br.com.dasa.enums.LogEnum;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@Component
public class LogComponent {

	private final VBox box; 
	private final ScrollPane scroll; 
	
	public LogComponent() {
		scroll = new ScrollPane();
		box = new VBox(); 
		try {
		
		box.setStyle("-fx-background-color: #333333;");
		box.setPrefSize(680, 490);
		scroll.setContent(box);
		scroll.setPrefSize(680, 490);
		scroll.setVmax(400);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public VBox getLogComponent() {
		return box; 
	}
	
	public void addLog(String msg, LogEnum logEnum) {
		Label label = new Label(msg); 
		label.setTextFill(Color.web(logEnum.getColor()));
		box.getChildren().add(label);
	}
}
