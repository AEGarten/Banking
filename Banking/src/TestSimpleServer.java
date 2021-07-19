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
		 
		int ATMUserID;
		int ATMpin;
		int checkingID;
		int savingsID;
		int sessionID;
		Customer customer;
		boolean checkingPositive;
		boolean savingsPositive;
		boolean done = false;
		
		msg = new ATMLogin(567890, 1234);
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
							checkingID = aLogin.checkingID;
							savingsID = aLogin.savingsID;
							savingsPositive = aLogin.savingsPositive;
							checkingPositive = aLogin.checkingPositive;
							
							
							msg = new ATMDeposit(sessionID, new Money(34,33, true), savingsID);
							toServer.writeObject(msg);
							msg = (Message) frServer.readObject();
							
							if (!(msg instanceof ATMDeposit)) System.out.println("not ATMDeposit");
							
							System.out.println(msg);
						}
						
						
						if (msg instanceof TellerLogin) {
							TellerLogin tLogin = (TellerLogin) msg;
							System.out.println(tLogin.sessionID);
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
