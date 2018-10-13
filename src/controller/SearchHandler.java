package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class SearchHandler implements EventHandler<KeyEvent> {
	TextField TF;

	public SearchHandler(TextField TF) {
		this.TF = TF;
	}

	@Override
	public void handle(KeyEvent event) {
		TF.textProperty().addListener((v, oldValue, newValue) -> { 
			System.out.println(oldValue);
			if (oldValue == null || !newValue.equals(oldValue)) {
				view.ChangeInterface.Content_center.getChildren().clear();
				for (int i = 0; i < DataStorage.propertyList.size(); i++) {
					if (DataStorage.propertyList.get(i).getId().toLowerCase().contains(newValue)) {
						view.ChangeInterface.addOnePropertytoContent(DataStorage.propertyList.get(i));
					}
				}
			}
		});
	}

}
