package banking_dev;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ATMGUI {
	
	public boolean success = false;
	private static Socket socketconnection;
	
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		try {
			socketconnection = new Socket("71.198.106.118", 1234);
			OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
			String[] credentials = loginGUI();
			ATM atm = new ATM();
			
			boolean successmessage = atm.login(credentials[0], credentials[1], objectOutputStream, inputStream);
			if(successmessage) {
				ATMGUI frameTabel = new ATMGUI(atm, objectOutputStream, inputStream);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ATMGUI(ATM atm, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
		
		 String[] commands = {"Deposit",
				 	"Withdrawal",
				 	"Transfer Funds",
				 	"View Balance",
				 	"Logout"};
		 
		 int choice;
		 
		 try {
			
			do {
				 choice = JOptionPane.showOptionDialog(null,
						 "Welcome ATM User", 
						 "ATM", 
						 JOptionPane.YES_NO_CANCEL_OPTION, 
						 JOptionPane.QUESTION_MESSAGE, 
						 null, 
						 commands,
						 commands[commands.length - 1]);
			 
				 switch (choice) {
				 	case 0: atm = deposit(atm, objectOutputStream, inputStream);break;
				 	case 1: atm = withdrawal(atm, objectOutputStream, inputStream);break;
				 	case 2: atm = transferFunds(atm, objectOutputStream, inputStream);break;
				 	case 3: atm = viewBalance(atm, objectOutputStream, inputStream);break;
				 	case 4: atm = logout(atm, objectOutputStream, inputStream);break;
				 	default:  // do nothing
				 }
				 
			 } while (choice != commands.length-1);
			 System.exit(0);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String[] loginGUI() {
		String loginID = "";
		String Pin = "";
		String[] credentials = new String[2];
		loginID = JOptionPane.showInputDialog("Enter Card ID");
		if (loginID == null) {
			return null;		// dialog was cancelled
		}
		
		Pin = JOptionPane.showInputDialog("Enter Pin");
		if (Pin == null) {
			return null;
		}
		credentials[0] = loginID;
		credentials[1] = Pin;
		return credentials;
	}

	
	public ATM deposit(ATM atm, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		String[] commands = {"Checking",
			 	"Savings"};
	 
		int choice;
		String source = "";
		choice = JOptionPane.showOptionDialog(null, "Checking or Savings", "Deposit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands, commands[commands.length - 1]);
		 switch (choice) {
		 	case 0: source = "Checking";break;
		 	case 1: source = "Savings";break;
		 	default:  // do nothing
		 }
		//source = JOptionPane.showInputDialog("Checking or Savings");
		String amount = JOptionPane.showInputDialog("Deposit Amount: ");
		System.out.println(choice);
		success = atm.deposit(source, amount, success, objectOutputStream, inputStream);
	
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Deposit successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Deposit not successful");
		}
		return atm;
	}
	
	public ATM withdrawal(ATM atm, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
		String[] commands = {"Checking",
		"Savings"};

		int choice;
		String source = "";
		choice = JOptionPane.showOptionDialog(null, "Checking or Savings", "Deposit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands, commands[commands.length - 1]);
		switch (choice) {
		case 0: source = "Checking";break;
		case 1: source = "Savings";break;
		default:  // do nothing
		}
		//String source = JOptionPane.showInputDialog("Checking or Savings");
		String amount = JOptionPane.showInputDialog("Withdrawal Amount: ");
		success = atm.withdrawal(source, amount, success, objectOutputStream, inputStream);
		
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Withdrawal successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Withdrawal not successful, account in overdraft");
		}
		
		
		return atm;
		
	}
	
	public ATM transferFunds(ATM atm, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
		String[] commands = {"Checking",
		"Savings"};

		int choice;
		String source = "";
		choice = JOptionPane.showOptionDialog(null, "Checking or Savings", "Deposit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands, commands[commands.length - 1]);
		switch (choice) {
		case 0: source = "Checking";break;
		case 1: source = "Savings";break;
		default:  // do nothing
		}
		//String source = JOptionPane.showInputDialog("From Checking or Savings?");
		String amount = JOptionPane.showInputDialog("Enter Transfer Amount: ");
		String target = JOptionPane.showInputDialog("Which account would you be transferring to?: ");
		
		success = atm.transferFunds(source, target, amount, success, objectOutputStream, inputStream);
		
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Transfer successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Transfer not successful, account in overdraft");
		}
		return atm;
		
	}
	
	public ATM viewBalance(ATM atm, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
		String[] commands = {"Checking",
		"Savings"};

		int choice;
		String balanceaccount = "";
		choice = JOptionPane.showOptionDialog(null, "Checking or Savings", "Deposit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands, commands[commands.length - 1]);
		switch (choice) {
		case 0: balanceaccount = "Checking";break;
		case 1: balanceaccount = "Savings";break;
		default:  // do nothing
		}
		//String balanceaccount = JOptionPane.showInputDialog("Checking or Savings?");
		Balance balance;
		balance = (Balance) atm.viewBalance(balanceaccount, success, objectOutputStream, inputStream);
		success = balance.success;
		//Money amount = balance.amount;
		String successmessage = "Your account balance is $" + balance.amount;
		if(success == true) {
			JOptionPane.showMessageDialog(null, successmessage);
		}
		return atm;	
	}
	
	public ATM logout(ATM atm, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException{
		//boolean success = false;
		success = atm.logout(success, objectOutputStream, inputStream);
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Logout successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Logout not successful");
		}
		
		return atm;
	}
	
}
