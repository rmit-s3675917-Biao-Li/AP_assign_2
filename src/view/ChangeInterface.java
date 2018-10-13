package view;

import java.io.File;
import java.util.ArrayList;
import controller.AddPropertyHandler;
import controller.RentHandler;
import controller.ReturnHandler;
import controller.SaveHandler;
import controller.SuiteCompleteMaintanceHandler;
import controller.UploadImageHandler;
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
	public static Stage MaintenanceTimeStage = new Stage();
	public static Label t = new Label(".....");
	public static Stage stage = new Stage();
	public static Stage Addstage = new Stage();
	public static GridPane TextDetailsGP = new GridPane();
	public static VBox Content_center = new VBox();
	public static String maintenanceDate = null;

	public static void ChangeToPropertyListInterface() {
		UI_Startup.content.getChildren().clear();
		AddFilterToContent(UI_Startup.content);
		AddPropertyListToContent("All", "All", "All");
		UI_Startup.content.getChildren().add(Content_center);

	}

	private static void AddPropertyListToContent(String Type, String Status, String BedroomNum) {
		int[] mark = new int[controller.DataStorage.propertyList.size()];
		int bednum;
		if (BedroomNum != "All")
			bednum = Integer.parseInt(BedroomNum);
		else
			bednum = -1;

		for (int i = 0; i < mark.length; i++) {
			mark[i] = 0;
		}
//		for (int j : mark) {
//			j = 1;// 1 means this property matches the filter;
//		}
		for (int i = 0; i < controller.DataStorage.propertyList.size(); i++) {
			if ((controller.DataStorage.propertyList.get(i).getType().equals(Type) || Type.equals("All"))
					&& (controller.DataStorage.propertyList.get(i).getStatus().equals(Status) || Status.equals("All"))
					&& ((controller.DataStorage.propertyList.get(i).getBednum() == bednum) || BedroomNum.equals("All")))
				mark[i] = 1;

		}

		Content_center.getChildren().clear();

		int i = 0;
		while (i < controller.DataStorage.propertyList.size()) {
			if (mark[i] == 1) {
				Property p = controller.DataStorage.propertyList.get(i);
				addOnePropertytoContent(p);
			}

			i++;
		}

	}

	public static void NewWindowsForPropertyDetails(Property p) {
		TextDetailsGP.getChildren().clear();
		TextDetailsGP.setVgap(10);
		StackPane sp = new StackPane();
		stage.setScene(new Scene(sp, 800, 800));
		sp.setPrefSize(600, 600);
		stage.setTitle("More Details");
		VBox addPropertyVbox = new VBox();
		addPropertyVbox.setMaxWidth(800);
		addPropertyVbox.setPadding(new Insets(30, 10, 10, 20));
		stage.show();
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
				stage.close();
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
					stage.close();
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
		HBox ProperyViewHbox = new HBox();
		ProperyViewHbox.setSpacing(10);
		ProperyViewHbox.setPadding(new Insets(20, 5, 20, 5));
		ImageView iv = new ImageView(p.getImage());
		iv.setFitHeight(300);
		iv.setFitWidth(300);

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
		Content_center.getChildren().add(ProperyViewHbox);
	}

	private static void AddFilterToContent(VBox content) {
		// Create a type filter
		GridPane filter = new GridPane();
		// filter.setGridLinesVisible(true);

		// Property type choiceBox
		Text TypeText = new Text(" Property Type: ");
		TypeText.setTextAlignment(TextAlignment.CENTER);
		AorP.getItems().setAll("All", "Apartment", "Premium Suite");
		AorP.setValue("All");

		// Bedroom number choiceBox
		Text BedroomNumText = new Text(" How Many Bedroom: ");
		BedroomNumText.setTextAlignment(TextAlignment.CENTER);
		BedNum.getItems().setAll("All", "1", "2", "3");
		BedNum.setValue("All");

		// Status choiceBox
		Text StatusText = new Text(" Status: ");
		StatusText.setTextAlignment(TextAlignment.CENTER);
		Statuscombo.getItems().setAll("All", "Available", "Rented", "Maintaining");
		Statuscombo.setValue("All");

		BedNum.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		AorP.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());
		Statuscombo.getSelectionModel().selectedItemProperty().addListener(new controller.FillterListener());

		//
		Button confirmButton = new Button("Reset");
		confirmButton.setOnAction(e -> {
			ChangeToPropertyListInterface();
		});
		// Add all elements into the filter
		filter.add(TypeText, 0, 0);
		filter.add(AorP, 1, 0);
		filter.add(BedroomNumText, 4, 0);
		filter.add(BedNum, 5, 0);
		filter.add(StatusText, 2, 0);
		filter.add(Statuscombo, 3, 0);
		filter.add(confirmButton, 6, 0);
		filter.setHgap(5);
		filter.setVgap(5);
		filter.setPadding(new Insets(5, 5, 5, 5));
		// add the filter to the interface
		content.getChildren().add(filter);

	}

	private static HBox AddTwoText(String s1, String s2) {
		HBox hbox = new HBox();
		Label l1 = new Label(s1);
		l1.setMinWidth(200);
		Label l2 = new Label(s2);
		l1.setLayoutX(10);
		hbox.getChildren().addAll(l1, l2);
		return hbox;
	}

	public static HBox addLabelandTextField(String label) {
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

	public static HBox addMenu() {
		HBox hMenu = new HBox();
		// hMenu.setAlignment(Pos.CENTER);
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
		menubar.setMinSize(500, 20);
		menubar.getMenus().addAll(systemMenu, fileMenu, propertyMenu);
		Label search = new Label("Search");
		search.setAlignment(Pos.BOTTOM_CENTER);
		search.setMinWidth(50);
		search.setPadding(new Insets(5, 10, 5, 10));
		TextField TF = new TextField();
		TF.setOnKeyPressed(new controller.SearchHandler(TF));
		hMenu.getChildren().addAll(menubar, search, TF);
		return hMenu;
	}
	

}