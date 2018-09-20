package view;

import java.util.ArrayList;
import java.util.List;

import controller.ChangeInterface;
import controller.Handler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Property;

public class UI_Startup extends Application  {
	private VBox content;
	private ArrayList<Property> propertyList = new ArrayList<Property>();
	
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
		HBox hMenu = new HBox();
		MenuBar menubar = new MenuBar();
		Menu systemMenu = new Menu("FlexiRentSystem");
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
			System.out.println("change to property list interface");
		});
		MenuItem addMenu = new MenuItem("Add");
		addMenu.setOnAction(e ->{
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			VBox addPropertyVbox = new VBox();
			Scene scene = new Scene(addPropertyVbox, 400, 300);
			stage.setScene(scene);
			stage.show();
			new ChangeInterface().ChangeToAddPropertyInterface(addPropertyVbox);
			System.out.println("change to add property interface");
		});
		MenuItem rentMenu = new MenuItem("Rent");
		MenuItem returnMenu = new MenuItem("Return");
		
		propertyMenu.getItems().addAll(viewMenu,addMenu,rentMenu,returnMenu);
		menubar.setMinSize(500, 20);
		menubar.getMenus().addAll(systemMenu,fileMenu,propertyMenu);
		Label search = new Label("Search");
		search.setAlignment(Pos.BOTTOM_CENTER);
		search.setMinWidth(50);
		search.setPadding(new Insets(5,10,5,10));
		hMenu.getChildren().addAll(menubar, search,new TextField(""));
		v.getChildren().add(hMenu);
	}


}
