package controller;


public class RentException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RentException(int i) {
		if (i==1) {
			new view.NewWindowForAlert("The apartment should not be rented for more than 28 days!");
		}
		
		if (i==2) {
			new view.NewWindowForAlert("Today is Fri/Sat, the number of rent day should be a minimum of 3 days!");
		}
		
		if (i==3) {
			new view.NewWindowForAlert("Today is Sun/Mon/Tue/Wed/Thu, the number of rent day should be a minimum of 3 days!");
		}
		
		if (i==4) {
			new view.NewWindowForAlert("This Premium Suite needs to be maintained ");
		}
		
		if	(i==5) {
			new view.NewWindowForAlert("Premium Suite should be rented for a minimum of 1 day!");
		}
		
	}
}
