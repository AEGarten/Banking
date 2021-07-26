package banking_dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerFileHandler {
	
	String filename;
	DataBase db;
	
	public CustomerFileHandler(String filename, DataBase db) {
		this.filename = filename;
		this.db = db;
	}
	
	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }
	
	public ArrayList<Customer> parse(){
		ArrayList<Customer> customers = new ArrayList<>();
		int lineNumber = 1;
		
		try (BufferedReader in = 
				new BufferedReader(
						new FileReader(
								new File(filename)) )){
			
			String line;
			line = in.readLine();
			String[] data = line.split(" ");
			int numCustomers = Integer.parseInt(data[1]); 	//if error file corrupt
			Date date;
			boolean ATMCard;
			Customer customer = null;
			Account account = null;
			LastTransaction lTrans;
			Fee fee;
			int numSavings = 0,  numChecking = 0, numFees = 0;
			
			while ((line = in.readLine()) != null) {
				lineNumber += 1;
				data = line.split(",", -1);
//				for (String s: data) System.out.println(s);
//				System.out.println("");
				
				switch (data[0]) {
				
				case "Customer": {
//					data		0	1	2		3		4		5		6		7		8
//					csv: customer, id, name, numSav, numCheck, open, close, passcode, PIN

//					new Customer(id, name, passcode, numSav, numChek, PIN, opened)
					
					customer = new Customer(Integer.parseInt(data[1]),
							data[2], 
							data[7],
							Integer.parseInt(data[3]), 
							Integer.parseInt(data[4]), 
							Integer.parseInt(data[8]), 
							new Date(data[5]));
					
					if (!data[6].equals("null")) customer.setClosed(new Date(data[6]));
					numCustomers += 1;
					numSavings = Integer.parseInt(data[3]);
					numChecking = Integer.parseInt(data[4]);

				} break;
				
				case "SAVINGS": {
//					data	0	1	2		3			4		5			6				7				8			9		10
//					csv: type, id, balance, numFees, cardID, lTrans:date, lTrans:priorBal, lTrans:change, lTrans:type, open, closed

//					new Account(id, type, balance, lastTransaction, opened, attachedCard, cardID)
								
					if (!data[5].equals("null")) {
						lTrans = new LastTransaction(
								new Date(data[5]), 
								new Money(Double.parseDouble(data[6])), 
								new Money(Double.parseDouble(data[7])), 
								data[8]);
					}
					else lTrans = null;
					
					ATMCard = !data[4].equals("0");	//if card id not 0 then ATMCard exists for account
					
					account = new Account(Integer.parseInt(data[1]), 
							AccountType.SAVINGS, 
							new Money(Double.parseDouble(data[2])), 
							lTrans, 
							new Date(data[9]), 
							ATMCard, 
							Integer.parseInt(data[4]));
					
					if (ATMCard) db.cardToCustomerTable.put(account.getCardID(), customer.getID());
					
					if (!data[10].equals("null")) account.setClosed(new Date(data[10]));
					
					numFees = Integer.parseInt(data[3]);
					if (numFees == 0) customer.addAccount(account);
					
					numSavings -= 1;
					if (numSavings == 0 && numChecking == 0 && numFees == 0) {
						customers.add(customer);
						customer = null;
					}

				} break;
				
				case "CHECKING": {
					//see SAVINGS
					
					if (!data[5].equals("null")) {
						lTrans = new LastTransaction(
								new Date(data[5]), 
								new Money(Double.parseDouble(data[6])), 
								new Money(Double.parseDouble(data[7])), 
								data[8]);
					}
					else lTrans = null;
					
					ATMCard = !data[4].equals("0");	//if card id not 0 then ATMCard exists for account
					
					account = new Account(Integer.parseInt(data[1]), 
							AccountType.CHECKING, 
							new Money(Double.parseDouble(data[2])), 
							lTrans, 
							new Date(data[9]), 
							ATMCard, 
							Integer.parseInt(data[4]));
					
					if (ATMCard) db.cardToCustomerTable.put(account.getCardID(), customer.getID());
					
					if (!data[10].equals("null")) account.setClosed(new Date(data[10]));
					
					numFees = Integer.parseInt(data[3]);
					if (numFees == 0) customer.addAccount(account);
					
					numChecking -= 1;
					if (numSavings == 0 && numChecking == 0 && numFees == 0) {
						customers.add(customer);
						customer = null;
					}
					
				} break;
				
				case "Fee": {
//					data		0		1		2		3
//					csv: fee: account, date, amount, type

//					new Fee(date, amount, type)
					fee = new Fee(new Date(data[1]), new Money(Double.parseDouble(data[2])), data[3]);
					account.addFee(fee);
					
					numFees -= 1;
					if (numFees == 0) {
						customer.addAccount(account);
						
						if (numSavings == 0 && numChecking == 0) {
							customers.add(customer);
							customer = null;
						}
					}	
				} break;
				
				default: throw new Exception("invalid object name");	//error
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error reading data file. Line number: " + lineNumber);
			return null;
		}
		return customers;
	}

	public boolean save(ArrayList<Customer> customers) {
		try (FileWriter out = new FileWriter(new File(filename))){ 
			
			out.write("numCustomers "+ customers.size() +" \n");
			
			for (Customer customer: customers) {
				out.write("Customer,"+
						customer.getID() +","+
						customer.getName() +","+
						customer.getNumSavings() +","+
						customer.getNumChecking() +","+
						customer.getOpened() +","+
						customer.getClosed() +","+
						customer.getPasscode() +","+
						customer.getPIN());
				out.write("\n");
				
				for (Account account: customer.getAccounts()) {
					out.write(account.getType() +","+
							account.getID() +","+
							account.getBalance() +","+
							account.getFees().size() +","+
							account.getCardID() +",");
					
					if (account.getLastTransaction() == null) out.write("null,null,null,null,"); //four commas for four nulls
					else {
						out.write(account.getLastTransaction().date +","+
										account.getLastTransaction().priorBalance +","+
										account.getLastTransaction().changeInBalance +","+
										account.getLastTransaction().type +",");
					}
					out.write(account.getOpened() +","+ account.getClosed());
					out.write("\n");
					
					for (Fee fee: account.getFees()) {
						out.write("Fee," +
								fee.date +","+
								fee.amount +","+
								fee.type);
						out.write("\n");
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
