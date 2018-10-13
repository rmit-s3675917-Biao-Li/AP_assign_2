package controller;


public class RentException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RentException(int i) {
		if (i==1) {
			view.ChangeInterface.NewWindowForAlert("The apartment should not be rented for more than 28 days!");
			System.out.println("The apartment should not be rented for more than 28 days!");
		}
		
		if (i==2) {
			view.ChangeInterface.NewWindowForAlert("Today is Fri/Sat, the number of rent day should be a minimum of 3 days!");
			System.out.println("Today is Fri/Sat, the number of rent day should be a minimum of 3 days!");
		}
		
		if (i==3) {
			view.ChangeInterface.NewWindowForAlert("Today is Sun/Mon/Tue/Wed/Thu, the number of rent day should be a minimum of 3 days!");
			System.out.println("Today is Sun/Mon/Tue/Wed/Thu, the number of rent day should be a minimum of 3 days!");
		}
		
		if (i==4) {
			view.ChangeInterface.NewWindowForAlert("This Premium Suite needs to be maintained ");
			System.out.println("This Premium Suite needs to be maintained ");
		}
		
		if	(i==5) {
			view.ChangeInterface.NewWindowForAlert("Premium Suite should be rented for a minimum of 1 day!");
			System.out.println("Premium Suite should be rented for a minimum of 1 day!");

		}
		
	}
}
