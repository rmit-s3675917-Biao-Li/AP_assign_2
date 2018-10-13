package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.DateTime;
import model.Property;
import model.Record;

public class DatabaseController {
	private static final String DB_NAME = "testDB";
	private static final String table1 = "PROPERTY";
	private static final String table2 = "RECOREDS";

	public static void connect() {

		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME)) {
			System.out.println("Connection to database " + DB_NAME + " created successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void create1() {

		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("CREATE TABLE PROPERTY (" + "id VARCHAR(20) NOT NULL,"
					+ "type VARCHAR(20) NOT NULL," + "stnum VARCHAR(8) NOT NULL," + "stname VARCHAR(20) NOT NULL,"
					+ "suburb VARCHAR(20) NOT NULL," + "bednum INT NOT NULL," + "description VARCHAR(4000) NOT NULL,"
					+ "status VARCHAR(20) NOT NULL," + "lastmaintain VARCHAR(12) NOT NULL,"
					+ "imagelocation VARCHAR(20) NOT NULL," + "PRIMARY KEY (id))");
			if (result == 0) {
				System.out.println("Table " + table1 + " has been created successfully");
			} else {
				System.out.println("Table " + table1 + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void create2() {
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("CREATE TABLE RECOREDS (" + "PROPERTY_ID VARCHAR(20) NOT NULL,"
					+ "RECORD_ID VARCHAR(30) NOT NULL," + "CUSTOMER_ID VARCHAR(20)," + "RENTDATE VARCHAR(20) NOT NULL,"
					+ "ESTMATEDATE VARCHAR(20) NOT NULL," + "ACTUALRETURNDATE VARCHAR(20) NOT NULL,"
					+ "RENTALFEE DOUBLE NOT NULL," + "LATEFEE DOUBLE NOT NULL," + "PRIMARY KEY (RECORD_ID))");
			if (result == 0) {
				System.out.println("Table " + table2 + " has been created successfully");
			} else {
				System.out.println("Table " + table2 + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void insert(model.Property p) {
		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + table1 + " VALUES ('" + p.getId() + "', '" + p.getType() + "', '"
					+ p.getStnum() + "', '" + p.getStname() + "', '" + p.getSuburb() + "', " + p.getBednum() + ", '"
					+ p.getDescription() + "', '" + p.getStatus() + "', '" + p.getLastMaintenance().getFormattedDate()
					+ "', '" + p.getImageFile().getPath() + "')";

			System.out.println(query);

			int result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("Insert into table " + table1 + " executed successfully");
			System.out.println(result + " row(s) affected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {

			if (p.getStatus().equals("Rented")) {
				String query = "INSERT INTO " + table2 + " VALUES ('" + p.getId() + "', '"
						+ p.getCurrentRecord().getRecord_id() + "', '" + p.getCurrentRecord().getCustomer_id() + "', '"
						+ p.getCurrentRecord().getRentdate().getFormattedDate() + "', '"
						+ p.getCurrentRecord().getEstreturndate().getFormattedDate() + "', '"
						+ p.getCurrentRecord().getActualreturndate().getFormattedDate() + "', " + -1 + ", " + -1 + ")";
				int result = stmt.executeUpdate(query);
				con.commit();
				System.out.println("Insert into table " + table2 + " executed successfully");
				System.out.println(result + " row(s) affected");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		model.Record[] rentRecords = p.getRentRecords();
		for (int i = 0; i < rentRecords.length; i++) {
			if (rentRecords[i] != null) {
				try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
					String query = "INSERT INTO " + table2 + " VALUES ('" + p.getId() + "', '"
							+ rentRecords[i].getRecord_id() + "', '" + rentRecords[i].getCustomer_id() + "', '"
							+ rentRecords[i].getRentdate().getFormattedDate() + "', '"
							+ rentRecords[i].getEstreturndate().getFormattedDate() + "', '"
							+ rentRecords[i].getActualreturndate().getFormattedDate() + "', "
							+ rentRecords[i].getRentalfee() + ", " + rentRecords[i].getLatefee() + ")";
					System.out.println(query);
					int result = stmt.executeUpdate(query);
					con.commit();
					System.out.println("Insert into table " + table2 + " executed successfully");
					System.out.println(result + " row(s) affected");
				} catch (

				Exception e) {
					System.out.println(e.getMessage());
				}
			} else
				break;
		}
	}

	public static ArrayList<Property> selectAll() {
		ArrayList<model.Property> propertylist = new ArrayList<model.Property>();
		return propertylist = selectTable1(propertylist);
	}

	public static ArrayList<model.Property> selectTable1(ArrayList<model.Property> propertylist) {
		String id;
		String stname;
		String suburb;
		String type;
		String status;
		String stnum;
		int bednum;
		DateTime lastMaintenance;
		File imageFile;
		String Description;
		model.Property p;

		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + table1;

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while (resultSet.next()) {
					id = resultSet.getString("id");
					type = resultSet.getString("type");
					stnum = resultSet.getString("stnum");
					stname = resultSet.getString("stname");
					suburb = resultSet.getString("suburb");
					bednum = resultSet.getInt("bednum");
					Description = resultSet.getString("description");
					status = resultSet.getString("status");
					lastMaintenance = new DateTime(resultSet.getString("lastmaintain"));
					imageFile = new File(resultSet.getString("imagelocation"));

					if (type.equals("Apartment"))
						p = new model.Apartment(id, type, stnum, stname, suburb, bednum, status, Description,
								imageFile);
					else
						p = new model.Suite(id, type, stnum, stname, suburb, bednum, status, Description, imageFile);

					p = selectTable2(p);
					p.setLastMaintenance(lastMaintenance);
					propertylist.add(p);

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return propertylist;

	}

	public static model.Property selectTable2(model.Property p) {
		String record_id;
		String customerid;
		Record[] rentRecords = new Record[10];
		Record record;
		DateTime rentdate;
		DateTime estreturndate;
		DateTime actualreturndate;
		Double rentfee;
		Double latefee;

		for (int i = 0; i < 10; i++) {
			rentRecords[i] = null;
		}
		int j = 0;
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + table2 + " WHERE PROPERTY_ID = '" + p.getId() + "'";

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while (resultSet.next()) {
					record_id = resultSet.getString("RECORD_ID");
					customerid = resultSet.getString("CUSTOMER_ID");
					rentdate = new DateTime(resultSet.getString("RENTDATE"));
					estreturndate = new DateTime(resultSet.getString("ESTMATEDATE"));
					actualreturndate = new DateTime(resultSet.getString("ACTUALRETURNDATE"));
					rentfee = resultSet.getDouble("RENTALFEE");
					latefee = resultSet.getDouble("LATEFEE");

					if (rentfee == -1) {
						record = new Record(p.getId(), customerid, rentdate, estreturndate);
						p.setCurrentRecord(record);
					} else {
						record = new Record(record_id, rentdate, estreturndate, actualreturndate, rentfee, latefee);
						rentRecords[j] = record;
						j++;
					}

					p.setRentRecords(rentRecords);

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return p;

	}

	public static void deleteTable() {

		// use try-with-resources Statement
		try (Connection con = getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			stmt.executeUpdate("DROP TABLE " + table1);
			int result = stmt.executeUpdate("DROP TABLE " + table2);

			System.out.println(result);

			if (result == 0) {
				System.out.println("Table " + table1 + " has been deleted successfully");
				System.out.println("Table " + table2 + " has been deleted successfully");

			} else {
				System.out.println("Table " + table1 + " was not deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");

		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "SA", "");
		return con;
	}
}
