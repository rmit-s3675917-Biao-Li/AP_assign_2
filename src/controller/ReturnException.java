package controller;


public class ReturnException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReturnException(int i) {
		if (i==1) {
			new view.NewWindowForAlert("The return date is not after the rent date");

		}
		
		
	}
}
