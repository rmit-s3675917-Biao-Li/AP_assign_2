package controller;

import java.io.File;
import java.util.ArrayList;
import model.Property;




public class DataStorage {
	private static ArrayList<Property> propertyList = new ArrayList<Property>();
	private static File selectImage = null;

	public static void initialization() {

		propertyList = DatabaseController.selectAll();
		
	}
	
	public static ArrayList<Property> getPropertyList() {
		return propertyList;
	}

	public static void setPropertyList(ArrayList<Property> propertyList) {
		DataStorage.propertyList = propertyList;
	}

	public static File getSelectImage() {
		return selectImage;
	}

	public static void setSelectImage(File selectImage1) {
		selectImage = selectImage1;
	}
	
	
}
