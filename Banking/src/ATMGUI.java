import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class ATMGUI {
	public boolean success = false;
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		
		String[] credentials = loginGUI();
		ATM atm = new ATM();
		
		boolean successmessage = atm.login(credentials[0], credentials[1]);
		if(successmessage) {
			ATMGUI frameTabel = new ATMGUI(atm);
		}
	}
	public ATMGUI(ATM atm) throws IOException, ClassNotFoundException{
		 String[] commands = {"Deposit",
				 	"Withdrawal",
				 	"Transfer Funds",
				 	"View Balance",
				 	"Logout"};
		 
		 int choice;
		 
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
			 	case 0: atm = deposit(atm);break;
			 	case 1: atm = withdrawal(atm);break;
			 	case 2: atm = transferFunds(atm);break;
			 	case 3: atm = viewBalance(atm);break;
			 	case 4: atm = logout(atm);break;
			 	default:  // do nothing
			 }
			 
		 } while (choice != commands.length-1);
		 System.exit(0);
		
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

	
	public ATM deposit(ATM atm) throws IOException, ClassNotFoundException {
		
		String source = JOptionPane.showInputDialog("Checking or Savings");
		String amount = JOptionPane.showInputDialog("Deposit Amount: ");
		boolean success = false;
		success = atm.deposit(source, amount, success);
	
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Deposit successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Deposit not successful");
		}
		return atm;
	}
	
	public ATM withdrawal(ATM atm) throws IOException, ClassNotFoundException{
		String source = JOptionPane.showInputDialog("Checking or Savings");
		String amount = JOptionPane.showInputDialog("Withdrawal Amount: ");
		boolean success = false;
		success = atm.withdrawal(source, amount, success);
		
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Withdrawal successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Withdrawal not successful");
		}
		
		
		return atm;
		
	}
	
	public ATM transferFunds(ATM atm) throws IOException, ClassNotFoundException{
		String source = JOptionPane.showInputDialog("From Checking or Savings?");
		String amount = JOptionPane.showInputDialog("Enter Transfer Amount: ");
		String target = JOptionPane.showInputDialog("Which account would you be transferring to?: ");
		
		success = atm.transferFunds(source, target, amount, success);
		
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Transfer successful");
		}
		else {
			JOptionPane.showMessageDialog(null, "Transfer not successful");
		}
		return atm;
		
	}
	
	public ATM viewBalance(ATM atm) throws IOException, ClassNotFoundException{
		String balanceaccount = JOptionPane.showInputDialog("Checking or Savings?");
		Balance balance;
		balance = (Balance) atm.viewBalance(balanceaccount, success);
		success = balance.success;
		Money amount = balance.amount;
		String successmessage = "Your account balance is $" + amount.getDollars() + amount.getCents() + ".";
		if(success == true) {
			JOptionPane.showMessageDialog(null, successmessage);
		}
		return atm;	
	}
	
	public ATM logout(ATM atm) throws IOException, ClassNotFoundException{
		boolean success = false;
		success = atm.logout(success);
		if(success == true) {
			JOptionPane.showMessageDialog(null, "Logout successful");
		}
		
		return atm;
	}
	
}
