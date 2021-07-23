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

public class Teller {
	private Socket socket;
	private Employee employee;
	private ArrayList<Message> messagesOut = new ArrayList<Message>();
	private Message message;
	private int sessionID;
	private int tellerID;
	private String tellerPW;
	private int customerID;
	private String customerPW;
	private Boolean tellerConnected;
	private Boolean custConnected;
	private Customer customer;
	private Message customerMsg;
	Scanner scan = new Scanner(System.in);
	OutputStream outputstream;
	ObjectOutputStream objectOutputStream;
	InputStream inputstream;
	ObjectInputStream objectInputStream;

	public Teller() throws ClassNotFoundException {
		sessionID = 0;
		tellerID = 0;
		customerID = 0;
		customerPW = tellerPW = "";
		tellerConnected = false;
		custConnected = false;
		message = new Message();

		try {
			socket = new Socket("localhost", 1234);
			outputstream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputstream);

			while (true) {
				makeConnection();
				accessCustomer();
			}
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public void makeConnection() throws IOException, ClassNotFoundException {
		while (!tellerConnected) {
			System.out.println("Enter Employee Account ID: ");
			tellerID = Integer.parseInt(scan.nextLine());

			System.out.println("Enter Employee Password: ");
			tellerPW = scan.nextLine();

//			message.authentication = "Login, Password";
//			message.perform = Process.LOGIN;
//			// Send ID/PW to server for verification

			TellerLogin tLogin = new TellerLogin("Login", "Password");
			objectOutputStream.writeObject(tLogin);

			inputstream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputstream);
			// True if connected / False if failed to log in
			TellerLogin receive = (TellerLogin) objectInputStream.readObject();
			// connected = (Boolean) objectInputStream.readBoolean();

			sessionID = receive.sessionID;
			tellerConnected = receive.success;
		}
		System.out.println("Teller logged in.");
	}

	public void accessCustomer() throws IOException, ClassNotFoundException {
		while (!custConnected) {
			System.out.println("Enter Customer Account ID: ");
			customerID = scan.nextInt();
			// messagesOut.add(new Message(customerID));
			System.out.println("Enter Customer Passcode: ");
			customerPW = scan.nextLine();

			CustomerAccess cAccess = new CustomerAccess(sessionID, "Passcode", customerID);
			objectOutputStream.writeObject(cAccess);

			// Receive server response
			inputstream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputstream);

			CustomerAccess receive = (CustomerAccess) objectInputStream.readObject();

			custConnected = receive.success;
			customer = receive.customer;
		}
		System.out.println("Customer logged in.");
		listen();
	}

	public void closeConnection() throws IOException {
		socket.close();
	}

	public Money money(double amount) {
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
				Money money = money(depositAmt);
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
				Money money = money(withdrawAmt);
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
				Money money = money(transferAmt);
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
}