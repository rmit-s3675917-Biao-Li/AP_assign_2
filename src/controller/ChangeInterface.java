package controller;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBo;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ChangeInterface {
	public void ChangeToPropertyListInterface(VBox content) {
		content.getChildren().clear();
		Addfilter(content);
		AddPropertyList(content);
	}

	private void AddPropertyList(VBox content) {
		
		
	}

	private void Addfilter(VBox content) {
		// Create a type filter
		GridPane filter = new GridPane();
		filter.setGridLinesVisible(true);

		// Property type choiceBox
		Label TypeText = new Label("Property Type");
		TypeText.setAlignment(Pos.CENTER);
		TypeText.setMinWidth(150);
		ComboBox AorP = new ComboBox();
		AorP.getItems().addAll("All", "Apartment", "Premium Suite");
		AorP.setValue("All");
		AorP.setMinWidth(150);

		// Bedroom number choiceBox
		Label BedroomNum = new Label("Bedroom");
		BedroomNum.setAlignment(Pos.CENTER);
		BedroomNum.setMinWidth(150);
		ComboBox BedNum = new ComboBox();
		BedNum.getItems().addAll("All", "1", "2", "3");
		BedNum.setValue("All");
		BedNum.setMinWidth(150);

		// Add all elements into the filter
		filter.add(TypeText, 0, 0);
		filter.add(AorP, 0, 1);
		filter.add(BedroomNum, 1, 0);
		filter.add(BedNum, 1, 1);
		filter.setHgap(5);
		filter.setVgap(5);

		// add the filter to the interface
		content.getChildren().add(filter);

	}
}
