package controller;

import java.io.File;
import java.util.ArrayList;
import model.DateTime;
import model.Property;
import view.ChangeInterface;


/**
 * 
 * @author Biao 16 Sep. 2018 2:45:58 pm
 */

public class DataStorage {
	public static ArrayList<Property> propertyList = new ArrayList<Property>();
//	public static DateTime CurrentTime;
	private static File selectImage = null;

	public static void initialization() {

		propertyList = DatabaseController.selectAll();
		
	}
	
	public static File getSelectImage() {
		return selectImage;
	}

	public static void setSelectImage(File selectImage1) {
		selectImage = selectImage1;
	}
	
	
}
