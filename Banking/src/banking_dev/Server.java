package banking_dev;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	enum ClientType { ATM, TELLER }
	
	private static int idCount = 0;
	private static Random rand = new Random();
	private static int port;
	private static DataBase db = new DataBase();
	private static boolean stopping = false;
	
	//TODO make false when ATM fully functional
	private static boolean online = db.isDataBaseLoaded();		//Server starts up online if file loaded correctly
	
	//for keeping track of clients, what type, re: what Customer, supervisor status; all mapped to session id
	private static ConcurrentHashMap<Integer, ClientInfo> sessionIDs = new ConcurrentHashMap<>();
	
	public Server(int port) throws IOException {
		Server.port = port;
		new Thread(new ConnectionListener()).start();
	}
	
	//keeps track of various info for each client in session
	public static class ClientInfo{
		public int reCustomerID = 0;
		public boolean isSupervisor = false;
	}
	
	public static synchronized void Stop() { 	
		stopping = true;
		System.out.println("Server stopping");
	}
	
	private static synchronized int reserveSessionID(ClientInfo ci) {
		Server.idCount += 1 + rand.nextInt(50);
		sessionIDs.put(Server.idCount, ci);
		return Server.idCount;
	}

	private static class ConnectionListener implements Runnable {
	
		@Override
		public void run() {
			System.out.println("Server running");
			Socket newClient = null;
			
			try (
				ServerSocket serverSocket = new ServerSocket(Server.port);
			){
				serverSocket.setReuseAddress(true);
				while (!stopping) {
					newClient = serverSocket.accept();
					new Thread(new ClientHandler(newClient)).start();
				}
			}
			catch (IOException e) { e.printStackTrace(); }
			finally {
				try { if (newClient != null) newClient.close(); }
				catch(Exception e) {}
			}
				
		}
	}
	
	private static class ClientHandler implements Runnable {
		private final Socket client;
		private boolean validated = false; //has the clients credentials been validated
		int sessionID = 0;	//continues session after closing connection w/o login each time
		ClientInfo clientInfo = new ClientInfo();	//clientType, re: Customer id, isSupervisor
		boolean closeConnection = false;	//close out client connection
		
		private ClientHandler(Socket newClient) { this.client = newClient; }
		
		
		@Override
		public void run() {
			System.out.println("New client connected");
			
			Message msgOut = null;
			Message msgIn = null;
			try (
				ObjectInputStream frClient = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
			){
				while (!closeConnection) {
					msgIn = (Message) frClient.readObject();
					
					//if not validated, attempt login
					if (!validated) {
						System.out.println("validating ...");					

						if (msgIn instanceof ATMLogin) {
							
							System.out.println("ATM type ...");							
							
							msgOut = validateATM((ATMLogin) msgIn);
							toClient.writeObject(msgOut);	//response
							closeConnection = !msgOut.success;
							
							if (msgOut.success) System.out.println("Client "+ sessionID +" logged in");
							continue;

						}
						//if from Teller
						else if (msgIn instanceof TellerLogin) {
							
							System.out.println("Teller type ...");	

							msgOut = validateTeller((TellerLogin) msgIn);								
							toClient.writeObject(msgOut);	//response
							closeConnection = !msgOut.success;
								
							if (msgOut.success) {
								System.out.print("Client "+ sessionID);
								if (clientInfo.isSupervisor) System.out.print(" Supervisor");
								System.out.println(" logged in");
							}
							continue;
						}	
						else if (sessionIDs.contains(msgIn.sessionID)) {
							clientInfo = sessionIDs.get(msgIn.sessionID);
							sessionID = msgIn.sessionID;
							validated = true;
							
							System.out.println("Client "+ sessionID +" revalidated");
							
						}
						else closeConnection = true;	//no login, no valid sessionID, close connection
					}
					if (validated) {
						switch(msgIn.perform) {
						
						case LOGOUT: {
							msgOut = logout((Logout) msgIn);
							toClient.writeObject(msgOut);
							closeConnection = true;
							continue;
						} 
						
	//					case SAVE: {
//							if (!online) {
								//TODO make save operation
//							}
//							else msgOut = Save(msgIn, "Server Online, Save disabled");
//						} break;						
						
//						case ONLINE: {
//							if (db.isDataBaseLoaded()) {	//success
//								online = true;
//								msgOut = new Online(msgIn);
//							}
//							else msgOut = new Online(msgIn, "data base failed to load");	//fail
//						} break;
						
						default: break;
						}
					}
					
					
					//by now should be valid, if not send msgIn back (already has msg.success as false)
					if (validated && online) {
						System.out.println("Client "+ sessionID +" attempting: "+ msgIn.perform);
						switch(msgIn.perform) {
						
						
						
						case CUSTOMER_ACCESS: msgOut = customerAccess((CustomerAccess) msgIn); break;

							
	//					case ADD_ACCOUNT:
	//						break;
	//					case ADD_EMPLOYEE:
	//						break;
						
						case BALANCE: msgOut = balance((Balance) msgIn);  break;
							
	//					case CHANGE_PASSWORD:
	//						break;
	//					case CLOSE_ACCOUNT:
	//						break;
	//					case CLOSE_CUSTOMER:
	//						break;
						case DEPOSIT: {
							if (msgIn instanceof ATMDeposit) msgOut = atmDeposit((ATMDeposit) msgIn);
							else msgOut = tellerDeposit((TellerDeposit) msgIn);
						} break;	
						
						case DISMISS: msgOut = dismiss((Dismiss) msgIn); break;

	//					case DIVIDEND:
	//						break;

	//					case NEW_CUSTOMER:
	//						break;

	//					case REMOVE_EMPLOYEE:
	//						break;

//						case SHUTDOWN: {
//							msgOut = new Shutdown(msgIn, true);
//							online = false;
//						} break;
						
						case TRANSFER: {
							if (msgIn instanceof ATMTransfer) msgOut = atmTransfer((ATMTransfer) msgIn);
							else msgOut = tellerTransfer((TellerTransfer) msgIn);
						} break;
						
						case WITHDRAWAL: {
							if (msgIn instanceof ATMWithdrawal) msgOut = atmWithdrawal((ATMWithdrawal) msgIn);
							else msgOut = tellerWithdrawal((TellerWithdrawal) msgIn);
						} break;	
							
						default: toClient.writeObject(new Message(msgIn, "invalid Process")); break;
							

						}
					}
					
					else msgOut = new Message (msgIn, "Validation failed or Server not Online");
					
					toClient.writeObject(msgOut);
				}

			}
			catch (ClassNotFoundException e) { e.printStackTrace(); }
			catch (EOFException e) { System.out.println("Client disconnected");}
			catch (IOException e) { e.printStackTrace(); }
			finally {
				try { if (client != null) client.close(); } 
				catch (Exception e) { e.printStackTrace(); }
				System.out.println("Thread closed");
			}
			
		}
		
		private TellerLogin validateTeller(TellerLogin in) {
			TellerLogin out = null;
			Employee emp = db.findEmployee(db.employeeLoginToID(in.login));
			
			if (emp == null) {
				out = new TellerLogin(in, "no matching employee login");
				closeConnection = true;
			}
			else if (in.login.equals(emp.getLoginusername()) && in.password.equals(emp.getLoginpwd())) {
				validated = true;
				clientInfo.isSupervisor = (emp.getType() == EmployeeType.SUPERVISOR);  
				sessionID = Server.reserveSessionID(clientInfo);
				
				if (!online && !clientInfo.isSupervisor) {
					out = new TellerLogin(in, "Server not Online, Supervisor access required");
					closeConnection = true;
				}
				else out = new TellerLogin(sessionID, in.id, true, clientInfo.isSupervisor);
			}
			else out = new TellerLogin(in, "invalid password");
			return out;
		}
		
		private ATMLogin validateATM(ATMLogin in) {
			
			if (!online) return new ATMLogin(in, "Server not Online"); //fail out if not online
			
			clientInfo.reCustomerID = db.getCustomerFromCard(in.cardID);
			Customer customer = db.findCustomer(clientInfo.reCustomerID);
			
			//if 0 then could be null, good point to stop instead of synch for db access
			if (clientInfo.reCustomerID <= 0) return new ATMLogin(in, "invalid Card ID");
			
			ATMLogin out;
			if (in.PIN == customer.getPIN()) {
				validated = true;
				sessionID = reserveSessionID(clientInfo);
				
				ArrayList<Account> accts = customer.getAccounts();
				
				//search this Customer's Accounts for ATMPacket values attached to their validated card
				int checkID = 0, savID = 0;
				boolean checkPosStat = false, savPosStat = false;
				boolean gotChecking = false, gotSavings = false;	//slight optimization
				for (Account a: accts) {
					if (a.hasAttachedCard()) {
						
						if (!gotChecking && a.getType() == AccountType.CHECKING) {
							if (a.getCardID() == in.cardID) {
								checkID = a.getID();
								checkPosStat = a.isPositiveStatus();
								
								if (gotSavings) break; //if already got Savings, then done
								gotChecking = true;
							}
						}
						else if (!gotSavings && a.getType() == AccountType.SAVINGS) {
							if (a.getCardID() == in.cardID) {
								savID = a.getID();
								savPosStat = a.isPositiveStatus();
								
								if (gotChecking) break; //if already got Checking, then done
								gotSavings = true;
							}
							
						}
					}
				}
				if (gotChecking || gotSavings) {
					out = new ATMLogin(checkID, savID, checkPosStat, savPosStat, sessionID, in); //success
				}
				else out = new ATMLogin(in, "no attached card"); //fail
				
				return out;
			}
			return new ATMLogin(in, "invalid PIN"); //fail
		}
		
		private Logout logout(Logout in) {
			sessionIDs.remove(sessionID);
			closeConnection = true;
			return new Logout(in);
		}
		
		private CustomerAccess customerAccess(CustomerAccess in) {
			CustomerAccess out;
			in.customer = db.findCustomer(in.customerID);
			
			if (in.customer == null) out = new CustomerAccess(in, "no matching customer");
			if (!in.passcode.equals(in.customer.getPasscode()) ) 
						out = new CustomerAccess(in, "incorrect passcode");
			else {
				out = new CustomerAccess(in.customer, in);
				clientInfo.reCustomerID = in.customerID;
			}
			return out; 
		}
		
		public Dismiss dismiss(Dismiss in) {
			return new Dismiss(in);
		}
		
		private Balance balance(Balance in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new Balance(in, "no matching Customer");
			Account acct = cust.findAccount(in.accountID);
			if (acct == null) return new Balance(in, "no matching Account");
			
			Balance out = new Balance(acct.getBalance(), in);
			return out;

		}
		
		private ATMDeposit atmDeposit(ATMDeposit in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new ATMDeposit(in, "no matching Customer");
			Account acct = cust.findAccount(in.accountID);
			if (acct == null) return new ATMDeposit(in, "no matching Account");
			
			LastTransaction lastTrans = new LastTransaction(acct.getBalance(), in.amount, "ATM deposit");
			acct.setLastTransaction(lastTrans);
			acct.setBalance(acct.getBalance().add(in.amount));
			return new ATMDeposit(in, acct.isPositiveStatus());
		}
		
		private TellerDeposit tellerDeposit(TellerDeposit in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new TellerDeposit(in, "no matching Customer");
			
			cust.removeAccount(in.account.getID());	//remove old account
			cust.addAccount(in.account);			//add updated
			return new TellerDeposit(in);
		}
		
		private ATMWithdrawal atmWithdrawal(ATMWithdrawal in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new ATMWithdrawal(in, "no matching Customer");
			Account acct = cust.findAccount(in.accountID);
			if (acct == null) return new ATMWithdrawal(in, "no matching Account");
			if (!acct.isPositiveStatus()) return new ATMWithdrawal(in, "account balance negative");
			
			in.amount.setIsPositive(false);	//set as negative so lastTransaction shows proper change in balance
			LastTransaction lastTrans = new LastTransaction(acct.getBalance(), in.amount, "ATM withdrawal");
			acct.setLastTransaction(lastTrans);
			acct.setBalance(acct.getBalance().add(in.amount));	//adding negative will subtract
			
			//overdraft fee if account overdrawn at ATM; not allowed at Teller per System Requirements
			if (!acct.isPositiveStatus()) {
				final Fee fee = db.getOverdraftFee(); 
				acct.addFee(new Fee(new Date(), fee.amount, fee.type));
				acct.setBalance(acct.getBalance().sub(fee.amount));
			}
			
			return new ATMWithdrawal(in, acct.isPositiveStatus());
		}
		
		private TellerWithdrawal tellerWithdrawal(TellerWithdrawal in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new TellerWithdrawal(in, "no matching Customer");
			
			//if account already negative balance, fail
			if (!cust.findAccount(in.account.getID()).isPositiveStatus()) {
				return new TellerWithdrawal(in, "account balance negative");
			}
			//As per System Requirements, ATM can overdraw but the Teller can not (involves employee in mistake)
			if (!in.account.isPositiveStatus()) {
				return new TellerWithdrawal(in, "account balance would be overdrawn");
			}
				
			cust.removeAccount(in.account.getID());	//remove old account
			cust.addAccount(in.account);			//add updated
			return new TellerWithdrawal(in);
		}
		
		private TellerTransfer tellerTransfer(TellerTransfer in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new TellerTransfer(in, "no matching Customer");
			
			//if account already negative balance, fail
			if (!cust.findAccount(in.frAccount.getID()).isPositiveStatus()) {
				return new TellerTransfer(in, "account balance negative");
			}
			//As per System Requirements, ATM can overdraw but the Teller can not (involves employee in mistake)
			if (!in.frAccount.isPositiveStatus()) {
				return new TellerTransfer(in, "account balance would be overdrawn");
			}
			//remove old account
			cust.removeAccount(in.toAccount.getID());	
			cust.removeAccount(in.frAccount.getID());
			
			//add updated
			cust.addAccount(in.toAccount);			
			cust.addAccount(in.frAccount);			
			return new TellerTransfer(in);
		}
		
		private ATMTransfer atmTransfer(ATMTransfer in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return new ATMTransfer(in, "no matching Customer");
			Account toAcct = cust.findAccount(in.toAccountID);
			Account frAcct = cust.findAccount(in.frAccountID);
			if (toAcct == null || frAcct == null) return new ATMTransfer(in, "no matching Account");
			
			//only from account needs to be positive as it's the only one subtracted
			if (!frAcct.isPositiveStatus()) return new ATMTransfer(in, "account balance negative");
			
			//first to-account
			LastTransaction toLastTrans = new LastTransaction(
					toAcct.getBalance(), in.amount, "ATM transfer from:" + frAcct.getID());
			toAcct.setLastTransaction(toLastTrans);
			toAcct.setBalance(toAcct.getBalance().add(in.amount));
			
			//then from-account
			in.amount.setIsPositive(false);	//set as negative since subtracting
			LastTransaction frLastTrans = new LastTransaction(
					frAcct.getBalance(), in.amount, "ATM transfer to:" + toAcct.getID());
			frAcct.setLastTransaction(frLastTrans);
			frAcct.setBalance(frAcct.getBalance().add(in.amount));	//add negative will subtract
			
			//overdraft fee if account overdrawn at ATM; not allowed at Teller per System Requirements
			if (!frAcct.isPositiveStatus()) {
				final Fee fee = db.getOverdraftFee(); 
				frAcct.addFee(new Fee(new Date(), fee.amount, fee.type));
				frAcct.setBalance(frAcct.getBalance().sub(fee.amount));
			}
			return new ATMTransfer(in, toAcct.isPositiveStatus(), frAcct.isPositiveStatus());
		}
		
		
			
	}
	
	
	
	
	
}

