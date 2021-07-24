
import java.util.*;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class CustomerFile {
	// private String[][][] data;
	private Customer customer = new Customer(123, "Dolu");
	private AccountType accounttype;
	private Account account = new Account(123, accounttype);
	private Fee fees;
	private LastTransaction lasttransaction;
	private Money amount;
	private Date date = new Date();
	private ArrayList<Customer> customers = new ArrayList<>();
	public CustomerFile() {

	}
	

	public void parse(String fileName) throws IOException, ParseException {
		List<List<String>> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(Arrays.asList(values));
			}
			br.close();
			//System.out.println(data.get(1).get(2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		/*
		 * int count = Integer.parseInt(data.get(0).get(0)); for(List innerlist: data) {
		 * for(Object i:innerlist) { while (i[innerlist] ) System.out.print(i+" "); }
		 *//*
			 * System.out.println("\n"); }
			 */
		  int i = 1; 
		  int j = 0;
		  while (i < data.size()) {
			  String customername = null;
			  int customerNumSavings = 0;
			  int customerNumChecking = 0;
			  Date customerOpenedDate = null;
			  Date customerClosedDate;
			  String customerPasscode = null;
			  int customerPin = 0;
			  Date feedate = null;
			  Money feeamount = null;
			  String feetype = "";
			  Date lasttransactiondate = null;
			  Money lasttransactionpreBalance = null;
			  Money lasttransactionchange = null;
			  String lasttransactionaction = "";
			  
			  if(data.get(i).get(0).equalsIgnoreCase("Customer")) {
				  //System.out.print(Double.parseDouble(data.get(2).get(2).replace('$', ' ')));
				  customername = data.get(i).get(2);
				  customerNumSavings = Integer.parseInt(data.get(i).get(3));
				  customerNumChecking = Integer.parseInt(data.get(i).get(4));
				  String line = data.get(i).get(6);
				  date = toDateObject(line);
				  customerOpenedDate = date;
				  customerClosedDate = null;
				  if(data.get(i).get(7).equalsIgnoreCase("not closed")){
					  customerClosedDate = null;
				  }
				  else {
					  String line1 = data.get(i).get(7);
					  date = toDateObject(line1);
					  customerClosedDate = date;
				  }
				  customerPasscode = data.get(i).get(8);
				  customerPin = Integer.parseInt(data.get(i).get(9));
				  Customer customer = null;
				  i++;
				  
				  if(data.get(i).get(0).equalsIgnoreCase("Savings")) {
					  //customer.addAccount(account);
					  amount = new Money(Double.parseDouble(data.get(i).get(2).replace('$', ' ')));
					  Money accountBalance = amount;
					  boolean AttachedCard = false;
					  int accountCardID = 0;
					  if(data.get(i).get(4) != null) {
						  accountCardID = Integer.parseInt(data.get(i).get(4));
						  AttachedCard = true;
					  }
					  String line2 = data.get(i).get(5);
					  date = toDateObject(line2);
					  lasttransactiondate = date; 
					  lasttransactionaction = data.get(i).get(6);
					  NumberFormat format = NumberFormat.getCurrencyInstance();
					  Number number = format.parse(data.get(i).get(7));
					  amount = new Money(number.doubleValue());
					  lasttransactionpreBalance = amount;
					  number = format.parse(data.get(i).get(8));
					  amount = new Money(number.doubleValue());
					  lasttransactionchange = amount;
					  String line3 = data.get(i).get(9);
					  date = toDateObject(line);
					  Date accountOpenedDate = date;
					  Date accountClosedDate;
					  if(data.get(i).get(10).equalsIgnoreCase("not closed")){
						  accountClosedDate = null;
					  } 
					  else {
						  String line4 = data.get(i).get(10);
						  date = toDateObject(line4);
						  accountClosedDate = date;
					  }
					  i++;
					  String line5 = data.get(i).get(1);
					  date = toDateObject(line5);
					  feedate = date;
					  amount = new Money(Double.parseDouble(data.get(i).get(2).replace('$', ' ')));
					  feeamount = amount;
					  feetype = data.get(i).get(3);
					  i++;
					  fees = new Fee(feedate, feeamount, feetype);
					  lasttransaction = new LastTransaction(lasttransactiondate, lasttransactionpreBalance, lasttransactionchange, lasttransactionaction);
					  account = new Account(Integer.parseInt(data.get(i).get(1)), AccountType.SAVINGS,
							  				accountBalance, lasttransaction,
							  				accountOpenedDate, AttachedCard, 
							  				accountCardID);
					  account.addFee(fees);
					  customer.addAccount(account);
					  
					  
				  }
				  
				  if(data.get(i).get(0).equalsIgnoreCase("Checking")) {
					  amount = new Money(Double.parseDouble(data.get(i).get(2).replace('$', ' ')));
					  Money accountBalance = amount;
					  int accountCardID = 0;
					  boolean AttachedCard = false;
					  if(data.get(i).get(4) != null) {
						  accountCardID = Integer.parseInt(data.get(i).get(4));
						  AttachedCard = true;
					  }
					  String line2 = data.get(i).get(5);
					  date = toDateObject(line2);
					  lasttransactiondate = date; 
					  lasttransactionaction = data.get(i).get(6);
					  amount = new Money(Double.parseDouble(data.get(i).get(7).replace('$', ' ')));
					  lasttransactionpreBalance = amount;
					  amount = new Money(Double.parseDouble(data.get(i).get(8).replace('$', ' ')));
					  lasttransactionchange = amount;
					  String line3 = data.get(i).get(9);
					  date = toDateObject(line);
					  Date accountOpenedDate = date;
					  Date accountClosedDate;
					  if(data.get(i).get(10).equalsIgnoreCase("not closed")){
						  accountClosedDate = null;
					  }
					  else {
						  String line4 = data.get(i).get(10);
						  date = toDateObject(line4);
						  accountClosedDate = date;
					  }
					  i++;
					  String line5 = data.get(i).get(1);
					  date = toDateObject(line5);
					  feedate = date;
					  amount = new Money(Double.parseDouble(data.get(i).get(2).replace('$', ' ')));
					  feeamount = amount;
					  feetype = data.get(i).get(3);
					  i++;
					  fees = new Fee(feedate, feeamount, feetype);
					  lasttransaction = new LastTransaction(lasttransactiondate, lasttransactionpreBalance, lasttransactionchange, lasttransactionaction);
					  account = new Account(Integer.parseInt(data.get(i).get(1)), AccountType.CHECKING,
							  				accountBalance, lasttransaction,
							  				accountOpenedDate, AttachedCard, 
							  				accountCardID);
					  account.addFee(fees);
					  customer.addAccount(account);
				  }
				  
				  
			  }
			  
			  i++;
			  customers.add(customer);
		  }
		// return customer;
		  System.out.println(customers);
	}
	
	@SuppressWarnings("deprecation")
	public Date toDateObject(String line) {
		String[] datevalue = line.split("/");
		//System.out.println(datevalue[0]);
		date.setMonth(Integer.parseInt(datevalue[0]));
		date.setDate(Integer.parseInt(datevalue[1]));
		date.setYear(Integer.parseInt(datevalue[2]));
		return date;
		
	}

	/*
	 * public void save(Customer customer) throws IOException { String fileName =
	 * "BankEmployees.csv"; String name = customer.getName(); int ID =
	 * employee.getEmployeeID(); String userName = employee.getLoginusername();
	 * String passWord = employee.getLoginpwd(); String[] data = {name,
	 * String.valueOf(ID), userName, passWord}; try (BufferedWriter br = new
	 * BufferedWriter(new FileWriter(fileName))) { StringBuilder sb = new
	 * StringBuilder(); for (String item : data) { sb.append(item); sb.append(",");
	 * } br.write(sb.toString()); br.close(); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } }
	 */
}