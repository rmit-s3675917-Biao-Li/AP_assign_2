package model;

import java.text.DecimalFormat;

public class Apartment extends Property {

	public Apartment(String id, String type, String stnum, String stname, String suburb, int bednum) {
		super(id, type, stnum, stname, suburb, bednum);
		this.setCurrentRecord(new Record());
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

	public double getlatefee(int Eday, int Aday) {
		if (Eday <= Aday)
			return Aday - Eday;
		else
			return 0;
	}

	public double getrentalfee(int Eday, int Aday) {
		if (Eday <= Aday)
			return Eday;
		else
			return Aday;
	}

	private String ReturnCurrentRecord() {
		String s = "";
		Record j = getCurrentRecord();
		s += "Record ID:             " + j.getRecord_id() + "\n";
		s += "Rent Date:             " + j.getRentdate().getFormattedDate() + "\n";
		s += "Estimated Return Date: " + j.getEstreturndate().getFormattedDate() + "\n";
		s += "______________________________________\n";
		return s;
	}

	public String printRecords() {
		String s = "";
		Record[] Records = getRentRecords();
		int i = 0;
		// test
		while (i < 10) {
			if (Records[i] == null)
				break;
			Record j = Records[i];
			s += printOneRecord(j);
			i++;
		}
		return s;
	}

	public String printOneRecord(Record j) {
		DecimalFormat df = new DecimalFormat("0.00##");
		String s = "";
		s += "Record ID:             " + j.getRecord_id() + "\n";
		s += "Rent Date:             " + j.getRentdate().getFormattedDate() + "\n";
		s += "Estimated Return Date: " + j.getEstreturndate().getFormattedDate() + "\n";
		s += "Actual Return Date:    " + j.getActualreturndate().getFormattedDate() + "\n";
		s += "Rental Fee:            " + df.format(j.getRentalfee()) + "\n";
		s += "Late Fee:              " + df.format(j.getLatefee()) + "\n";
		s += "______________________________________\n\n";
		return s;

	}

	@Override
	public boolean rent(String customerId, DateTime rentDate, int numOfRentDay) {
		if (this instanceof Suite) {
			if (rentDate.diffDays(rentDate, getLastMaintenance()) + numOfRentDay > 10) {
				System.out.println("This Premium Suite is needed to be maintained first! ");
				return false;
			}
			if (numOfRentDay <= 1) {
				System.out.println("Premium Suite should be rented for a minimum of 1 day!");
				return false;
			}

		}

		if (this instanceof Apartment) {
			if (numOfRentDay > 28) {
				System.out.println("The apartment should not be rented for more than 28 days!");
				return false;
			}
			int i = Integer.parseInt(rentDate.getDayOfWeek());
			if (i == 5 || i == 6) {
				if (numOfRentDay < 3) {
					System.out.println("Today is Fri/Sat, the number of rent day should be a minimum of 3 days!");
					return false;
				}
			} else {
				if (numOfRentDay < 2) {
					System.out.println(
							"Today is Sun/Mon/Tue/Wed/Thu, the number of rent day should be a minimum of 3 days!");
					return false;
				}
			}
		}

		switch (getStatus()) {
		case 0:
			Record j = this.getCurrentRecord();
			j.setRecord_id(getId() + "_" + customerId + "_" + rentDate.getEightDigitDate());
			DateTime time = new DateTime(rentDate, numOfRentDay);
			j.setRentdate(rentDate);
			j.setCustomer_id(customerId);
			j.setEstreturndate(time);
			setStatus(1);
			this.setCurrentRecord(j);
			return true;
		case 1:
			return false;
		case 2:
			return false;
		}
		return false;
	}

	@Override
	public boolean Return(DateTime returnDate) {
		if (getStatus() != 1) {
			switch (getStatus()) {
			case 0:
				System.out.println("This property is not be rented.");
				break;
			case 2:
				System.out.println("This property is under maintenaince.");
				break;
			default:
				break;
			}
			return false;
		}
		if (FlexiRentSystem.CurrentTime.diffDays(returnDate, getCurrentRecord().getRentdate()) <= 0) {
			System.out.println("The return date is not after the rent date");
			return false;
		} else {
			Record j = this.getCurrentRecord();
			j.setActualreturndate(returnDate);
			this.setCurrentRecord(j);
			getfee();
			setStatus(0);
			this.addToRentRecords(getCurrentRecord());
			this.setCurrentRecord(new Record());
			return true;
		}
	}

	@Override
	public boolean performMaintenance() {
		switch (getStatus()) {
		case 0:
			setStatus(2);
			return true;
		case 1:
			System.out.print("This property is being rented.");
			return false;
		case 2:
			System.out.print("This property is under maintenaince.");
			return false;
		}
		return false;
	}

	@Override
	public boolean completeMaintenance(DateTime completionDate) {
		if (getStatus() == 2) {
			setLastMaintenance(completionDate);
			setStatus(0);
			return true;
		} else
			return false;
	}

	public String toString() {
		String s = getId() + ":" + getStnum() + ":" + getStname() + ":" + getSuburb() + ":" + "Apartment:" + getBednum()
				+ ":";
		switch (getStatus()) {
		case 0:
			s += "Available";
			break;
		case 1:
			s += "Rented";
			break;
		case 2:
			s += "Being maintenancing";
			break;
		}
		return s;
	}

	@Override
	public String getReadableDetails() {
		String s = "";
		s += "Property ID:        " + getId() + "\n";
		s += "Address:            " + getStnum() + " " + getStname() + " " + getSuburb() + "\n";
		s += "Type:               " + getType() + "\n";
		s += "Bedroom:            " + getBednum() + "\n";
		s += "Status:             ";
		switch (getStatus()) {
		case 0:
			s += "Available\n";
			break;
		case 1:
			s += "Rented\n";
			break;
		case 2:
			s += "Under maintenance\n";
			break;
		}
		return s;
	}

	@Override
	public String getDetails() {
		String s = "";
		s += this.getReadableDetails();
		// test final
		if (this instanceof Suite)
			s += "Last maintenance:   " + getLastMaintenance().getFormattedDate() + "\n";
		s += "RENTAL RECORD";
		// return record
		if (getStatus() == 1) {
			s += "\n";
			if (!(getRentRecords()[0] == null)) {
				s += ReturnCurrentRecord() + printRecords();
			} else
				s += ReturnCurrentRecord();
		} else if (!(getRentRecords()[0] == null)) {
			s += "\n";
			s += printRecords();
		} else {
			s += ":      Empty";
		}
		return s;
	}

}
