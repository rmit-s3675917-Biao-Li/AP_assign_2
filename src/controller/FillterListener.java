package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Property;

public class FillterListener implements ChangeListener<Object> {

	@Override
	public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
		if (oldValue != newValue) {
			int[] mark = new int[controller.DataStorage.propertyList.size()];
			int bednum;
			if (view.ChangeInterface.BedNum.getValue() != "All")
				bednum = Integer.parseInt((String) view.ChangeInterface.BedNum.getValue());
			else
				bednum = -1;
			String Type = (String) view.ChangeInterface.AorP.getValue();
			String Status = (String) view.ChangeInterface.Statuscombo.getValue();
			
			for (int i = 0; i < mark.length; i++) {
				mark[i] = 0;
			}

			for (int i = 0; i < controller.DataStorage.propertyList.size(); i++) {
				if ((controller.DataStorage.propertyList.get(i).getType().equals(Type) || Type.equals("All"))
						&& (controller.DataStorage.propertyList.get(i).getStatus().equals(Status)
								|| Status.equals("All"))
						&& ((controller.DataStorage.propertyList.get(i).getBednum() == bednum)
								|| bednum == -1))
					mark[i] = 1;

			}

			view.ChangeInterface.Content_center.getChildren().clear();

			int i = 0;
			while (i < controller.DataStorage.propertyList.size()) {
				if (mark[i] == 1) {
					Property p = controller.DataStorage.propertyList.get(i);
					view.ChangeInterface.addOnePropertytoContent(p);
				}

				i++;
			}
		}
	}
}