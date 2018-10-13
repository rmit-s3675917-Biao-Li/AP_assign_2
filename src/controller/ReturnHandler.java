package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Property;

public class ReturnHandler implements EventHandler<ActionEvent> {
	Property p;

	public ReturnHandler(Property p) {
		this.p = p;
	}

	@Override
	public void handle(ActionEvent event) {
		new view.NewWindowForReturn(p);
	}

}
