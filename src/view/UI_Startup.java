package view;

import java.util.ArrayList;
import java.util.List;

import controller.ChangeInterface;
import controller.Handler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Property;

public class UI_Startup extends Application  {
	private ArrayList<Property> propertyList = new ArrayList<Property>();
	private VBox content;
	public static void main(String[] args) {
	launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vbox = new VBox();
		addMenu(vbox);
		content = new VBox();
		ScrollPane sp = new ScrollPane();
		sp.setContent(content);
		vbox.getChildren().add(sp);
		Scene scene = new Scene(vbox, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		
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
		viewMenu.setOnAction(e -> {
			new ChangeInterface().ChangeToPropertyListInterface(content);
			System.out.println("change a property list view");
		});
		MenuItem addMenu = new MenuItem("Add");
		MenuItem rentMenu = new MenuItem("Rent");
		MenuItem returnMenu = new MenuItem("Return");
		
		propertyMenu.getItems().addAll(viewMenu,addMenu,rentMenu,returnMenu);
		
		menubar.getMenus().addAll(systemMenu,fileMenu,propertyMenu);
		v.getChildren().add(menubar);
	}


}
