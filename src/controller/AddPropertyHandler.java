package controller;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Apartment;
import model.Suite;

public class AddPropertyHandler implements EventHandler<ActionEvent> {
	private ArrayList<HBox> hboxArray = new ArrayList<HBox>();
	private ComboBox<String> aorP;

	public AddPropertyHandler(ArrayList<HBox> hboxArray, ComboBox<String> aorP) {
		this.hboxArray = hboxArray;
		this.aorP = aorP;
	}

	@Override
	public void handle(ActionEvent event) {
		String s1 = ((TextField) hboxArray.get(0).getChildren().get(1)).getText();
		String s2 = ((TextField) hboxArray.get(1).getChildren().get(1)).getText();
		String s3 = ((TextField) hboxArray.get(2).getChildren().get(1)).getText();
		String s4 = ((TextField) hboxArray.get(3).getChildren().get(1)).getText();
		String s5 = ((TextField) hboxArray.get(4).getChildren().get(1)).getText();
		String s6 = ((TextField) hboxArray.get(5).getChildren().get(1)).getText();
		String s7 = ((TextField) hboxArray.get(6).getChildren().get(1)).getText();

		try {
			addProperty(s1, aorP, s2, s3, s4, s5, s6, s7, DataStorage.getSelectImage());
			view.ChangeInterface.NewWindowForAlert("Property Successfully Added");
			view.ChangeInterface.Addstage.close();
			File f = null;
			DataStorage.setSelectImage(f);
			view.ChangeInterface.t.setText(".....");
			view.ChangeInterface.ChangeToPropertyListInterface();
		} catch (AddPropertyException e) {
			System.out.println("Wrong Input");
		}
	}

	public void addProperty(String id, ComboBox<String> aorP, String stnum, String stname, String suburb, String bednum,
			String lastMaintenanceDate, String desription, File selectImage) throws AddPropertyException {

		System.out.println(id);
		if (aorP.getValue() == null)
			throw new AddPropertyException(2);
		if (aorP.getValue().toString().equals("Apartment") && !id.startsWith("A_"))
			id = "A_" + id;
		else if (aorP.getValue().toString().equals("Premium Suite") && !id.startsWith("S_"))
			id = "S_" + id;
		System.out.println(id);

		if (repeatId(id))
			throw new AddPropertyException(1);
		

		
		if (id.equals("") || aorP.getValue().toString().isEmpty() || stnum.equals("") || stname.equals("")
				|| suburb.equals("") || bednum.equals(""))
			throw new AddPropertyException(2);
		else {
			if (aorP.getValue().toString().equals("Apartment"))
				try {
					if (Integer.parseInt(bednum)<1 && Integer.parseInt(bednum)>3) throw new AddPropertyException(5);
					DataStorage.propertyList.add(new Apartment(id, aorP.getValue().toString(), stnum, stname, suburb,
							Integer.parseInt(bednum), "Available", desription, selectImage));
				} catch (Exception e) {
					throw new AddPropertyException(2);
				}
			else {
				try {
					model.DateTime maintenanceDate = new model.DateTime(lastMaintenanceDate);
					System.out.println(maintenanceDate.getFormattedDate());
					System.out.println(maintenanceDate.diffDays(new model.DateTime(-1), maintenanceDate));
					if (maintenanceDate.diffDays(new model.DateTime(-1), maintenanceDate) < 0)
						throw new AddPropertyException(3);

					model.Suite newSuite = new Suite(id, aorP.getValue().toString(), stnum, stname, suburb, 3,
							"Available", desription, selectImage);
					newSuite.setLastMaintenance(maintenanceDate);
					DataStorage.propertyList.add(newSuite);

				} catch (Exception e) {
					throw new AddPropertyException(4);
				}

			}

		}

	}

	private static boolean repeatId(String id) {
		int i = 0;
		while (i < DataStorage.propertyList.size()) {
			if (DataStorage.propertyList.get(i).getId().equals(id))
				return true;
			i++;
		}
		return false;
	}

}
