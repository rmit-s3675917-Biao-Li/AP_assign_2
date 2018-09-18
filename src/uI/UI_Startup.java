package uI;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI_Startup extends Application{

	public static void main(String[] args) {
	launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vbox = new VBox();
		addMenu(vbox);
		addFeature(vbox);
		Scene scene = new Scene(vbox, 600, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void addFeature(VBox vbox) {
		HBox featureHbox = new HBox();
		featureHbox.setSpacing(5);
		Button propertyButton = new Button("Property");
		propertyButton.setMinWidth(100);
		Button rentButton = new Button("Rent");
		rentButton.setMinWidth(100);
		Button returnButton = new Button("Return");
		returnButton.setMinWidth(100);
		Button customerButton = new Button("Customer");
		customerButton.setMinWidth(100);
		
		featureVbox.getChildren().addAll(propertyButton,rentButton,returnButton,customerButton);
		hbox.getChildren().add(featureVbox);
		vbox.getChildren().add(hbox);
	}

	public void addMenu(VBox v) {
		MenuBar menubar = new MenuBar();
		Menu exitMenu = new Menu("Exit");
		Menu logoutMenu = new Menu("Logout");
		menubar.getMenus().addAll(exitMenu,logoutMenu);
		v.getChildren().add(menubar);
	}
}
