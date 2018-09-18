package assignment_1_final;

import java.util.ArrayList;
import java.util.Scanner;

public class FlexiRentSystem{
	public static DateTime CurrentTime = new DateTime();
	Scanner input = new Scanner(System.in);
	private ArrayList<Property> propertyList = new ArrayList<Property>();
	private int AorP;
	private String choice;
	private String property_id;
	private String customer_id;
	private ArrayList<String> array = new ArrayList<String>();
	private String stname;
	private String suburb;
	private String type;
	// int status;// 0=available for rent, 1=being rented, 2=under maintenance
	private String stnum;
	private int bednum;
	private int days;
	private boolean end = false;
	private int i;
	private int m;
	private DateTime k;
	private Property j;
	private String date;
	private String[] dateSplit;

	void StartUp() {
		for (i = 0; i < 8; i++)
			array.add(String.valueOf(i + 1));

		// test
//		test a = new test();
//		a.initialization(propertyList);

		while (!end) {
			this.printMenu();
			this.refresh(propertyList);
			choice = input.next();
			if (!array.contains(choice)) {
				System.out.println("You should enter number 1-8");
				continue;
			}
			switch (choice) {

			// add property
			case "1":
				System.out.print("Property Id:");
				property_id = input.next();
				input.nextLine();

				i = 0;
				System.out.print("Property Type:");
				type = input.nextLine();

				if (!property_id.startsWith("A_") && !property_id.startsWith("S_")) {
					switch (type) {
					case "Apartment":
						property_id = "A_" + property_id;
						break;
					case "Premium Suite":
						property_id = "S_" + property_id;
					}
				}

				if (FlexiRentSystem.checkRepeatId(propertyList, property_id) != -1) {
					System.out.println("This property id already existed");
					break;
				} else {
					System.out.print("Street Number:");
					stnum = input.next();
					System.out.print("Street Name:");
					stname = input.next();
					System.out.print("Suburb:");
					suburb = input.next();
					if (type.equals("Apartment")) {
						System.out.print("Number Of Bedrooms:");
						bednum = input.nextInt();
					} else
						bednum = 3;
				}

				if (type.equals("Apartment"))
					j = new Apartment(property_id, type, stnum, stname, suburb, bednum);
				else {
					if (type.equals("Premium Suite")) {
						System.out.print("Last Maintenance Date:");
						date = input.next();
						dateSplit = date.split("/");
						j = new Suite(property_id, type, stnum, stname, suburb, bednum);
						k = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
								Integer.parseInt(dateSplit[2]));
						j.setLastMaintenance(k);
					} else {
						System.out.println("This property is not added successfully");
						break;
					}
				}
				propertyList.add(j);
				System.out.println("Property " + property_id + " has been successfully added");
				System.out.println("______________________________________");
				break;

			// Rent property
			case "2":
				System.out.print("Enter Property Id:");
				property_id = input.next();
				m = FlexiRentSystem.checkRepeatId(propertyList, property_id);
				if (m == -1) {
					System.out.println("This property id is not existed");
					break;
				}
				System.out.print("Customer Id:");
				customer_id = input.next();
				do {
					try {
						System.out.print("Rent date(dd/mm/yyyy):");
						date = input.next();
						dateSplit = date.split("/");// to do
						k = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
								Integer.parseInt(dateSplit[2]));
						if (k.diffDays(k, CurrentTime) < 0) throw new Exception();
						break;
					} catch (Exception e) {
						System.out.println("you can't input the past date");
					}
				} while (0 == 0);

				System.out.print("How many days?:");
				days = input.nextInt();
				if (!propertyList.get(m).rent(customer_id, k, days)) {
					System.out.println(
							propertyList.get(m).getType() + " " + propertyList.get(m).getId() + " could not be rented");
					break;
				} else
					System.out.println(propertyList.get(m).getType() + " " + propertyList.get(m).getId()
							+ " is now rented by customer " + customer_id);

				break;

			// return property
			case "3":
				System.out.print("Enter Property Id:");
				property_id = input.next();
				m = FlexiRentSystem.checkRepeatId(propertyList, property_id);
				if (m == -1) {
					System.out.println("This property id is not existed");
					break;
				}
				System.out.print("Return date(dd/mm/yyyy):");
				date = input.next();
				dateSplit = date.split("/");// to do
				k = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
						Integer.parseInt(dateSplit[2]));

				if (propertyList.get(m).Return(k)) {
					System.out.println(propertyList.get(m).getType() + " " + propertyList.get(m).getId()
							+ " is returned by customer " + propertyList.get(m).getRentRecords()[0].getCustomer_id());
					String s = propertyList.get(m).getReadableDetails();
					s += "RENTAL RECORD\n";
					s += propertyList.get(m).printOneRecord(propertyList.get(m).getRentRecords()[0]);
					System.out.print(s);
					break;
				}

				// maintain property
			case "4":
				System.out.print("Enter property id: ");
				property_id = input.next();
				m = FlexiRentSystem.checkRepeatId(propertyList, property_id);
				if (m == -1) {
					System.out.println("This property id is not existed");
					break;
				} else if (propertyList.get(m).performMaintenance()) {
					System.out.println(propertyList.get(m).getType() + " " + propertyList.get(m).getId()
							+ " is now under maintenance");
				} else {
					System.out.println("This property could not be maintained. ");
				}
				break;

			// complete maintenance
			case "5":
				System.out.print("Enter property id: ");
				property_id = input.next();
				m = FlexiRentSystem.checkRepeatId(propertyList, property_id);
				if (m == -1) {
					System.out.println("This property id is not existed");
					break;
				}
				if (propertyList.get(m) instanceof Suite) {
					System.out.print("Maintenance completion date (dd/mm/yyyy): ");
					date = input.next();
					dateSplit = date.split("/");// to do
					k = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
							Integer.parseInt(dateSplit[2]));
				} else
					k = new DateTime();

				if (propertyList.get(m).completeMaintenance(k)) {
					System.out.println(propertyList.get(m).getType() + " " + propertyList.get(m).getId()
							+ " has all maintenance completed and ready for rent");
				} else if (propertyList.get(m).getStatus() == 2) {
					System.out.println("This property will finish maintenance on " + k.getFormattedDate());

				} else
					System.out.println("This property is not being maintained");

				break;

			// display all properties
			case "6":
				m = 0;
				String s = "";
				if (propertyList.isEmpty()) {
					System.out.println("There is no property recorded in this system");
					break;
				}
				while (m < propertyList.size()) {
					s += propertyList.get(m).getDetails();
					System.out.println(s);
					s = "";
					System.out.println("______________________________________");
					m++;
				}
				break;

			// turn off the system
			case "7":
				end = true;
				System.out.println("System Is Closed");
				System.out.println("______________________________________");
				break;
			// test
//			case "8":
//				System.out.println("what date you wanna set to be the current time (dd/mm/yyyy)");
//				date = input.next();
//				dateSplit = date.split("/");
//				k = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
//						Integer.parseInt(dateSplit[2]));
//				CurrentTime = k;
//				break;
			}
		}
	}

	void printMenu() {
		System.out.println("****** FLEXRENT SYSTEM MENU *******");
		System.out.println("Add Property:                     1");
		System.out.println("Rent Property:                    2");
		System.out.println("Return Property:                  3");
		System.out.println("Property Maintenance:             4");
		System.out.println("Complete Maintenance:             5");
		System.out.println("Display All Properties:           6");
		System.out.println("Exit Program:                     7");
		// System.out.println("Pretend being several days after 8");
		System.out.print("Enter your choice:");
	}

	static int checkRepeatId(ArrayList<Property> propertyList, String id) {
		int i = 0;
		while (i < propertyList.size()) {
			if (propertyList.get(i).getId().equals(id))
				return i;
			i++;
		}
		return -1;
	}

	// refresh
	void refresh(ArrayList<Property> propertyList) {
		int i = 0;
		while (i < propertyList.size()) {
			if (propertyList.get(i).getLastMaintenance().getTime() < CurrentTime.getTime()
					&& propertyList.get(i).getStatus() == 2 && propertyList.get(i).getWaitingMaintenance()) {
				propertyList.get(i).setStatus(0);
			}
			if (propertyList.get(i).getStatus() == 1) {
				if (propertyList.get(i).getCurrentRecord().getRentdate().getEightDigitDate()
						.equals(CurrentTime.getEightDigitDate())) {
					System.out.println(propertyList.get(i).toString());
				}
			}
			i++;
		}
	}

}
