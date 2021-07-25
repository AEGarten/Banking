package banking_dev;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class CustomerFile {
	// private String[][][] data;
	private Customer customer;
	//private AccountType accounttype;
	private Account account;
	private Fee fees;
	private LastTransaction lasttransaction;
	private Money amount;
	private Date date = new Date();
	private ArrayList<Customer> customers = new ArrayList<>();
	

	public ArrayList<Customer> parse(String fileName) throws IOException, ParseException {
		List<List<String>> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(Arrays.asList(values));
			}
			br.close();
			//System.out.println(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		  int i = 1; 
		  while (i < data.size()) {
			  String customername;
			  int customerNumSavings;
			  int customerNumChecking;
			  Date customerOpenedDate;
			  Date customerClosedDate;
			  String customerPasscode;
			  int customerPin = 0;
			  Date feedate;
			  Money feeamount;
			  String feetype;
			  Date lasttransactiondate = null;
			  Money lasttransactionpreBalance;
			  Money lasttransactionchange;
			  String lasttransactionaction = null;
			  
			  if(data.get(i).get(0).equalsIgnoreCase("Customer")) {
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
				  customer = new Customer(Integer.parseInt(data.get(i).get(1)), customername,
							customerPasscode, customerNumSavings,
							customerNumChecking, customerPin, 
							customerOpenedDate);
				  i++;
				  
				  if(data.get(i).get(0).equalsIgnoreCase("Savings")) {
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
					  date = toDateObject(line3);
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
					  
					  fees = new Fee(feedate, feeamount, feetype);
					  lasttransaction = new LastTransaction(lasttransactiondate, lasttransactionpreBalance, lasttransactionchange, lasttransactionaction);
					  account = new Account(Integer.parseInt(data.get(i-1).get(1)), AccountType.SAVINGS,
							  				accountBalance, lasttransaction,
							  				accountOpenedDate, AttachedCard, 
							  				accountCardID);
					  account.addFee(fees);
					  customer.addAccount(account);
					  i++;
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
					  date = toDateObject(line3);
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
					  
					  fees = new Fee(feedate, feeamount, feetype);
					  lasttransaction = new LastTransaction(lasttransactiondate, lasttransactionpreBalance, lasttransactionchange, lasttransactionaction);
					  account = new Account(Integer.parseInt(data.get(i-1).get(1)), AccountType.CHECKING,
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
		  //System.out.println(customers);
		  return customers;	  
	}

	public void save(ArrayList<Customer> customers, String fileName) throws IOException {
		int noofcustomers = customers.size();
		int count = 0;
		List<List<String>> data = new ArrayList<>();
		
		List<String> datafield = new ArrayList<>();
		//List<String> datafield1 = new ArrayList<>();
		//List<String> datafield2 = new ArrayList<>();
		//	List<String> datafield3 = new ArrayList<>();
		//List<String> datafield4 = new ArrayList<>();
		datafield.add(Integer.toString(noofcustomers));
		data.add(datafield);

		//datafields.clear();
		//try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
		//StringBuilder sb = new StringBuilder(); 
		String stringdate;
		while (count < noofcustomers) {
			List<String> datafield1 = new ArrayList<>();
			List<String> datafield2 = new ArrayList<>();
			List<String> datafield3 = new ArrayList<>();
			List<String> datafield4 = new ArrayList<>();
			List<String> datafield5 = new ArrayList<>();
			datafield1.add("Customer");
			customer = customers.get(count);
			datafield1.add(Integer.toString(customer.getID()));
			datafield1.add(customer.getName());
			datafield1.add(Integer.toString(customer.getNumSavings()));
			datafield1.add(Integer.toString(customer.getNumChecking()));
			int i = 0;
			int numFees = 0;
			while (i < customer.getAccounts().size()) {
				if (customer.getAccounts().get(i).getFees().get(0).type.equalsIgnoreCase("Overdraft") ) {
					numFees++;
				}
				i++;
			}
			datafield1.add(Integer.toString(numFees));
			stringdate = toStringObject(customer.getOpened());
			datafield1.add(stringdate);
			System.out.println(stringdate);
			if(customer.getClosed() == null) {
				datafield1.add("not closed");
			}
			else {
				stringdate = toStringObject(customer.getClosed());
				datafield1.add(stringdate);
			}

			datafield1.add(customer.getPasscode());
			datafield1.add(Integer.toString(customer.getPIN()));
			data.add(datafield1);
			//datafields.clear();

			count++;
			
			
			int j = 0; 
			
			while (j < (customer.getNumChecking() + customer.getNumSavings())-1) {
				
				if(customer.getNumSavings() == 1) {
					datafield2.add("Savings");
					datafield2.add(Integer.toString(customer.getAccounts().get(j).getID()));
					datafield2.add(customer.getAccounts().get(j).getBalance().toString());
					if(customer.getAccounts().get(j).getFees().get(0).type.equalsIgnoreCase("Overdraft")) {
						numFees = 1;
						datafield2.add(Integer.toString(numFees));
					}
					else {
						numFees = 0;
						datafield2.add(Integer.toString(numFees));
					}
					datafield2.add(Integer.toString(customer.getAccounts().get(j).getCardID()));
					stringdate = toStringObject(customer.getAccounts().get(j).getLastTransaction().date);
					datafield1.add(stringdate);
					Date date = customer.getAccounts().get(j).getLastTransaction().date; 
					String dateString = date.toString().replaceAll(":", "/"); 
					datafield2.add(dateString);
					String action = customer.getAccounts().get(j).getLastTransaction().type; 
					datafield2.add(action);
					Money balance = customer.getAccounts().get(j).getLastTransaction().priorBalance; 
					String balance1 = balance.toString();
					datafield2.add(balance1);
					Money change = customer.getAccounts().get(j).getLastTransaction().changeInBalance; 
					String change1 = change.toString(); 
					datafield2.add(change1);
					datafield2.add(customer.getAccounts().get(j).getOpened().toString());
					if(customer.getAccounts().get(j).getClosed() == null) {
						datafield2.add("not closed");
					}
					else {
						datafield2.add(customer.getAccounts().get(j).getClosed().toString());
					}
					data.add(datafield2);
					
					datafield3.add("Fee");
					Date feedate = customer.getAccounts().get(j).getFees().get(0).date; 
					String feedateString = feedate.toString(); 
					datafield3.add(feedateString);
					Money feebalance = customer.getAccounts().get(j).getFees().get(0).amount; 
					String feebalance1 = feebalance.toString(); 
					datafield3.add(feebalance1);
					String feetype = customer.getAccounts().get(j).getFees().get(0).type; 
					datafield3.add(feetype);
					data.add(datafield3);
					j++;
				}
				
				if(customer.getNumChecking() == 1) {
					datafield4.add("Checking");
					datafield4.add(Integer.toString(customer.getAccounts().get(j).getID()));
					datafield4.add(customer.getAccounts().get(j).getBalance().toString());
					if(customer.getAccounts().get(j).getFees().get(0).type == "Overdraft") {
						numFees = 1;
						datafield4.add(Integer.toString(numFees));
					}
					else {
						numFees = 0;
						datafield4.add(Integer.toString(numFees));
					}
					datafield4.add(Integer.toString(customer.getAccounts().get(j).getCardID()));
					Date date = customer.getAccounts().get(j).getLastTransaction().date; 
					String dateString = date.toString().replaceAll(":", "/"); 
					datafield4.add(dateString);
					String action = customer.getAccounts().get(j).getLastTransaction().type; 
					datafield4.add(action);
					Money balance = customer.getAccounts().get(j).getLastTransaction().priorBalance; 
					String balance1 = balance.toString();
					datafield4.add(balance1);
					Money change = customer.getAccounts().get(j).getLastTransaction().changeInBalance; 
					String change1 = change.toString(); 
					datafield4.add(change1);
					datafield4.add(customer.getAccounts().get(j).getOpened().toString());
					if(customer.getAccounts().get(j).getClosed() == null) {
						datafield4.add("not closed");
					}
					else {
						datafield4.add(customer.getAccounts().get(j).getClosed().toString());
					}
					data.add(datafield4);
					
					datafield5.add("Fee");
					Date feedate = customer.getAccounts().get(j).getFees().get(0).date; 
					String feedateString = feedate.toString(); 
					datafield5.add(feedateString);
					Money feebalance = customer.getAccounts().get(j).getFees().get(0).amount; 
					String feebalance1 = feebalance.toString(); 
					datafield5.add(feebalance1);
					String feetype = customer.getAccounts().get(j).getFees().get(0).type; 
					datafield5.add(feetype);
					data.add(datafield5);
					j++;
				}
			}
		}
		try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
			String collect = null;
			int i = 0;
			while(i < data.size()) {
				collect = data.get(i).stream().collect(Collectors.joining(","));
				br.write(collect);
				br.write("\n");
				i++;
			}
			
			
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println(data);

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
	
	public String toStringObject(Date date) {
		String month = Integer.toString(date.getMonth());
		String day = Integer.toString(date.getDay());
		String year = Integer.toString(date.getYear());
		String stringdate = month + "/" + day + "/" + year;
		return stringdate;
		
	}
	 
}