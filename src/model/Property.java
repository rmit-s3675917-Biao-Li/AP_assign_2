package model;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;

import javafx.scene.image.Image;

public abstract class Property implements PropertyPlug {
	private String id;
	private String stname;
	private String suburb;
	private String type;
	private String status;// 0=available for rent, 1=being rented, 2=under maintenance
	private String stnum;
	private int bednum;
	private int EstDifferDay;
	private int ActualDifferDay;
	private Record[] rentRecords = new Record[10];
	private Record currentRecord;
	private DateTime lastMaintenance = new DateTime();
	private File imageFile;
	private Image image;
	private String Description;

	public Property(String id, String type, String stnum, String stname, String suburb, int bednum, String status,
			String Description, File Imagefile) throws MalformedURLException {
		this.id = id;
		this.stname = stname;
		this.type = type;
		this.stnum = stnum;
		this.suburb = suburb;
		this.bednum = bednum;
		this.status = status;
		this.Description = Description;
		currentRecord = new Record();
		if (Imagefile == null)
			this.imageFile = new File("Files/Image-Unavailable.jpg");
		else
			this.imageFile = Imagefile;

		this.image = new Image(this.imageFile.toURI().toURL().toString());

		for (int i1 = 0; i1 < 10; i1++) {
			rentRecords[i1] = null;
		}

	}

	public void Return(DateTime returnDate) throws controller.ReturnException {
		if (new DateTime().diffDays(returnDate, getCurrentRecord().getRentdate()) <= 0) {
			throw new controller.ReturnException(1);
		} else {
			Record j = this.getCurrentRecord();
			j.setActualreturndate(returnDate);
			this.setCurrentRecord(j);
			getfee();
			setStatus("Available");
			this.addToRentRecords(getCurrentRecord());
			this.setCurrentRecord(new Record());
		}
	}

	public boolean performMaintenance() {
		switch (getStatus()) {
		case "Available":
			setStatus("Maintaining");
			return true;
		case "Rented":
			System.out.print("This property is being rented.");
			return false;
		case "Maintaining":
			System.out.print("This property is under maintenaince.");
			return false;
		}
		return false;
	}

	public String printOneRecord(Record r) {
		DecimalFormat df = new DecimalFormat("0.00##");
		String s = "";
		s += ("Record ID:                                                     ") + r.getRecord_id() + "\n";
		s += ("Rent Date:                                                     ") + r.getRentdate().getFormattedDate() + "\n";
		s += ("Estimated Return Date:                                ") + r.getEstreturndate().getFormattedDate() + "\n";
		s += ("Actual Return Date:                                      ") + r.getActualreturndate().getFormattedDate() + "\n";
		s += ("Rental Fee:                                                    ") + df.format(r.getRentalfee()) + "\n";
		s += ("Late Fee:                                                       ") + df.format(r.getLatefee()) + "\n";
        
		s += "______________________________________\n\n";
		return s;

	}

	public String getDetails() {
		String s = "";
		s += "RENTAL RECORD:";
		// return record
		if (getStatus().equals("Rented")) {
			s += "\n";
			if (!(getRentRecords()[0] == null)) {
				s += ReturnCurrentRecord() + printRecords();
			} else
				s += ReturnCurrentRecord();

		} else if (!(getRentRecords()[0] == null)) {
			s += "\n";
			s += printRecords();
		} else {
			s += "      Empty";
		}
		return s;
	}

	public String printRecords() {
		String s = "";
		int i = 0;

		while (i < 10) {
			if (rentRecords[i] == null)

				break;

			s += printOneRecord(rentRecords[i]);
			i++;
		}
		return s;
	}

	public void addToRentRecords(Record newrRecord) {
		for (int i = 9; i > 0; i--) {
			rentRecords[i] = rentRecords[i - 1];
		}
		rentRecords[0] = newrRecord;
	}

	private String ReturnCurrentRecord() {
		String s = "";
		Record j = getCurrentRecord();
		s += "Record ID:                                                     " + j.getRecord_id() + "\n";
		s += "Customer ID:			 			       " + j.getCustomer_id() + "\n";
		s += "Rent Date:                                                     " + j.getRentdate().getFormattedDate() + "\n";
		s += "Estimated Return Date:                                " + j.getEstreturndate().getFormattedDate() + "\n";
		s += "______________________________________\n";
		return s;
	}

//	private String AutoFillRightPad(String s) {
//		String value = String.format("%-40s", s);
//		return value;
//	}

	public String getId() {
		return id;
	}

	public String getStname() {
		return stname;
	}

	public void setStname(String stname) {
		this.stname = stname;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStnum() {
		return stnum;
	}

	public int getBednum() {
		return bednum;
	}

	public int getEstDifferDay() {
		return EstDifferDay;
	}

	public void setEstDifferDay(int estDifferDay) {
		EstDifferDay = estDifferDay;
	}

	public int getActualDifferDay() {
		return ActualDifferDay;
	}

	public void setActualDifferDay(int actualDifferDay) {
		ActualDifferDay = actualDifferDay;
	}

	public Record[] getRentRecords() {
		return rentRecords;
	}

	public void setRentRecords(Record[] records) {
		this.rentRecords = records;
	}

	public Record getCurrentRecord() {
		return currentRecord;
	}

	public DateTime getLastMaintenance() {
		return lastMaintenance;
	}

	public void setLastMaintenance(DateTime lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setCurrentRecord(Record record) {
		this.currentRecord = record;
	}

	public String getDescription() {
		return Description;
	}

	public String getDescription22() {
		String s = "";
		String d = Description;
		int i = 50;
		while (d.length() > 50) {
			while (d.charAt(i) != ' ')
				i--;
			s += d.substring(0, i + 1) + "\n";
			d = d.substring(i + 1);
			i = 50;
		}
		s = s + d;
		return s;

	}

	public File getImageFile() {
		return imageFile;
	}

}
