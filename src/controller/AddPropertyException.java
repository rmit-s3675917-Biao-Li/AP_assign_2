package controller;

public class AddPropertyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddPropertyException(int i) {
		if (i==1) {
			view.ChangeInterface.NewWindowForAlert("Repeated ID");			

		}
		
		if (i==2) {
			view.ChangeInterface.NewWindowForAlert("Please input valid value");			

		}
		
		if (i==3) {
			view.ChangeInterface.NewWindowForAlert("Please input a past date for Maintenance Date");			

		}
		
		if (i==4) {
			view.ChangeInterface.NewWindowForAlert("Please input a valid date format");

		}
		
		if (i==5) {
			view.ChangeInterface.NewWindowForAlert("Please input a valid bedroom number between 1 to 3");

		}
	}
}
