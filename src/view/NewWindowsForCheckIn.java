package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NewWindowsForCheckIn {
	public NewWindowsForCheckIn(model.Property p){
		Stage stage = new Stage();
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(20,20,20,20));
		Button button2 = new Button("Confirm");
		HBox ID = view.HomePage.addLabelandTextField("Customer ID");
		HBox from = view.HomePage.addLabelandTextField("Check-in date (dd/mm/yyyy)");
		HBox days = view.HomePage.addLabelandTextField("Rent for how many days ");
		gp.add(ID, 0, 0);
		gp.add(from, 0, 1);
		gp.add(days, 0, 2);
		gp.add(button2, 0, 3);
		button2.setOnAction(f -> {
			String customerID = ((TextField) ID.getChildren().get(1)).getText();
			String rentdate = ((TextField) from.getChildren().get(1)).getText();
			String Estmatedays = ((TextField) days.getChildren().get(1)).getText();
			try {
				String[] dateSplit = new String[3];
				dateSplit = rentdate.split("/");
				model.DateTime dt = new model.DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
						Integer.parseInt(dateSplit[2]));
				int dayss = Integer.parseInt(Estmatedays);
				if (dt.diffDays(dt, new model.DateTime(-1)) < 0)
					throw new Exception();
				
				try {
					p.rent(customerID, dt, dayss);
					view.NewWindowsForPropertyDetails.proertyDetailStage.close();
					new view.NewWindowsForPropertyDetails(p);
					stage.close();
					new view.NewWindowForAlert("Booked Successfully!");
				} catch (Exception b) {

				}
			}

			catch (Exception a) {
				new view.NewWindowForAlert("Past or wrong format date is unacceptable");
			}

		});
		stage.show();
		stage.setScene(new Scene(gp,500,200));
		stage.centerOnScreen();

	}
	
	
}
