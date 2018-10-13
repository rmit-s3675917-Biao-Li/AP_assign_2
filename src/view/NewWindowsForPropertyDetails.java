package view;

import controller.RentHandler;
import controller.ReturnHandler;
import controller.SuiteCompleteMaintanceHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Property;

public class NewWindowsForPropertyDetails {
	public static Stage proertyDetailStage = new Stage();
	public static GridPane TextDetailsGP = new GridPane();
	
	public NewWindowsForPropertyDetails(Property p) {
		TextDetailsGP.getChildren().clear();
		TextDetailsGP.setVgap(10);
		StackPane sp = new StackPane();
		proertyDetailStage.setScene(new Scene(sp, 800, 800));
		sp.setPrefSize(600, 600);
		proertyDetailStage.setTitle("More Details");
		VBox addPropertyVbox = new VBox();
		addPropertyVbox.setMaxWidth(800);
		addPropertyVbox.setPadding(new Insets(30, 10, 10, 20));
		proertyDetailStage.show();
		addPropertyVbox.setSpacing(50);
		sp.getChildren().add(addPropertyVbox);

		ImageView iv = new ImageView(p.getImage());
		iv.setFitHeight(300);
		iv.setFitWidth(300);
		HBox hbox1 = new HBox();
		hbox1.setSpacing(30);
		Button button;
		TextDetailsGP.add(AddTwoText("Property ID:", p.getId()), 0, 0);
		TextDetailsGP.add(AddTwoText("Type:", p.getType()), 0, 1);
		TextDetailsGP.add(AddTwoText("Address:", p.getStnum() + " " + p.getStname() + " " + p.getSuburb()), 0, 2);
		TextDetailsGP.add(AddTwoText("Last Maintaining Date:", p.getLastMaintenance().getFormattedDate()), 0, 3);
		TextDetailsGP.add(AddTwoText(("Description: \n") + p.getDescription22(), " "), 0, 5);

		switch (p.getStatus()) {
		case "Available":
			button = new Button("Reserve");
			Button button2 = new Button("Maintain");
			TextDetailsGP.add(AddTwoText("Status:", "Available for Rent"), 0, 4);
			TextDetailsGP.add(button, 0, 6);
			TextDetailsGP.add(button2, 0, 7);
			button.setOnAction(new RentHandler(p));
			button2.setOnAction(e -> {
				proertyDetailStage.close();
				p.performMaintenance();
				new NewWindowsForPropertyDetails(p);
			});
			break;
		case "Rented":
			button = new Button("Return");
			TextDetailsGP.add(AddTwoText("Status:", "Being Rented"), 0, 4);
			TextDetailsGP.add(button, 0, 6);
			button.setOnAction(new ReturnHandler(p));
			break;
		case "Maintaining":
			button = new Button("Stop Maintenance");
			TextDetailsGP.add(AddTwoText("Status:", "maintaining"), 0, 4);
			TextDetailsGP.add(button, 0, 6);
			if (p instanceof model.Apartment) {
				button.setOnAction(e -> {
					((model.Apartment) p).completeMaintenance();
					proertyDetailStage.close();
					new NewWindowsForPropertyDetails(p);
					new NewWindowForAlert("Maintainance Completed");
				});
				break;
			} else {
				button.setOnAction(new SuiteCompleteMaintanceHandler(p));
			}
		}

		hbox1.getChildren().addAll(iv, TextDetailsGP);
		ScrollPane sp2 = new ScrollPane();
		sp2.setContent(AddTwoText(p.getDetails(), ""));

		addPropertyVbox.getChildren().addAll(hbox1, sp2);

	}
	
	private HBox AddTwoText(String s1, String s2) {
		//return a HBOX of one single property's basic information back to the home page.
		HBox hbox = new HBox();
		Label l1 = new Label(s1);
		l1.setMinWidth(200);
		Label l2 = new Label(s2);
		l1.setLayoutX(10);
		hbox.getChildren().addAll(l1, l2);
		return hbox;
	}
}
