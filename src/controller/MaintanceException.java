package controller;

public class MaintanceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaintanceException() {
		view.ChangeInterface.NewWindowForAlert("Past date is unacceptable");
		System.out.println("Past date is unacceptable");
	}

}
