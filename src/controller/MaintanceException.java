package controller;

public class MaintanceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaintanceException() {
		new view.NewWindowForAlert("Past date is unacceptable");
	}

}
