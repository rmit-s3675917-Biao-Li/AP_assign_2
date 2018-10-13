package controller;


public class ReturnException extends Exception{
	public ReturnException(int i) {
		if (i==1) {
			view.ChangeInterface.NewWindowForAlert("The return date is not after the rent date");
			System.out.println("The return date is not after the rent date");

		}
		
		
	}
}
