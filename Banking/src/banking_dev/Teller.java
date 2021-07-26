package banking_dev;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Scanner;

import java.util.*;

public class Teller {
	private Socket socket;
	private ArrayList<Message> messagesOut = new ArrayList<Message>();
	private int sessionID;
	private Boolean tellerType;
	private Boolean tellerConnected;
	private Boolean custConnected;
	private Customer customer;
	Scanner scan = new Scanner(System.in);
	OutputStream outputstream;
	ObjectOutputStream objectOutputStream;
	InputStream inputstream;
	ObjectInputStream objectInputStream;

	public Teller() throws ClassNotFoundException {
		sessionID = 0;
		tellerConnected = false;
		custConnected = false;
		try {
			socket = new Socket("localhost", 1234);
			outputstream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputstream);
			inputstream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputstream);
			
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	
	public void closeConnection() throws IOException {
		socket.close();
	}

	public Money convertMoney(double amount) {
		String[] amt = String.valueOf(amount).split("\\.");
		int[] dollars = new int[2];
		dollars[0] = Integer.parseInt(amt[0]);
		dollars[1] = Integer.parseInt(amt[1]);
		Money money = new Money(dollars[0], dollars[1], true);
		return money; 
	}

	public Boolean addAccount(Account newAccount) throws IOException, ClassNotFoundException {
		AddAccount addAccount = new AddAccount(customer, sessionID, newAccount);
		objectOutputStream.writeObject(addAccount);

		AddAccount aReceive = (AddAccount) objectInputStream.readObject();
		if (aReceive.success)
			customer = aReceive.customer;
		return aReceive.success;
	}

	public Boolean removeAccount(String oldAccount) throws IOException, ClassNotFoundException {
		RemoveAccount removeAccount = new RemoveAccount(customer, sessionID,
				customer.findAccount(Integer.parseInt(oldAccount)));
		objectOutputStream.writeObject(removeAccount);

		RemoveAccount rReceive = (RemoveAccount) objectInputStream.readObject();
		if (rReceive.success)
			customer = rReceive.customer;
		return rReceive.success;
	}

	public Boolean[] login(String username, String password) throws IOException, ClassNotFoundException {
		TellerLogin tLogin = new TellerLogin(username, password);
		objectOutputStream.writeObject(tLogin);
		TellerLogin receive = (TellerLogin) objectInputStream.readObject();

		sessionID = receive.sessionID;
		tellerConnected = receive.success;
		tellerType = receive.supervisor;

		Boolean[] arr = { tellerConnected, tellerType };
		return arr;
	}

	public CustomerInfo custLogin(String passcode, int custID) throws IOException, ClassNotFoundException {
		CustomerAccess cAccess = new CustomerAccess(sessionID, passcode, custID);
		objectOutputStream.writeObject(cAccess);
		CustomerAccess receive = (CustomerAccess) objectInputStream.readObject();

		custConnected = receive.success;
		customer = receive.customer;
		CustomerInfo custInfo = new CustomerInfo(receive.success, receive.customer);
		return custInfo;
	}

	public Boolean newCustomer(String name, String passcode, int numSavings, int numChecking)
			throws IOException, ClassNotFoundException {
		Date date = new Date();
		Customer customer = new Customer(0, name, passcode, numSavings, numChecking, 0, date);
		NewCustomer newCustomer = new NewCustomer(customer, sessionID);
		objectOutputStream.writeObject(newCustomer);

		NewCustomer nReceive = (NewCustomer) objectInputStream.readObject();

		return nReceive.success;
	}

	public Boolean removeCustomer() throws IOException, ClassNotFoundException {
		RemoveCustomer removeCustomer = new RemoveCustomer(customer, sessionID);
		objectOutputStream.writeObject(removeCustomer);

		RemoveCustomer rReceive = (RemoveCustomer) objectInputStream.readObject();

		return rReceive.success;
	}

	public Boolean deposit(String accountNumber, String depositAmount) throws IOException, ClassNotFoundException {
		Money money = convertMoney(Double.parseDouble(depositAmount));
		TellerDeposit tDeposit = new TellerDeposit(customer, sessionID, money, Integer.parseInt(accountNumber));
		objectOutputStream.writeObject(tDeposit);

		TellerDeposit tReceive = (TellerDeposit) objectInputStream.readObject();
		return tReceive.success;
	}

	public Boolean withdraw(String accountNumber, String withdrawAmount) throws IOException, ClassNotFoundException {
		Money money = convertMoney(Double.parseDouble(withdrawAmount));
		TellerWithdrawal tWithdraw = new TellerWithdrawal(customer, sessionID, money, Integer.parseInt(accountNumber));
		objectOutputStream.writeObject(tWithdraw);

		TellerWithdrawal tReceive = (TellerWithdrawal) objectInputStream.readObject();
		return tReceive.success;
	}

	public Boolean transfer(String fromAccount, String toAccount, String transferAmount)
			throws IOException, ClassNotFoundException {
		Money money = convertMoney(Double.parseDouble(transferAmount));
		TellerTransfer tTransfer = new TellerTransfer(customer, sessionID, money, Integer.parseInt(toAccount),
				Integer.parseInt(fromAccount));
		objectOutputStream.writeObject(tTransfer);

		TellerTransfer tReceive = (TellerTransfer) objectInputStream.readObject();

		return tReceive.success;
	}

	public Boolean dismiss() throws IOException, ClassNotFoundException {
		Dismiss dismiss = new Dismiss(sessionID);
		objectOutputStream.writeObject(dismiss);

		Dismiss rDismiss = (Dismiss) objectInputStream.readObject();
		return rDismiss.success;
	}

	public Boolean logout() throws IOException, ClassNotFoundException {
		Logout logout = new Logout(sessionID);
		objectOutputStream.writeObject(logout);

		Logout lReceive = (Logout) objectInputStream.readObject();
		return lReceive.success;
	}

	public String getBalance(int accountID) {
		Money balance = customer.findAccount(accountID).getBalance();

		return "" + balance;
	}

	public Boolean addEmployee(String name, String username, String password, String employeeType)
			throws IOException, ClassNotFoundException {
		if (employeeType.toUpperCase() == "EMPLOYEE") {
			Employee nEmployee = new Employee(name, username, password, EmployeeType.EMPLOYEE);
			NewEmployee newEmployee = new NewEmployee(nEmployee, sessionID);
			objectOutputStream.writeObject(newEmployee);
		} else {
			Employee nEmployee = new Employee(name, username, password, EmployeeType.SUPERVISOR);
			NewEmployee newEmployee = new NewEmployee(nEmployee, sessionID);
			objectOutputStream.writeObject(newEmployee);
		}

		NewEmployee eReceive = (NewEmployee) objectInputStream.readObject();

		return eReceive.success;
	}

	public Boolean removeEmployee(String name, String id) throws IOException, ClassNotFoundException {
		String upper = name.toUpperCase();
		String[] initials = upper.split(" ");
		String initial = "";
		for (String i : initials) {
			initial += i;
		}
		RemoveEmployee removeEmployee = new RemoveEmployee(sessionID, initial);
		objectOutputStream.writeObject(removeEmployee);

		RemoveEmployee rReceive = (RemoveEmployee) objectInputStream.readObject();
		if (rReceive.success) {
			RemoveEmployee removeEmployeeID = new RemoveEmployee(sessionID, Integer.parseInt(id));
			objectOutputStream.writeObject(removeEmployeeID);

			RemoveEmployee rReceiveSuccess = (RemoveEmployee) objectInputStream.readObject();
			return rReceiveSuccess.success;
		}
		return false;
	}
}