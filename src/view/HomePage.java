package view;

import StartUP.Startup;
import controller.DataStorage;
import controller.SaveHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Property;

public class HomePage {
	public static ComboBox<String> AorP = new ComboBox<String>();
	public static ComboBox<String> BedNum = new ComboBox<String>();
	public static ComboBox<String> Statuscombo = new ComboBox<String>();
	public static ComboBox<String> Suburbcombo;
	

	public static void ChangeToPropertyListInterface() {
		Startup.content.getChildren().clear();
		AorP.setValue("All");
		BedNum.setValue("All");
		Statuscombo.setValue("All");
		Suburbcombo.setValue("All");
		new controller.FillterListener().changed(null, 0, 1);;

	}

	public static void addOnePropertytoContent(Property p) {
//		one ProperyViewHbox shows one Property's information,
//		ProperyViewHbox = ImageView + ProperyDetailsVbox.
		HBox ProperyViewHbox = new HBox();
		
		//ProperyViewHbox
		ProperyViewHbox.setMaxWidth(700);
		ProperyViewHbox.setMinWidth(700);
		ProperyViewHbox.setSpacing(10);
		ProperyViewHbox.setPadding(new Insets(20, 5, 20, 5));
		
		//ImageView
		ImageView iv = new ImageView(p.getImage());
		iv.setFitHeight(300);
		iv.setFitWidth(300);
		
		//ProperyDetailsVbox
		VBox ProperyDetailsVbox = new VBox();
		ProperyDetailsVbox.setSpacing(10);
		ProperyDetailsVbox.setPadding(new Insets(0, 5, 5, 5));
		Text PropertyIDText = new Text("Property ID: " + p.getId());
		PropertyIDText.setFont(Font.font("Verdana", 20));
		PropertyIDText.setFill(Color.CORNFLOWERBLUE);
		Text PropertyAddressText = new Text("Address: " + p.getStnum() + " " + p.getStname() + " " + p.getSuburb());
		Text descText = new Text("Description: \n" + p.getDescription22());
		
		Button b = new Button("More Details");
		b.setOnAction(e -> {
			new NewWindowsForPropertyDetails(p);
			System.out.println("Showing property details");
		});

		ProperyDetailsVbox.getChildren().addAll(PropertyIDText, PropertyAddressText, descText, b);
		ProperyViewHbox.getChildren().addAll(iv, ProperyDetailsVbox);
		Startup.content.getChildren().add(ProperyViewHbox);
	}

	private static void AddFilterToContent(VBox content) {
		GridPane filter = new GridPane();
		
		// Property type choiceBox
		Text TypeText = new Text(" Property Type: ");
		TypeText.setTextAlignment(TextAlignment.CENTER);
		AorP.getItems().setAll("All", "Apartment", "Premium Suite");
		AorP.setValue("All");
		AorP.setMinWidth(200);

		// Bedroom number choiceBox
		Text BedroomNumText = new Text(" How Many Bedroom: ");
		BedroomNumText.setTextAlignment(TextAlignment.CENTER);
		BedNum.getItems().setAll("All", "1", "2", "3");
		BedNum.setValue("All");
		BedNum.setMinWidth(100);

		// Status choiceBox
		Text StatusText = new Text(" Status: ");
		StatusText.setTextAlignment(TextAlignment.CENTER);
		Statuscombo.getItems().setAll("All", "Available", "Rented", "Maintaining");
		Statuscombo.setValue("All");
		Statuscombo.setMinWidth(100);

		// Suburb choiceBox
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("All");
		for (int i = 0; i < DataStorage.getPropertyList().size(); i++) {
			if (!list.contains(DataStorage.getPropertyList().get(i).getSuburb()))
				list.add(DataStorage.getPropertyList().get(i).getSuburb());
		}
		Text suburbText = new Text(" Suburb: ");
		StatusText.setTextAlignment(TextAlignment.CENTER);
		Suburbcombo = new ComboBox<String>(list);
		Suburbcombo.setValue("All");
		Suburbcombo.setMinWidth(200);
		
		//Add listener to all the comboBox which will change the showing list if one of the filter being changed  
		BedNum.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		AorP.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		Statuscombo.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		Suburbcombo.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		Suburbcombo.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		
		//Reset Button
		Button confirmButton = new Button("Reset");
		confirmButton.setMinWidth(70);
		confirmButton.setOnAction(e -> {
			ChangeToPropertyListInterface();
		});
		
		//Search view
		Label search = new Label("Search");
		search.setAlignment(Pos.BOTTOM_CENTER);
		search.setMinWidth(80);
		search.setPadding(new Insets(5, 10, 5, 10));
		TextField TF = new TextField();
		TF.setMinWidth(200);
		TF.setOnKeyPressed(new controller.SearchHandler(TF));
		Region r = new Region();
		r.setPrefWidth(5000);
		r.autosize();

		// Add all elements into the filter
		filter.add(TypeText, 0, 0);
		filter.add(AorP, 1, 0);
		filter.add(StatusText, 2, 0);
		filter.add(Statuscombo, 3, 0);
		filter.add(BedroomNumText, 4, 0);
		filter.add(BedNum, 5, 0);
		filter.add(suburbText, 6, 0);
		filter.add(Suburbcombo, 7, 0);
		filter.add(confirmButton, 8, 0);
		filter.add(r, 9, 0);
		filter.add(search, 10, 0);
		filter.add(TF, 11, 0);
		filter.setHgap(5);
		filter.setVgap(5);
		filter.setPadding(new Insets(5, 5, 5, 5));
		
		// add the filter to the interface
		content.getChildren().add(filter);

	}

	public static VBox addMenu() {
		VBox topBox = new VBox();
		HBox hMenu = new HBox();
		MenuBar menubar = new MenuBar();
		Menu systemMenu = new Menu("FlexiRentSystem");
		MenuItem Save = new MenuItem("Save");
		Save.setOnAction(new SaveHandler());
		MenuItem Exit = new MenuItem("New DB(test)");
		Exit.setOnAction(e -> {
			controller.DatabaseController.deleteTable();
			controller.DatabaseController.create1();
			controller.DatabaseController.create2();
		});
		systemMenu.getItems().addAll(Save, Exit);

		Menu fileMenu = new Menu("File");
		MenuItem importMenu = new MenuItem("Import...");
		importMenu.setOnAction(e -> {
			try {
				controller.FileController.importFile();

			} catch (Exception e1) {
				System.out.println("Wrong input data");
				e1.printStackTrace();
			}
		});
		MenuItem exportMenu = new MenuItem("Export...");
		exportMenu.setOnAction(e -> {
			try {
				controller.FileController.exportFile();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		fileMenu.getItems().addAll(importMenu, exportMenu);

		Menu propertyMenu = new Menu("Property");

		MenuItem viewMenu = new MenuItem("List");
		viewMenu.setOnAction(e -> {
			ChangeToPropertyListInterface();
			System.out.println("change to property list interface");
		});
		// Add menu
		MenuItem addMenu = new MenuItem("Add");
		addMenu.setOnAction(e -> {
			new NewWindowForAddPropertyInterface();
			System.out.println("Showing the Add property interface");
		});

		propertyMenu.getItems().addAll(viewMenu, addMenu);
		menubar.prefWidthProperty().bind(Startup.content.widthProperty().add(40));
		menubar.getMenus().addAll(systemMenu, fileMenu, propertyMenu);
		hMenu.getChildren().addAll(menubar);
		topBox.getChildren().add(hMenu);
		AddFilterToContent(topBox);

		return topBox;
	}
	public static HBox addLabelandTextField(String label) {
		//return a HBOX of one single property's basic information back to the add property interface.
		HBox hbox = new HBox();
		Label lb = new Label(label);
		lb.setMinWidth(300);
		lb.setPadding(new Insets(5, 5, 5, 5));
		TextField tf = new TextField();
		hbox.getChildren().addAll(lb, tf);
		return hbox;
	}

}