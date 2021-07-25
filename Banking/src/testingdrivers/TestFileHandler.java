package testingdrivers;
import java.util.Date;

import banking_dev.*;

public class TestFileHandler {
	
	
	public static void main(String args[]) {
		double value = -60.40;
		int dollars = (int) value;
		System.out.println(dollars);
		String digits = String.format("%.2f", value);
		System.out.println(digits);
		digits = digits.split("\\.")[1];	//only keep numbers after decimal
		System.out.println(digits);
		int cents = Integer.parseInt(digits);
		System.out.println(cents);
		
		if (value < 0) {
//			isPositive = false;			//isPositive true be default
			dollars *= -1;
		}
		
		
		
		
		
		DataBase db = new DataBase();
		
		System.out.println(db.saveCustomers());
		
		
		
//		Date date = new Date();
//		String test = date.toString();
//		date = new Date(test);
//		
//		System.out.println(date);
		
	}
	
	
	
}

