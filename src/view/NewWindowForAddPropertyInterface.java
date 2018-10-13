package view;

import java.util.ArrayList;

import controller.AddPropertyHandler;
import controller.UploadImageHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewWindowForAddPropertyInterface {
	private static Label t = new Label(".....");
	public static Stage Addstage = new Stage();

	public NewWindowForAddPropertyInterface() {
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
		HboxArray.add(HomePage.addLabelandTextField("Property ID"));

		// Type
		HBox TypeHbox = new HBox();
		Label TypeLabel = new Label("Property Type");
		TypeLabel.setMinWidth(300);
		TypeLabel.setPadding(new Insets(5, 5, 5, 5));
		ComboBox<String> AorP = new ComboBox<String>();
		AorP.setMinWidth(168);
		AorP.getItems().addAll("Apartment", "Premium Suite");

		TypeHbox.getChildren().addAll(TypeLabel, AorP);

		HboxArray.add(HomePage.addLabelandTextField("Street Number"));
		HboxArray.add(HomePage.addLabelandTextField("Street Name"));
		HboxArray.add(HomePage.addLabelandTextField("Suburb"));
		HboxArray.add(HomePage.addLabelandTextField("Bedroom Quantity(1-3)"));
		HboxArray.add(HomePage.addLabelandTextField("Last Maintenance Date(dd/mm/yyyy)"));
		HboxArray.add(HomePage.addLabelandTextField("Description"));
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

	public Label getT() {
		return t;
	}

	public static void setT(Label t1) {
		t = t1;
	}
	
}
