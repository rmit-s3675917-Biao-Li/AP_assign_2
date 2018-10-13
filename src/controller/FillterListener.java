package controller;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Property;

public class FillterListener implements ChangeListener<Object> {

	@Override
	public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
		if (oldValue != newValue) {
			ArrayList<Property> PL = DataStorage.getPropertyList();
			int[] mark = new int[PL.size()];
			int bednum;
			if (view.ChangeInterface.BedNum.getValue() != "All")
				bednum = Integer.parseInt((String) view.ChangeInterface.BedNum.getValue());
			else
				bednum = -1;
			String Type = (String) view.ChangeInterface.AorP.getValue();
			String Status = (String) view.ChangeInterface.Statuscombo.getValue();
			String Suburb = (String) view.ChangeInterface.Suburbcombo.getValue();
			for (int i = 0; i < mark.length; i++) {
				mark[i] = 0;
			}

			for (int i = 0; i < PL.size(); i++) {
				if ((PL.get(i).getType().equals(Type) || Type.equals("All"))
						&& (PL.get(i).getStatus().equals(Status) || Status.equals("All"))
						&& (PL.get(i).getSuburb().equals(Suburb) || Suburb.equals("All"))
						&& ((PL.get(i).getBednum() == bednum) || bednum == -1))
					mark[i] = 1;

			}

			StartUP.Startup.content.getChildren().clear();

			int i = 0;
			while (i < PL.size()) {
				if (mark[i] == 1) {
					Property p = PL.get(i);
					view.ChangeInterface.addOnePropertytoContent(p);
				}

				i++;
			}
		}
	}
}