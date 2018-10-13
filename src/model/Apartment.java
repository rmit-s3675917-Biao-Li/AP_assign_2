package model;

import java.io.File;
import java.net.MalformedURLException;
import controller.RentException;


public class Apartment extends Property {
	
	public Apartment(String id, String type, String stnum, String stname, String suburb, int bednum, String status, String Description, File image)
			throws MalformedURLException {
		super(id, type, stnum, stname, suburb, bednum, status, Description, image);

	}


	public void rent(String customerId, DateTime rentdate, int numOfRentDay) throws RentException {
		if (numOfRentDay > 28) {
			throw new RentException(1);
		}
		int i = Integer.parseInt(rentdate.getDayOfWeek());
		if (i == 5 || i == 6) {
			if (numOfRentDay < 3) {
				throw new RentException(2);
			}
		} else {
			if (numOfRentDay < 2) {
				throw new RentException(3);
			}
		}
	
			Record j = new Record(getId(),customerId,rentdate,new DateTime(rentdate, numOfRentDay));
			setStatus("Rented");
			this.setCurrentRecord(j);
	}


	public void completeMaintenance() {
		if (getStatus().equals("Maintaining")) {
			setLastMaintenance(new DateTime());
			setStatus("Available");
		}
	}
	

	public String toString() {
		String s = getId() + ":" + getStnum() + ":" + getStname() + ":" + getSuburb() + ":" + "Apartment:" + getBednum()
				+ ":" + getStatus();
		
		if (getImageFile() == null)
			s += ":null:" + this.getDescription();
		else 
		s +=  ":" + this.getImageFile().getName() + ":" + this.getDescription();
		return s;
	}



	@Override
	public double getfee() {
		double priceperday = 0;
		Record j = new Record();
		j = this.getCurrentRecord();
		setEstDifferDay(j.getEstDifferentDay());
		setActualDifferDay(j.getActDifferentDay());
		switch (getBednum()) {
		case 1:
			priceperday = 143;
			break;
		case 2:
			priceperday = 210;
			break;
		default:
			priceperday = 319;
		}
		j.setRentalfee(getrentalfee(getEstDifferDay(), getActualDifferDay()) * priceperday);
		j.setLatefee(getlatefee(getEstDifferDay(), getActualDifferDay()) * priceperday * 1.15);
		setCurrentRecord(j);
		return j.getRentalfee() + j.getLatefee();
	}

	private double getlatefee(int Eday, int Aday) {
		if (Eday <= Aday)
			return Aday - Eday;
		else
			return 0;
	}

	private double getrentalfee(int Eday, int Aday) {
		if (Eday <= Aday)
			return Eday;
		else
			return Aday;
	}



}
