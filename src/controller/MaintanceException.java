package controller;

public class MaintanceException extends Exception {
	public MaintanceException() {
		view.ChangeInterface.NewWindowForAlert("Past date is unacceptable");
		System.out.println("Past date is unacceptable");
	}

}
