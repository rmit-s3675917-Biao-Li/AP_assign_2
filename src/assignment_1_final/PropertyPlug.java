package assignment_1_final;

public interface PropertyPlug {
	public String toString();
	public String getDetails();
	public boolean rent(String customerId,DateTime rentDate,int numOfRentDay);
	public boolean Return(DateTime returnDate);
	public boolean performMaintenance();
	public boolean completeMaintenance(DateTime completionDate);
}
