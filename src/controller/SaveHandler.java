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
		for (int i=0; i<DataStorage.propertyList.size(); i++) {
			DatabaseController.insert(DataStorage.propertyList.get(i));
			System.out.println(i);
		}
		
		view.ChangeInterface.NewWindowForAlert("Data Saved");
	}

}
