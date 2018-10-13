package view;

import java.util.ArrayList;
import StartUP.Startup;
import controller.AddPropertyHandler;
import controller.DataStorage;
import controller.RentHandler;
import controller.ReturnHandler;
import controller.SaveHandler;
import controller.SuiteCompleteMaintanceHandler;
import controller.UploadImageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Property;

public class ChangeInterface {
	public static ComboBox<String> AorP = new ComboBox<String>();
	public static ComboBox<String> BedNum = new ComboBox<String>();
	public static ComboBox<String> Statuscombo = new ComboBox<String>();
	public static ComboBox<String> Suburbcombo;
	
	public static Stage Addstage = new Stage();
	
	public static Stage proertyDetailStage = new Stage();
	public static GridPane TextDetailsGP = new GridPane();
	public static Label t = new Label(".....");

	public static void ChangeToPropertyListInterface() {
		Startup.content.getChildren().clear();
		AorP.setValue("All");
		BedNum.setValue("All");
		Statuscombo.setValue("All");
		Suburbcombo.setValue("All");
		new controller.FillterListener().changed(null, 0, 1);;

	}

	public static void NewWindowsForPropertyDetails(Property p) {
		TextDetailsGP.getChildren().clear();
		TextDetailsGP.setVgap(10);
		StackPane sp = new StackPane();
		proertyDetailStage.setScene(new Scene(sp, 800, 800));
		sp.setPrefSize(600, 600);
		proertyDetailStage.setTitle("More Details");
		VBox addPropertyVbox = new VBox();
		addPropertyVbox.setMaxWidth(800);
		addPropertyVbox.setPadding(new Insets(30, 10, 10, 20));
		proertyDetailStage.show();
		addPropertyVbox.setSpacing(50);
		sp.getChildren().add(addPropertyVbox);

		ImageView iv = new ImageView(p.getImage());
		iv.setFitHeight(300);
		iv.setFitWidth(300);
		HBox hbox1 = new HBox();
		hbox1.setSpacing(30);
		Button button;
		TextDetailsGP.add(AddTwoText("Property ID:", p.getId()), 0, 0);
		TextDetailsGP.add(AddTwoText("Type:", p.getType()), 0, 1);
		TextDetailsGP.add(AddTwoText("Address:", p.getStnum() + " " + p.getStname() + " " + p.getSuburb()), 0, 2);
		TextDetailsGP.add(AddTwoText("Last Maintaining Date:", p.getLastMaintenance().getFormattedDate()), 0, 3);
		TextDetailsGP.add(AddTwoText(("Description: \n") + p.getDescription22(), " "), 0, 5);

		switch (p.getStatus()) {
		case "Available":
			button = new Button("Reserve");
			Button button2 = new Button("Maintain");
			TextDetailsGP.add(AddTwoText("Status:", "Available for Rent"), 0, 4);
			TextDetailsGP.add(button, 0, 6);
			TextDetailsGP.add(button2, 0, 7);
			button.setOnAction(new RentHandler(p));
			button2.setOnAction(e -> {
				proertyDetailStage.close();
				p.performMaintenance();
				NewWindowsForPropertyDetails(p);
			});
			break;
		case "Rented":
			button = new Button("Return");
			TextDetailsGP.add(AddTwoText("Status:", "Being Rented"), 0, 4);
			TextDetailsGP.add(button, 0, 6);
			button.setOnAction(new ReturnHandler(p));
			break;
		case "Maintaining":
			button = new Button("Stop Maintenance");
			TextDetailsGP.add(AddTwoText("Status:", "maintaining"), 0, 4);
			TextDetailsGP.add(button, 0, 6);
			if (p instanceof model.Apartment) {
				button.setOnAction(e -> {
					((model.Apartment) p).completeMaintenance();
					proertyDetailStage.close();
					NewWindowsForPropertyDetails(p);
					NewWindowForAlert("Maintainance Completed");
				});
				break;
			} else {
				button.setOnAction(new SuiteCompleteMaintanceHandler(p));
			}
		}

		hbox1.getChildren().addAll(iv, TextDetailsGP);
		ScrollPane sp2 = new ScrollPane();
		sp2.setContent(AddTwoText(p.getDetails(), ""));

		addPropertyVbox.getChildren().addAll(hbox1, sp2);

	}

	public static void NewWindowForAddPropertyInterface() {
		Addstage.setMinWidth(600);
		Addstage.setMinHeight(600);
		Addstage.setMaxHeight(600);
		Addstage.setMaxWidth(600);
		Addstage.setTitle("Add Property");
		VBox content = new VBox();
		content.setPadding(new Insets(30, 10, 10, 20));
		content.setMinWidth(600);
		content.setAlignment(Pos.TOP_CENTER);
		Addstage.setScene(new Scene(content));
		Addstage.show();

		ArrayList<HBox> HboxArray = new ArrayList<HBox>();
		// property id
		HboxArray.add(addLabelandTextField("Property ID"));

		// Type
		HBox TypeHbox = new HBox();
		Label TypeLabel = new Label("Property Type");
		TypeLabel.setMinWidth(300);
		TypeLabel.setPadding(new Insets(5, 5, 5, 5));
		ComboBox<String> AorP = new ComboBox<String>();
		AorP.setMinWidth(168);
		AorP.getItems().addAll("Apartment", "Premium Suite");

		TypeHbox.getChildren().addAll(TypeLabel, AorP);

		HboxArray.add(addLabelandTextField("Street Number"));
		HboxArray.add(addLabelandTextField("Street Name"));
		HboxArray.add(addLabelandTextField("Suburb"));
		HboxArray.add(addLabelandTextField("Bedroom Quantity(1-3)"));
		HboxArray.add(addLabelandTextField("Last Maintenance Date(dd/mm/yyyy)"));
		HboxArray.add(addLabelandTextField("Description"));
		// Image Upload
		HBox ImageUploadHbox = new HBox();
		Label ImageUploadLabel = new Label("Image");
		ImageUploadLabel.setMinWidth(300);
		ImageUploadLabel.setPadding(new Insets(5, 5, 5, 5));
		Button UploadButton = new Button("Upload");

		UploadButton.setOnAction(new UploadImageHandler());

		ImageUploadHbox.getChildren().addAll(ImageUploadLabel, UploadButton, t);

		// Image path
		HBox ImagePathHbox = new HBox();
		t.setPadding(new Insets(5, 5, 5, 5));
		ImagePathHbox.getChildren().add(t);

		// Add Button
		HBox AddButtonbox = new HBox();
		AddButtonbox.setPadding(new Insets(40, 0, 0, 0));
		Button AddButton = new Button("Add");
		AddButton.setMinSize(100, 50);
		AddButtonbox.getChildren().add(AddButton);
		AddButtonbox.setAlignment(Pos.CENTER);
		AddButton.setOnAction(new AddPropertyHandler(HboxArray, AorP));

		for (int i = 0; i < HboxArray.size(); i++) {
			content.getChildren().add(HboxArray.get(i));
			if (i == 0)
				content.getChildren().add(TypeHbox);
			if (i == 6)
				content.getChildren().addAll(ImageUploadHbox, ImagePathHbox, AddButtonbox);
		}
		content.setSpacing(10);

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
			NewWindowsForPropertyDetails(p);
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

	private static HBox AddTwoText(String s1, String s2) {
		//return a HBOX of one single property's basic information back to the home page.
		HBox hbox = new HBox();
		Label l1 = new Label(s1);
		l1.setMinWidth(200);
		Label l2 = new Label(s2);
		l1.setLayoutX(10);
		hbox.getChildren().addAll(l1, l2);
		return hbox;
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

	public static void NewWindowForAlert(String string) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		Button b = new Button("Ok");
		BorderPane bp = new BorderPane();
		Label label = new Label(string);
		HBox h = new HBox();
		h.setMinWidth(500);
		h.getChildren().add(b);
		h.setAlignment(Pos.CENTER);
		bp.setCenter(label);
		bp.setBottom(h);
		stage.setScene(new Scene(bp, 500, 100));
		stage.show();

		b.setOnAction(e -> {
			stage.close();
		});
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
			NewWindowForAddPropertyInterface();
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

}