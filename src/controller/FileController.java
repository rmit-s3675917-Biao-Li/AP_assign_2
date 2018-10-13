package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DateTime;
import model.Property;

public class FileController {
	private static File F = null;

	public static void importFile() throws Exception {
		if (choose()) {
			Scanner input = null;
			String line = null;
			String[] ss;
			model.Record[] records = new model.Record[10];
			for (int i = 0; i < 10; i++) {
				records[i] = null;
			}
			int i = 0;
			model.Property p = null;
			int number = 0;
			try {
				input = new Scanner(new FileInputStream(F.getAbsolutePath()));

			} catch (FileNotFoundException e) {
				System.err.println("No Such File.");
				System.exit(0);
			}

			ArrayList<Property> PL = DataStorage.getPropertyList();

			while (input.hasNextLine()) {
				// to do
				line = input.nextLine();
				System.out.println(line);
				ss = line.split(":");
				if (ss.length == 6) {
					if (p.getStatus().equals("Rented") && ss[5].equals("none")) {
						model.Record r = new model.Record(ss[0], new model.DateTime(ss[1]), new model.DateTime(ss[2]));
						p.setCurrentRecord(r);
					} else {
						model.Record r = new model.Record(ss[0], new model.DateTime(ss[1]), new model.DateTime(ss[2]),
								new model.DateTime(ss[3]), Double.parseDouble(ss[4]), Double.parseDouble(ss[5]));
						records[i] = r;
						i++;
						p.setRentRecords(records);
					}
				} else {
					if (number != 0)
						PL.add(p);

					if (ss.length == 9)
						p = new model.Apartment(ss[0], ss[4], ss[1], ss[2], ss[3], Integer.parseInt(ss[5]), ss[6],
								ss[8], new File("Files/" + ss[7]));
					else if (ss.length == 10) {
						p = new model.Suite(ss[0], ss[4], ss[1], ss[2], ss[3], Integer.parseInt(ss[5]), ss[6], ss[9],
								new File("Files/" + ss[8]));
						p.setLastMaintenance(new DateTime(ss[7]));
					}

					number++;
					i = 0;
					records = new model.Record[10];
					for (int j = 0; j < 10; j++) {
						records[j] = null;
					}
				}
				System.out.println(line);

			}
			if (number != 0)
				PL.add(p);
			input.close();

			DataStorage.setPropertyList(PL);
		}
	}

	public static void exportFile() throws Exception {
		if (saver()) {
			File f = new File(F.getAbsolutePath() + "/ExportFile.txt");
			try {
				PrintWriter output = new PrintWriter(f);
				ArrayList<Property> PL = DataStorage.getPropertyList();

				for (int i = 0; i < PL.size(); i++) {
					output.write(PL.get(i).toString() + "\n");
					System.out.println(PL.get(i).toString());
					if (PL.get(i).getStatus().equals("Rented"))
						output.write(PL.get(i).getCurrentRecord().toString() + "\n");

					for (int j = 0; j < 10; j++) {
						if (PL.get(i).getRentRecords()[j] == null)
							break;
						output.write(PL.get(i).getRentRecords()[j].toString() + "\n");
					}
				}

				output.close(); // don't forget this method
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public static boolean choose() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Upload Data");
		FileChooser F1 = new FileChooser();
		F = F1.showOpenDialog(stage);
		if (F != null)
			return true;
		else
			return false;
	}

	public static boolean saver() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Export Data");
		DirectoryChooser F1 = new DirectoryChooser();
		F = F1.showDialog(stage);
		System.out.println(F.getAbsolutePath());
		if (F != null)
			return true;
		else
			return false;
	}
}
