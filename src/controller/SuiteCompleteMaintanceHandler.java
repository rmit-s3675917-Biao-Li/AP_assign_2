package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Property;

public class SuiteCompleteMaintanceHandler implements EventHandler<ActionEvent> {
	Property p;

	public SuiteCompleteMaintanceHandler(Property p) {
		this.p = p;
	}

	@Override
	public void handle(ActionEvent event) {
		new view.NewWindowsSuiteCompleteMaintance(p);

	}


}
