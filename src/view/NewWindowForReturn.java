package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NewWindowForReturn {
	public NewWindowForReturn(model.Property p) {
		Stage stage = new Stage();
		GridPane gp = new GridPane();
		Button button2 = new Button("Confirm");
		HBox from = view.HomePage.addLabelandTextField("Check-out date (dd/mm/yyyy)");
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
					view.NewWindowsForPropertyDetails.proertyDetailStage.close();
					new view.NewWindowsForPropertyDetails(p);
					stage.close();
					new view.NewWindowForAlert("Return Successfully!");
				} catch (Exception b) {

				}
			}

			catch (Exception a) {
				new view.NewWindowForAlert("Past or wrong format date is unacceptable");
			}

		});
		stage.show();
		stage.setScene(new Scene(gp, 500, 200));
		stage.centerOnScreen();

	}
}
