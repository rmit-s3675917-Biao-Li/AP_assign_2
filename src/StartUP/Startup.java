package StartUP;

import controller.DatabaseController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import view.HomePage;

public class Startup extends Application {
	public static FlowPane content;

	public static void main(String[] args) {
		DatabaseController.connect();
		controller.DataStorage.initialization();
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		primaryStage --> BorderPane --> {top --> menuBar
//										center --> FlowPane}	
		new controller.SaveHandler();
		BorderPane bp = new BorderPane();
		bp.setPrefSize(2000, 1000);
		
		content = new FlowPane();
		ScrollPane sp = new ScrollPane();
		sp.setContent(content);
		bp.setTop(HomePage.addMenu());
		bp.setCenter(sp);
		HomePage.ChangeToPropertyListInterface();

		sp.setFitToWidth(true);
		primaryStage.setScene(new Scene(bp));
		primaryStage.show();

	}

}
