package banking_dev;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class TestSimpleServer {

	public static void main(String[] args) {

		Message msg;
		Socket socketconnection = null;
		 
		
		int sessionID = 0;
		int checkID = 0;
		int savID = 0;
		boolean checkPos;
		boolean savPos;
		
		Customer cust = null;
		Account acct;
		
		boolean done = false;
		
		msg = new ATMLogin(567890, 1234);				//comment out the Client you are not testing
//		msg = new TellerLogin("Login", "Password");
		Logout logout;
		
		try {
			socketconnection = new Socket("localhost", 1234);
			
			try (
			        ObjectOutputStream toServer = new ObjectOutputStream(socketconnection.getOutputStream());
					ObjectInputStream frServer = new ObjectInputStream(socketconnection.getInputStream());
						){
					System.out.println("connected to Server..");
					
					while (socketconnection != null && !done) {
						System.out.println("Logging in");
						toServer.writeObject(msg);
						
						try { msg = (Message) frServer.readObject(); }
						catch (EOFException e) { continue; }
						
						if (msg.success) {
							System.out.println("success!");
							sessionID = msg.sessionID;
						}
						else {
							System.out.println("fail ");
							System.out.println(msg.why);
							break;
						}
						
						if (msg instanceof ATMLogin) {
							ATMLogin aLogin = (ATMLogin) msg;
							checkID = aLogin.checkingID;
							savID = aLogin.savingsID;
							savPos = aLogin.savingsPositive;
							checkPos = aLogin.checkingPositive;
							
							msg = new ATMTransfer(sessionID, new Money(15,11, true), checkID, savID);
							toServer.writeObject(msg);
							ATMTransfer with = (ATMTransfer) frServer.readObject();
							
							if (with.success) {
								System.out.println("transferred $15.11");
								
								msg = new Balance(sessionID, savID);
								toServer.writeObject(msg);
								
								Balance bal = (Balance) frServer.readObject();
								if (bal.success) System.out.println("savings balance: "+ bal.amount);
								else System.out.println(bal.why);
								
								msg = new Balance(sessionID, checkID);
								toServer.writeObject(msg);
								
								bal = (Balance) frServer.readObject();
								if (bal.success) System.out.println("checking balance: "+ bal.amount);
								else System.out.println(bal.why);
							}
							else System.out.println(with.why);
							
						}
						
						
						if (msg instanceof TellerLogin) {
							TellerLogin tLogin = (TellerLogin) msg;
							sessionID = tLogin.sessionID;
							
							msg = new CustomerAccess(sessionID, "Passcode", 4543);
							toServer.writeObject(msg);
							CustomerAccess custAcc = (CustomerAccess) frServer.readObject();
							if (custAcc.success) {
								cust = custAcc.customer;
//								System.out.print(cust);
							}
							else System.out.println(custAcc.why);
							
							msg = new TellerWithdrawal(cust, sessionID, new Money(24,50,true), 92837);
							toServer.writeObject(msg);
							TellerDeposit tDep = (TellerDeposit) frServer.readObject();
							if (tDep.success) {
								System.out.println("Account #"+ 
							cust.findAccount(92837).getID() +" balance = "+ cust.findAccount(92837).getBalance());
							}
							else System.out.println(custAcc.why);
							
							
						}
						
						logout = new Logout(sessionID);
						toServer.writeObject(logout);
						System.out.println("Logging out");

						try { msg = (Message) frServer.readObject(); }
						catch (EOFException e) { }
						if (msg.success) {
							System.out.println("success!");
							done = true;
						}
					}
					
				}
				catch(IOException e) { e.printStackTrace(); }
				catch(ClassNotFoundException e) { e.printStackTrace(); }
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			if (socketconnection != null)
				try {
					socketconnection.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
		
	}

}
