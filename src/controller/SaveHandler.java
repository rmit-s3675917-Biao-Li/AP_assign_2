package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SaveHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		DatabaseController.deleteTable();
		DatabaseController.create1();
		DatabaseController.create2();
		for (int i=0; i<DataStorage.getPropertyList().size(); i++) {
			DatabaseController.insert(DataStorage.getPropertyList().get(i));
			System.out.println(i);
		}
		
		new view.NewWindowForAlert("Data Saved");
	}

}
