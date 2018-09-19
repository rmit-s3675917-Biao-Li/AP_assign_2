package model;

import javafx.scene.image.Image;

public abstract class Property implements PropertyPlug{
		 private String id;
		 private String stname;
		 private String suburb;
		 private String type;
		 private int status;//0=available for rent, 1=being rented, 2=under maintenance
		 private String stnum;
		 private int bednum;
		 private int EstDifferDay;
		 private int ActualDifferDay;
		 private Record[] rentRecords = new Record[10];
		 private Record currentRecord;
		 private DateTime lastMaintenance = new DateTime(0,0,0);
		 private boolean getwaiting;
		 private Image image;
		
		public Property(String id, String type, String stnum, String stname, String suburb, int bednum) {
			this.id = id;
			this.stname = stname;
			this.type = type;
			this.stnum = stnum;
			this.suburb = suburb;
			this.bednum = bednum;
			this.status = 0;
			getwaiting = false;
			for (Record record : rentRecords) {
				record = null;
			}
		}
		public abstract double getfee();
		public abstract String printRecords();
		public abstract String printOneRecord(Record j);
		public abstract String getReadableDetails();
		public String getId() {
			return id;
		}
		public boolean isGetwaiting() {
			return getwaiting;
		}
		public void setGetwaiting(boolean getwaiting) {
			this.getwaiting = getwaiting;
		}
		public void setId(String id) {
			this.id = id;
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
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getStnum() {
			return stnum;
		}
		public void setStnum(String stnum) {
			this.stnum = stnum;
		}
		public int getBednum() {
			return bednum;
		}
		public void setBednum(int bednum) {
			this.bednum = bednum;
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
		public void setRentRecords(Record[] rentRecords) {
			this.rentRecords = rentRecords;
		}
		public void addToRentRecords(Record newrRecord) {
			for (int i=9;i>0;i--) {
				rentRecords[i] = rentRecords[i-1];
			}
			rentRecords[0] = newrRecord;
		
		}
		public Record getCurrentRecord() {
			return currentRecord;
		}
		public void setCurrentRecord(Record currentRecord) {
			this.currentRecord = currentRecord;
		}
		public DateTime getLastMaintenance() {
			return lastMaintenance;
		}
		public void setLastMaintenance(DateTime lastMaintenance) {
			this.lastMaintenance = lastMaintenance;
		}
		public boolean getWaitingMaintenance() {
			return getwaiting;
		}
	}

