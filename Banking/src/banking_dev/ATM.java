package banking_dev;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ATM {

	private int checkingID = 0;
	private int savingsID = 0;
	private boolean checkingPositive = false;
	private boolean savingsPositive = false;
	private Money localcash;
	private ATMUser user;
	private Socket socketconnection;
	private int sessionID;
	private Message message;
	private boolean GUImessage;
	Scanner sc = new Scanner(System.in);
	
	public ATM() {
		this.localcash = new Money();
		this.message = new Message();
		this.user = new ATMUser();
		this.socketconnection = new Socket();
		this.sessionID = 0;
	}
	
	public ATM(Money localcash, Message message, ATMUser user, Socket socketconnection, int sessionID) {
		this.localcash = localcash;
		this.message = message;
		this.user = user;
		this.socketconnection = socketconnection;
		this.sessionID = sessionID;
	}
	
	

	
	
	
	public boolean login(String ATMID, String ATMpin, ObjectOutputStream objectOutputStream, ObjectInputStream inputStream) throws UnknownHostException, IOException, ClassNotFoundException {
		
		//socketconnection = new Socket("localhost", 1234);
		
		String operation = "Login";
		ATMLogin login = new ATMLogin(Integer.parseInt(ATMID), Integer.parseInt(ATMpin));
		objectOutputStream.writeObject(login);
		ATMLogin atmlogin = (ATMLogin) inputStream.readObject();
		//ATMLogin atmlogin = (ATMLogin) talkToServer(login, operation);
		checkingID = atmlogin.checkingID;
		savingsID = atmlogin.savingsID;
		checkingPositive = atmlogin.checkingPositive;
		savingsPositive = atmlogin.savingsPositive;
		sessionID = atmlogin.sessionID;
		GUImessage = atmlogin.success;
		
		return GUImessage;
	}
	
	
	public boolean deposit(String source, String depositamount, boolean success, ObjectOutputStream objectOutputstream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		Money amount = new Money(Double.parseDouble(depositamount));
		
		ATMDeposit deposit = null;
		if(source.equalsIgnoreCase("Checking")) {
			deposit = new ATMDeposit(sessionID, amount, checkingID);
		}
		
		else if(source.equalsIgnoreCase("Savings")) {
			deposit = new ATMDeposit(sessionID, amount, savingsID);
		}
		objectOutputstream.writeObject(deposit);
		ATMDeposit atmdeposit = (ATMDeposit) inputStream.readObject();
		GUImessage = atmdeposit.success;

		return GUImessage;
	}
	
	public boolean withdrawal(String withdrawalsource, String withdrawalamount, boolean success, ObjectOutputStream objectOutputstream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		Money amount = new Money();
		amount.setDollars(Integer.parseInt(withdrawalamount));
		amount.setCents(0);
		
		ATMWithdrawal withdrawal = null;
		if(withdrawalsource.equalsIgnoreCase("Checking") & checkingPositive == true) {
			withdrawal = new ATMWithdrawal(sessionID, amount,  checkingID);
		}
		else if(withdrawalsource.equalsIgnoreCase("Savings") & savingsPositive == true) {
			withdrawal = new ATMWithdrawal(sessionID, amount,  savingsID);
		}
		else if(withdrawalsource.equalsIgnoreCase("Checking") & checkingPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		else if(withdrawalsource.equalsIgnoreCase("Savings") & savingsPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
			
		objectOutputstream.writeObject(withdrawal);
		ATMWithdrawal atmwithdrawal = (ATMWithdrawal) inputStream.readObject();
		GUImessage = atmwithdrawal.success;

		return GUImessage;
	}
	
	
	public boolean transferFunds(String transfersource, String transfertarget, String transferamount, boolean success, ObjectOutputStream objectOutputstream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		int sourceID = 0;
		
		Money amount = new Money();
		amount.setDollars(Integer.parseInt(transferamount));
		amount.setCents(0);
		
		ATMTransfer transfer = null;
		if(transfersource.equalsIgnoreCase("Checking") & checkingPositive == true) {
			sourceID = user.getCheckingID();
			transfer = new ATMTransfer(sessionID, amount, Integer.parseInt(transfertarget), checkingID);
		}
		else if(transfersource.equalsIgnoreCase("Savings") & savingsPositive == true) {
			sourceID = user.getSavingsID();
			transfer = new ATMTransfer(sessionID, amount, Integer.parseInt(transfertarget), savingsID);
		}
		else if(transfersource.equalsIgnoreCase("Checking") & checkingPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		else if(transfersource.equalsIgnoreCase("Savings") & savingsPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		
			
		objectOutputstream.writeObject(transfer);
		ATMTransfer atmtransfer = (ATMTransfer) inputStream.readObject();
		GUImessage = atmtransfer.success;
		return GUImessage;
		
	}
	
	public Balance viewBalance(String balanceaccount, boolean success, ObjectOutputStream objectOutputstream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		Balance balance = null;
		if(balanceaccount.equalsIgnoreCase("Checking")) {
			balance = new Balance(sessionID, checkingID);
		}
		else if(balanceaccount.equalsIgnoreCase("Savings")) {
			balance = new Balance(sessionID, savingsID);
		}
		
		
		
		objectOutputstream.writeObject(balance);
		Balance atmbalance = (Balance) inputStream.readObject();
	
		return atmbalance;
	}
	
	
	
	
	
	public void dispenseCash(Money money) {
		// I know it needs to be here but I dont really know how it will be implemented
	}
	
	public boolean logout(boolean success, ObjectOutputStream objectOutputstream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		Logout logout = null;
		String operation = "Logout";
		logout = new Logout(sessionID);
		objectOutputstream.writeObject(logout);
		Logout atmlogout = (Logout) inputStream.readObject();
		//Logout atmlogout = (Logout) talkToServer(logout, operation);	
		GUImessage = atmlogout.success;
		
		
		//System.out.println(GUImessage);
		return GUImessage;
	}

}
