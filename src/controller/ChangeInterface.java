package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeInterface {
	public void ChangeToPropertyListInterface(VBox content) {
		content.getChildren().clear();
		Addfilter(content);
		AddPropertyList(content, "All", "All", "All");
	}

	private void AddPropertyList(VBox content, String TypeChoosing, String StatusChoosing, String BedroomNumChoosing) {
		VBox ProperyListVbox = new VBox();
	}

	private void Addfilter(VBox content) {
		// Create a type filter
		GridPane filter = new GridPane();
		// filter.setGridLinesVisible(true);

		// Property type choiceBox
		Text TypeText = new Text(" Property Type: ");
		TypeText.setTextAlignment(TextAlignment.CENTER);
		ComboBox AorP = new ComboBox();
		AorP.getItems().addAll("All", "Apartment", "Premium Suite");
		AorP.setValue("All");

		// Bedroom number choiceBox
		Text BedroomNumText = new Text(" How Many Bedroom: ");
		BedroomNumText.setTextAlignment(TextAlignment.CENTER);
		ComboBox BedNum = new ComboBox();
		BedNum.getItems().addAll("All", "1", "2", "3");
		BedNum.setValue("All");

		// Status choiceBox
		Text StatusText = new Text(" Status: ");
		StatusText.setTextAlignment(TextAlignment.CENTER);
		ComboBox Statuscombo = new ComboBox();
		Statuscombo.getItems().addAll("All", "Available", "Rented", "Maintaining", "Maintenance needed");
		Statuscombo.setValue("All");

		// Add all elements into the filter
		filter.add(TypeText, 0, 0);
		filter.add(AorP, 1, 0);
		filter.add(BedroomNumText, 4, 0);
		filter.add(BedNum, 5, 0);
		filter.add(StatusText, 2, 0);
		filter.add(Statuscombo, 3, 0);
		filter.setHgap(5);
		filter.setVgap(5);
		filter.setPadding(new Insets(5, 5, 5, 5));
		// add the filter to the interface
		content.getChildren().add(filter);

	}

	public void ChangeToAddPropertyInterface(VBox content) {
		content.getChildren().clear();
		// Type
		HBox TypeHbox = new HBox();
		Label TypeLabel = new Label("Property Type:");
		TypeLabel.setPadding(new Insets(5,5,5,5));
		ComboBox AorP = new ComboBox();
		AorP.getItems().addAll("Apartment", "Premium Suite");
		TypeHbox.getChildren().addAll(TypeLabel, AorP);

		// Street number
		HBox StreetNumHbox = new HBox();
		Label StreetNumLabel = new Label("Street Number:");
		StreetNumLabel.setPadding(new Insets(5,5,5,5));
		TextField StreetNumTF = new TextField();
		StreetNumHbox.getChildren().addAll(StreetNumLabel, StreetNumTF);

		// Street
		HBox StreetHbox = new HBox();
		Label StreetLabel = new Label("Street:");
		StreetLabel.setPadding(new Insets(5,5,5,5));
		TextField StreetTF = new TextField();
		StreetHbox.getChildren().addAll(StreetLabel, StreetTF);

		// property id
		HBox PropertyIDHbox = new HBox();
		Label PropertyIDLabel = new Label("Property ID:");
		PropertyIDLabel.setPadding(new Insets(5,5,5,5));
		TextField PropertyIDTF = new TextField();
		PropertyIDHbox.getChildren().addAll(PropertyIDLabel, PropertyIDTF);

		// Suburb
		HBox SuburbHbox = new HBox();
		Label SuburbLabel = new Label("Suburb:");
		SuburbLabel.setPadding(new Insets(5,5,5,5));
		TextField SuburbTF = new TextField();
		SuburbHbox.getChildren().addAll(SuburbLabel, SuburbTF);

		// Bedroom Quantity
		HBox BedroomNumHbox = new HBox();
		Label BedroomNumLabel = new Label("Bedroom Quantity:");
		BedroomNumLabel.setPadding(new Insets(5,5,5,5));
		TextField BedroomNumTF = new TextField();
		BedroomNumHbox.getChildren().addAll(BedroomNumLabel, BedroomNumTF);
		
		// Image Upload
		HBox ImageUploadHbox = new HBox();
		Label ImageUploadLabel = new Label("Image:");
		ImageUploadLabel.setPadding(new Insets(5,5,5,5));
		Button UploadButton = new Button("Upload");
		UploadButton.setOnAction(e ->{
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Upload Image");
			FileChooser imageFC = new FileChooser();
			imageFC.showOpenDialog(stage);

		});
		
		ImageUploadHbox.getChildren().addAll(ImageUploadLabel, UploadButton);
		
		//Add Button
		Button AddButton = new Button("Add");
		
		content.getChildren().addAll(PropertyIDHbox,TypeHbox,StreetNumHbox,StreetHbox,SuburbHbox,BedroomNumHbox,ImageUploadHbox,AddButton);
		content.setSpacing(5);
	}

}