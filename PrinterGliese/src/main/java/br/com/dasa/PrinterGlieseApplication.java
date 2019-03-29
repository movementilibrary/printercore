package br.com.dasa;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import br.com.dasa.controllers.PainelController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableAsync
@ComponentScan
public class PrinterGlieseApplication extends Application {

	private static final Logger log = LoggerFactory.getLogger(PrinterGlieseApplication.class);
	
	private ConfigurableApplicationContext springContext;
	private Scene scene;

	public static void main(final String[] args) {
		launch(PrinterGlieseApplication.class, args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(PrinterGlieseApplication.class);
		PainelController controller = springContext.getBean(PainelController.class);
		scene = new Scene(controller.getPanel());

	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}

	@Override
	public void start(Stage stage) throws Exception {
		startApplication(stage);
	}

	private void startApplication(final Stage primaryStage) {
		log.info("Iniciando aplicação");
		primaryStage.setTitle("Gliese");
		primaryStage.setHeight(750);
		primaryStage.setWidth(680);
		primaryStage.centerOnScreen();
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	@Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GlieseLookup-");
        executor.initialize();
        return executor;
    }


}
