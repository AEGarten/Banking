package banking_dev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
	
	private Fee overdraftFee = new Fee(new Date(), new Money(25,00, true), "overdraft");
	
	private UniqueIDs customerIDs = new UniqueIDs();
	private UniqueIDs accountIDs = new UniqueIDs();
	private UniqueIDs employeeIDs = new UniqueIDs();
	
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Employee> employees = new ArrayList<>();
	
	//maps cards to their Customers, more efficient than searching each Customer then each Acct
	private HashMap<Integer, Integer> cardToCustomerTable = new HashMap<>();
	
	//maps employee logins to their id, concurrent because it changes while Sever Onlines
	private ConcurrentHashMap<String, Integer> loginToIDTable = new ConcurrentHashMap<>();
	
	public DataBase() {
		//TODO remove hardcoding after FileManager added
		cardToCustomerTable.put(567890, 4543);
		
		accountIDs.addID(92837);
		accountIDs.addID(92840);
		
		LastTransaction lTrans = new LastTransaction(new Money(1261.00), new Money(-60.40), "teller withdrawal");
		
//		Account check = new Account(, );
		Account check = new Account(92837, AccountType.CHECKING, new Money(23.16), null, new Date(), true, 567890);
		check.addFee(overdraftFee);
		check.addFee(overdraftFee);
		
		Account check2 = new Account(92840, AccountType.CHECKING, new Money(1000.16), null, new Date(), false, 0);
		
		Account sav = new Account(567, AccountType.SAVINGS, new Money(100.01), null, new Date(), true, 567890);
		sav.setBalance(new Money(100, 01, true));
		sav.setAttachedCard(true);
		sav.setCardID(567890);
		sav.addFee(overdraftFee);
		
		Customer cust = new Customer(4543, "Aidan Chartreuse");
		cust.setPIN(1234);
		cust.setPasscode("Passcode");
		cust.addAccount(check);
		cust.addAccount(sav);
		cust.addAccount(check2);
		cust.setNumChecking(2);
		cust.setNumSavings(0);
		addCustomer(cust);
		
		cust = new Customer(
				444, "Sam Johnson", 
				"secret", 2, 
				0, 0, 
				new Date(System.currentTimeMillis() - 7*24*3600_000));
		sav = new Account(
				34, AccountType.SAVINGS, 
				new Money(1200.60), lTrans, 
				new Date(System.currentTimeMillis() - 5*24*3600_000), false, 
				0);
		cust.addAccount(sav);
		lTrans = new LastTransaction(new Money(0.01), new Money(6.00), "teller deposit");
		sav = new Account(
				39, AccountType.SAVINGS, 
				new Money(6.01), lTrans, 
				new Date(System.currentTimeMillis() - 24*3600_000), false, 
				0);
		cust.addAccount(sav);
		addCustomer(cust);
		
		Employee employee = new Employee("C. Smith", 197, "Login", "Password");
		addEmployee(employee);
		
		employee = new Employee("Dave S.", 297, "Better, faster, stronger", "Passw0rd");
		employee.setType(EmployeeType.SUPERVISOR);
		addEmployee(employee);
	}
	
	public Fee getOverdraftFee() {return this.overdraftFee; }
	public void setOverdraftFee(Fee f) { this.overdraftFee = f; }
	
	public int getCustomerFromCard(int cardNum) {
		if (cardToCustomerTable.containsKey(cardNum)) 
			return cardToCustomerTable.get(cardNum);
		
		return 0;
	}
	
	private void addCardToCustomer(int cardNum, int customerID) {
		cardToCustomerTable.put(cardNum, customerID);
	}
	
	
	public synchronized Customer findCustomer(int custID) {
		int foundIndex;
		Customer key = new Customer(custID, null);
			
		foundIndex = Collections.binarySearch(customers, key, 
			(a, b) -> a.getID() - b.getID());		
		return (foundIndex > -1) ? customers.get(foundIndex) : null;
	}
	
	public synchronized boolean setCustomer(Customer c) {
		int toReplace;
		
		toReplace = Collections.binarySearch(customers, c, 
				(a, b) -> a.getID() - b.getID());
		
		if (toReplace > -1) {
			
			//delete it and replace, does not affect sort order
			customers.remove(toReplace);
			customers.add(toReplace, c);
			return true;				//finally executes before return
		}	
		return false;		
	}
	
	public synchronized boolean removeCustomer(int id) {
		Customer toRemove = findCustomer(id);
		if (toRemove == null) return false; //fail to find

		//does not affect sort order
		customers.remove(toRemove); 		
		customerIDs.removeID(toRemove.getID());
		return true;
	}
	
	public synchronized boolean addCustomer(Customer c) {
		boolean success = false;
		
		if (customerIDs.addID(c.getID()) && customers.add(c)) {
			success = true;
			Collections.sort(customers, (a, b) -> a.getID() - b.getID()); //only sort if add successful
		}
		return success;
	}
	
	public synchronized boolean addAccount(Customer c, Account a) {
		//TODO: make installAccount, Customer, Employee. These are used to add new unID'd files to DataBase;
		
		return true;
	}
	
	public synchronized boolean addEmployee(Employee e) {
		boolean success = false;
		
		if ( employeeIDs.addID(e.getEmployeeID()) && 
				employees.add(e) &&
				(loginToIDTable.put(e.getLoginusername(), e.getEmployeeID()) == null) ){
			success = true;
			Collections.sort(employees, (a, b) -> a.getEmployeeID() - b.getEmployeeID()); //only sort if add successful
		}
		return success;
	}
	
	public int employeeLoginToID(String login) {
		return loginToIDTable.get(login);
	}
	
	public synchronized Employee findEmployee(int empID) {
		int foundIndex;
		Employee key = new Employee("", empID, "", "");
			
		foundIndex = Collections.binarySearch(employees, key, 
			(a, b) -> a.getEmployeeID() - b.getEmployeeID() );		
		return (foundIndex > -1) ? employees.get(foundIndex) : null;
	}
	
	public synchronized boolean setEmployee(Employee e) {
		int toReplace;
		
		toReplace = Collections.binarySearch(employees, e, 
				(a, b) -> a.getEmployeeID() - b.getEmployeeID());
		
		if (toReplace > -1) {
			
			//delete it and replace, does not affect sort order
			employees.remove(toReplace);
			employees.add(toReplace, e);
			return true;				//finally executes before return
		}	
		return false;		
	}
	
	public synchronized boolean removeEmployee(int id) {
		Employee toRemove = findEmployee(id);
		if (toRemove == null) return false; //fail to find

		//does not affect sort order
		customers.remove(toRemove); 		
		customerIDs.removeID(toRemove.getEmployeeID());
		return true;
	}
	
	
	
}
