package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Property;


public class RentHandler implements EventHandler<ActionEvent> {
	Property p;

	public RentHandler(Property p) {
		this.p = p;
	}

	@Override
	public void handle(ActionEvent event) {
		new view.NewWindowsForCheckIn(p);
	}
}
