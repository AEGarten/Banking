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
	private Employee employee;
	private ArrayList<Message> messagesOut = new ArrayList<Message>();
	private Message message;
	private int sessionID;
	private String employeeUsername;
	private String tellerPW;
	private int customerID;
	private String customerPW;
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
		employeeUsername = "";
		customerID = 0;
		customerPW = tellerPW = "";
		tellerConnected = false;
		custConnected = false;
		message = new Message();

		try {
			socket = new Socket("localhost", 1234);
			outputstream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputstream);
			inputstream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputstream);
//			while (true) {
//				makeConnection();
//				accessCustomer();
//			}
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}

//	public void makeConnection() throws IOException, ClassNotFoundException {
//		while (!tellerConnected) {
//			System.out.println("Enter Employee Username: ");
//			employeeUsername = scan.nextLine();
//
//			System.out.println("Enter Employee Password: ");
//			tellerPW = scan.nextLine();
//
////			message.authentication = "Login, Password";
////			message.perform = Process.LOGIN;
////			// Send ID/PW to server for verification
//
//			TellerLogin tLogin = new TellerLogin("Login", "Password");
//			objectOutputStream.writeObject(tLogin);
//
//			inputstream = socket.getInputStream();
//			objectInputStream = new ObjectInputStream(inputstream);
//			// True if connected / False if failed to log in
//			TellerLogin receive = (TellerLogin) objectInputStream.readObject();
//			// connected = (Boolean) objectInputStream.readBoolean();
//
//			sessionID = receive.sessionID;
//			tellerConnected = receive.success;
//		}
//		System.out.println("Teller logged in.");
//	}
//
//	public void accessCustomer() throws IOException, ClassNotFoundException {
//		while (!custConnected) {
//			System.out.println("Enter Customer Account ID: ");
//			customerID = scan.nextInt();
//			// messagesOut.add(new Message(customerID));
//			System.out.println("Enter Customer Passcode: ");
//			customerPW = scan.nextLine();
//
//			CustomerAccess cAccess = new CustomerAccess(sessionID, "Passcode", customerID);
//			objectOutputStream.writeObject(cAccess);
//
//			CustomerAccess receive = (CustomerAccess) objectInputStream.readObject();
//
//			custConnected = receive.success;
//			customer = receive.customer;
//		}
//		System.out.println("Customer logged in.");
//		listen();
//	}

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

	public void listen() throws IOException, ClassNotFoundException {
		String gui = ("Transaction: DEPOSIT," + "	WITHDRAWAL," + "	TRANSFER," + " CLOSE_ACCOUNT,"
				+ "	ADD_ACCOUNT," + " NEW_CUSTOMER," + " CLOSE_CUSTOMER," + " DISMISS,");
		System.out.println(gui);
		String listen = scan.nextLine();
		Boolean exit = false;
		while (!exit) {
			switch (listen) {
			case "DEPOSIT": {
				System.out.println("Accounts: " + customer + "\nChoose Account: ");
				int acc = scan.nextInt();
				System.out.println("Enter Amount: ");
				double depositAmt = Double.parseDouble(scan.nextLine());
				Money money = convertMoney(depositAmt);
				TellerDeposit tDeposit = new TellerDeposit(customer, sessionID, money, acc);
				objectOutputStream.writeObject(tDeposit);

				TellerDeposit tReceive = (TellerDeposit) objectInputStream.readObject();
				Boolean dSuccess = tReceive.success;
				if (dSuccess) {
					System.out.println("Successfully Deposited " + depositAmt);
				}
				System.out.println("Enter next action: " + gui);
				listen = scan.nextLine();
			}
				break;
			case "WITHDRAW": {
				System.out.println("Accounts: " + customer + "\nChoose Account: ");
				int acc = scan.nextInt();
				System.out.println("Enter Amount: ");
				double withdrawAmt = Double.parseDouble(scan.nextLine());
				Money money = convertMoney(withdrawAmt);
				TellerWithdrawal tWithdraw = new TellerWithdrawal(customer, sessionID, money, acc);
				objectOutputStream.writeObject(tWithdraw);

				TellerWithdrawal tReceive = (TellerWithdrawal) objectInputStream.readObject();
				Boolean wSuccess = tReceive.success;
				if (wSuccess) {
					System.out.println("Successfully Withdrew " + withdrawAmt);
				}
				System.out.println("Enter next action: " + gui);
				listen = scan.nextLine();
			}
				break;
			case "TRANSFER": {
				System.out.println("Accounts: " + customer + "\nChoose Account: ");
				int acc = scan.nextInt();
				System.out.println("Enter which account to transfer to: ");
				int tAcc = scan.nextInt();
				System.out.println("Enter Amount: ");
				double transferAmt = Double.parseDouble(scan.nextLine());
				Money money = convertMoney(transferAmt);
				TellerTransfer tTransfer = new TellerTransfer(customer, sessionID, money, tAcc, acc);
				objectOutputStream.writeObject(tTransfer);

				TellerTransfer tReceive = (TellerTransfer) objectInputStream.readObject();
				Boolean tSuccess = tReceive.success;
				if (tSuccess) {
					System.out.println("Successfully withdrew " + transferAmt);
				}
				System.out.println("Enter next action: " + gui);
				listen = scan.nextLine();
			}
				break;
			case "DISMISS": {
				System.out.println("Ending customer");
				customer = null;
				Dismiss dismiss = new Dismiss(sessionID);
				objectOutputStream.writeObject(dismiss);

				Dismiss dReceive = (Dismiss) objectInputStream.readObject();
				Boolean dSuccess = dReceive.success;
				if (dSuccess) {
					System.out.println("Dismissed Successfully");
				}
			}
				break;
			}
		}
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
		TellerTransfer tTransfer = new TellerTransfer(customer, sessionID, money, Integer.parseInt(transferAmount),
				Integer.parseInt(transferAmount));
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