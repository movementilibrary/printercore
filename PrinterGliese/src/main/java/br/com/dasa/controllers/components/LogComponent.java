package br.com.dasa.controllers.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.dasa.enums.LogEnum;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@Component
public class LogComponent {
	
	private static final Logger log = LoggerFactory.getLogger(LogComponent.class);

	private final VBox box; 
	private final ScrollPane scroll; 
	private DateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss"); 
	
	public LogComponent() {
		scroll = new ScrollPane();
		box = new VBox(); 
		try {
		
		box.setStyle("-fx-background-color: #333333;");
		box.setPrefSize(680, 490);
		scroll.setContent(box);
		scroll.setPrefSize(680, 490);
		scroll.autosize();
		scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public ScrollPane getLogComponent() {
		return scroll; 
	}
	
	public void addLog(String msg, LogEnum logEnum) {
		
		if(box.getChildren().size() > 200) {
			box.getChildren().clear();
		}
		
		Label label = new Label(format.format(new Date()).concat(": ").concat(msg)); 
		label.setTextFill(Color.web(logEnum.getColor()));
		box.getChildren().add(label);
	}
}
