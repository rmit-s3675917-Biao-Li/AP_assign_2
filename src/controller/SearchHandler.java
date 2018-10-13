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
				StartUP.Startup.content.getChildren().clear();
				for (int i = 0; i < DataStorage.getPropertyList().size(); i++) {
					if (DataStorage.getPropertyList().get(i).getId().toLowerCase().contains(newValue)) {
						view.HomePage.addOnePropertytoContent(DataStorage.getPropertyList().get(i));
					}
				}
			}
		});
	}

}
