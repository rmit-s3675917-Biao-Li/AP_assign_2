package model;

import controller.RentException;

public interface PropertyPlug {
	public abstract String toString();
	public abstract String getDetails();
	public abstract void rent(String customerId,DateTime rentDate,int numOfRentDay) throws RentException;
	public abstract double getfee();
}
