package model;

import java.io.File;
import java.net.MalformedURLException;

public class Suite extends Property {

	public Suite(String id, String type, String stnum, String stname, String suburb, int bednum, String status,
			String Description, File image) throws MalformedURLException {
		super(id, type, stnum, stname, suburb, 3, status, Description, image);
	}

	public void rent(String customerId, DateTime rentdate, int numOfRentDay) throws controller.RentException {
		if (rentdate.diffDays(rentdate, getLastMaintenance()) + numOfRentDay > 10) {
			throw new controller.RentException(4);
		}
		if (numOfRentDay <= 1)
			throw new controller.RentException(5);

		Record j = new Record(getId(), customerId, rentdate, new DateTime(rentdate, numOfRentDay));
		setStatus("Rented");
		this.setCurrentRecord(j);

	}

	public void completeMaintenance(DateTime completionDate) throws controller.MaintanceException {
		if (getStatus().equals("Maintaining")) {
			if (completionDate.getTime() > new DateTime(-1).getTime()) {
				setLastMaintenance(completionDate);
				setStatus("Available");
			} else
				throw new controller.MaintanceException();

		}
	}

	public String toString() {
		String s = getId() + ":" + getStnum() + ":" + getStname() + ":" + getSuburb() + ":" + "Premium Suite:"
				+ getBednum() + ":" + getStatus() + ":" + getLastMaintenance().getFormattedDate();
		
		if (getImageFile() == null)
			s += ":null:" + this.getDescription();
		else 
		s +=  ":" + this.getImageFile().getName() + ":" + this.getDescription();

		return s;
	}

	@Override
	public double getfee() {
		setEstDifferDay(getCurrentRecord().getEstDifferentDay());
		setActualDifferDay(getCurrentRecord().getActDifferentDay());
		if (getEstDifferDay() >= getActualDifferDay()) {
			Record j = this.getCurrentRecord();
			j.setRentalfee(554 * getActualDifferDay());
			this.setCurrentRecord(j);
			return j.getRentalfee();
		} else {
			getCurrentRecord().setRentalfee(554 * getEstDifferDay());
			getCurrentRecord().setLatefee(662 * (getActualDifferDay() - getEstDifferDay()));
			return getCurrentRecord().getRentalfee() + getCurrentRecord().getLatefee();
		}
	}

}
