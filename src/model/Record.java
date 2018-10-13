package model;

import java.text.DecimalFormat;

public class Record {
	private String record_id;
	private String customer_id;
	private DateTime rentdate;
	private DateTime estreturndate;
	private DateTime actualreturndate;
	private double rentalfee;
	private double latefee;

	public Record() {
		rentdate = new DateTime();
		estreturndate = new DateTime();
		customer_id = "";
		this.actualreturndate = new DateTime();
		rentalfee = 0;
		latefee = 0;
	}
//for current record import
	public Record(String record_id, DateTime rentdate, DateTime estreturndate) {
		this.record_id = record_id;
		this.rentdate = rentdate;
		this.estreturndate = estreturndate;
		this.actualreturndate = new DateTime();
		rentalfee = 0;
		latefee = 0;
	}

	public Record(String property_id, String customer_id, DateTime rentdate, DateTime estreturndate) {
		this.record_id = property_id + "_" + customer_id + "_" + rentdate.getEightDigitDate();
		this.customer_id = customer_id;
		this.rentdate = rentdate;
		this.estreturndate = estreturndate;
		this.actualreturndate = new DateTime();
		rentalfee = 0;
		latefee = 0;
	}

//for old record import
	public Record(String record_id, DateTime rentdate, DateTime estreturndate, DateTime actualreturndate,
			double rentalfee, double latefee) {
		this.record_id = record_id;
		this.rentdate = rentdate;
		this.estreturndate = estreturndate;
		this.actualreturndate = actualreturndate;
		this.rentalfee = rentalfee;
		this.latefee = latefee;

	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public DateTime getRentdate() {
		return rentdate;
	}

	public void setRentdate(DateTime rentdate) {
		this.rentdate = rentdate;
	}

	public DateTime getEstreturndate() {
		return estreturndate;
	}

	public void setEstreturndate(DateTime estreturndate) {
		this.estreturndate = estreturndate;
	}

	public DateTime getActualreturndate() {
		return actualreturndate;
	}

	public void setActualreturndate(DateTime actualreturndate) {
		this.actualreturndate = actualreturndate;
	}

	public double getRentalfee() {
		return rentalfee;
	}

	public void setRentalfee(double rentalfee) {
		this.rentalfee = rentalfee;
	}

	public double getLatefee() {
		return latefee;
	}

	public void setLatefee(double latefee) {
		this.latefee = latefee;
	}

	public int getEstDifferentDay() {
		return estreturndate.diffDays(estreturndate, rentdate);
	}

	public int getActDifferentDay() {
		return estreturndate.diffDays(actualreturndate, rentdate);
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00##");
		if (this.actualreturndate == null && rentalfee == 0 && latefee == 0)
			return record_id + ":" + rentdate.getFormattedDate() + ":" + estreturndate.getFormattedDate()
					+ ":none:none:none";
		else
			return record_id + ":" + rentdate.getFormattedDate() + ":" + estreturndate.getFormattedDate() + ":"
					+ actualreturndate.getFormattedDate() + ":" + df.format(rentalfee) + ":" + df.format(latefee);

	}
}
