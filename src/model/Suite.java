package model;

public class Suite extends Apartment {

	public Suite(String id, String type, String stnum, String stname, String suburb, int bednum) {
		super(id, type, stnum, stname, suburb, bednum);
		this.setCurrentRecord(new Record());
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

	@Override
	public boolean completeMaintenance(DateTime completionDate) {
		if (getStatus() == 2) {
			if (completionDate.getTime() <= FlexiRentSystem.CurrentTime.getTime()) {
				setLastMaintenance(completionDate);
				setStatus(0);
				return true;
			} else {
				setLastMaintenance(completionDate);
				setGetwaiting(true);
				return false;
			}
		} else
			return false;
	}

	public String toString() {
		String s = getId() + ":" + getStnum() + ":" + getStname() + ":" + getSuburb() + ":" + "Premium Suite:"
				+ getBednum() + ":";
		switch (getStatus()) {
		case 0:
			s += "Available:" + getLastMaintenance().getFormattedDate();
			break;
		case 1:
			s += "Rented:" + getLastMaintenance().getFormattedDate();
			break;
		case 2:
			s += "Being maintenancing";
			break;
		}
		return s;
	}

}
