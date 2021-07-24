package banking_dev;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
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
	

	
	
	
	public boolean login(String ATMID, String ATMpin) throws UnknownHostException, IOException, ClassNotFoundException {
		
		socketconnection = new Socket("localhost", 1234);
		
		ATMLogin login = new ATMLogin(567890, 1234);
		
		try {
			OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(login);
			
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
			ATMLogin atmlogin = (ATMLogin) inputStream.readObject();
			checkingID = atmlogin.checkingID;
			savingsID = atmlogin.savingsID;
			checkingPositive = atmlogin.checkingPositive;
			savingsPositive = atmlogin.savingsPositive;
			sessionID = atmlogin.sessionID;
			GUImessage = atmlogin.success;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GUImessage;
	}
	
	public boolean deposit(String source, String depositamount, boolean success) throws IOException, ClassNotFoundException {
		Money amount = new Money();
		amount.setDollars(Integer.parseInt(depositamount));
		amount.setCents(0);
		
		ATMDeposit deposit = null;
		if(source.equalsIgnoreCase("Checking")) {
			deposit = new ATMDeposit(sessionID, amount, user.getCheckingID());
		}
		
		else if(source.equalsIgnoreCase("Savings")) {
			deposit = new ATMDeposit(sessionID, amount, user.getSavingsID());
		}
		
		try {
			OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(deposit);
			
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
			ATMDeposit atmdeposit = (ATMDeposit) inputStream.readObject();
			GUImessage = atmdeposit.success;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GUImessage;
	}
	
	public boolean withdrawal(String withdrawalsource, String withdrawalamount, boolean success) throws IOException, ClassNotFoundException {
		Money amount = new Money();
		amount.setDollars(Integer.parseInt(withdrawalamount));
		amount.setCents(0);
		
		ATMWithdrawal withdrawal = null;
		if(withdrawalsource.equalsIgnoreCase("Checking") & checkingPositive == true) {
			withdrawal = new ATMWithdrawal(sessionID, amount,  user.getCheckingID());
		}
		else if(withdrawalsource.equalsIgnoreCase("Savings") & savingsPositive == true) {
			withdrawal = new ATMWithdrawal(sessionID, amount,  user.getSavingsID());
		}
		else if(withdrawalsource.equalsIgnoreCase("Checking") & checkingPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		else if(withdrawalsource.equalsIgnoreCase("Savings") & savingsPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		
		try {
			OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(withdrawal);	
			
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
			ATMWithdrawal atmwithdrawal = (ATMWithdrawal) inputStream.readObject();
			GUImessage = atmwithdrawal.success;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GUImessage;
	}
	
	
	public boolean transferFunds(String transfersource, String transfertarget, String transferamount, boolean success) throws IOException, ClassNotFoundException {
		int sourceID = 0;
		
		Money amount = new Money();
		amount.setDollars(Integer.parseInt(transferamount));
		amount.setCents(0);
		
		ATMTransfer transfer = null;
		if(transfersource.equalsIgnoreCase("Checking") & checkingPositive == true) {
			sourceID = user.getCheckingID();
			transfer = new ATMTransfer(sessionID, amount, Integer.parseInt(transfertarget), sourceID);
		}
		else if(transfersource.equalsIgnoreCase("Savings") & savingsPositive == true) {
			sourceID = user.getSavingsID();
			transfer = new ATMTransfer(sessionID, amount, Integer.parseInt(transfertarget), sourceID);
		}
		else if(transfersource.equalsIgnoreCase("Checking") & checkingPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		else if(transfersource.equalsIgnoreCase("Savings") & savingsPositive == false) {
			GUImessage = false;
			return GUImessage;
		}
		
		
		
		try {
			OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(transfer);
			
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
			ATMTransfer atmtransfer = (ATMTransfer) inputStream.readObject();
			GUImessage = atmtransfer.success;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GUImessage;
		
	}
	
	public Balance viewBalance(String balanceaccount, boolean success) throws IOException, ClassNotFoundException {
		Balance balance = null;
		if(balanceaccount.equalsIgnoreCase("Checking")) {
			balance = new Balance(sessionID, user.getCheckingID());
		}
		else if(balanceaccount.equalsIgnoreCase("Savings")) {
			balance = new Balance(sessionID, user.getSavingsID());
		}
		
		
		Balance atmbalance = null;
		try {
			OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(balance);
			
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
			atmbalance = (Balance) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return atmbalance;
	}
	
	
	
	
	
	public void dispenseCash(Money money) {
		// I know it needs to be here but I dont really know how it will be implemented
	}
	
	public boolean logout(boolean success) throws IOException, ClassNotFoundException {
		Logout logout = null;
		logout = new Logout(sessionID);
		
		try(OutputStream outputStream = socketconnection.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());) {
			
			objectOutputStream.writeObject(logout);
			Logout atmlogout = (Logout) inputStream.readObject();
			GUImessage = atmlogout.success;
		} 
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EOFException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(GUImessage);
		return GUImessage;
	}

}
