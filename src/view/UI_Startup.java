package view;

import java.util.List;
import controller.Handler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI_Startup extends Application implements EventHandler<ActionEvent> {
	
	public static void main(String[] args) {
	launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vbox = new VBox();
		addMenu(vbox);
		addFilter(vbox);
		
		Scene scene = new Scene(vbox, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void addFilter(VBox vbox) {
		ScrollPane sp = new ScrollPane();
		vbox.getChildren().add(sp);
	}
	

	public void addMenu(VBox v) {
		MenuBar menubar = new MenuBar();
		Menu systemMenu = new Menu("DBJ Motel");
		MenuItem Logout = new MenuItem("Logout");
		MenuItem Exit = new MenuItem("Exit");
		systemMenu.getItems().addAll(Logout,Exit);
		
		Menu fileMenu = new Menu("File");
		MenuItem importMenu = new MenuItem("Import...");
		MenuItem exportMenu = new MenuItem("Export...");
		fileMenu.getItems().addAll(importMenu,exportMenu);
		
		Menu propertyMenu = new Menu("Property");
		MenuItem viewMenu = new MenuItem("List");
		viewMenu.setOnAction(e -> System.out.println(11111));
		MenuItem addMenu = new MenuItem("Add");
		MenuItem rentMenu = new MenuItem("Rent");
		MenuItem returnMenu = new MenuItem("Return");
		
		propertyMenu.getItems().addAll(viewMenu,addMenu,rentMenu,returnMenu);
		
		menubar.getMenus().addAll(systemMenu,fileMenu,propertyMenu);
		v.getChildren().add(menubar);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		
	}

}
