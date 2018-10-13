package view;

import controller.DatabaseController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI_Startup extends Application {
	public static VBox content;

	public static void main(String[] args) {
		DatabaseController.connect();
		controller.DataStorage.initialization();
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		try {
//			controller.FileController.importFile();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new controller.SaveHandler();
		BorderPane bp = new BorderPane();
		content = new VBox();
		StackPane stack = new StackPane(content);
		ChangeInterface.ChangeToPropertyListInterface();

		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(800, 800);
		sp.setContent(stack);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		content.setMaxWidth(700);

		bp.setTop(ChangeInterface.addMenu());
		bp.setCenter(sp);

		sp.setFitToWidth(true);
		// sp.setFitToHeight(true);
		primaryStage.setScene(new Scene(bp));
		primaryStage.show();

	}

}
