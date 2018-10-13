package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Property;

public class ReturnHandler implements EventHandler<ActionEvent> {
	Property p;

	public ReturnHandler(Property p) {
		this.p = p;
	}

	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		GridPane gp = new GridPane();
		Button button2 = new Button("Confirm");
		HBox from = view.ChangeInterface.addLabelandTextField("Check-out date (dd/mm/yyyy)");
		gp.add(from, 0, 1);
		gp.add(button2, 0, 3);
		button2.setOnAction(f -> {
			String rentdate = ((TextField) from.getChildren().get(1)).getText();
			try {
				String[] dateSplit = new String[3];
				dateSplit = rentdate.split("/");
				model.DateTime dt = new model.DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
						Integer.parseInt(dateSplit[2]));
				if (dt.diffDays(dt, new model.DateTime()) < 0)
					throw new Exception();
				
				try {
					p.Return(dt);
					view.ChangeInterface.stage.close();
					view.ChangeInterface.NewWindowsForPropertyDetails(p);
					stage.close();
					view.ChangeInterface.NewWindowForAlert("Return Successfully!");
				} catch (Exception b) {

				}
			}

			catch (Exception a) {
				view.ChangeInterface.NewWindowForAlert("Past or wrong format date is unacceptable");
			}

		});
		stage.show();
		stage.setScene(new Scene(gp, 400, 200));

	}

}
