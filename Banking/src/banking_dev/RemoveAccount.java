package banking_dev;

/*
 * Message //parent
 * -------------------------
 * +perform: Process enum
 * +success: boolean = false
 * +id: int = auto					//auto generated from date
 * +sessionID: int = 0				//continued login
 * +why: String	= ""				//why fail
 * -------------------------
 * +Message()
 * +Message(sessionID, perform) 	//subclass use
 * +Message(sessionID, id, success)	//server: detailed success
 * +Message(Message, success)		//server: simple success
 * +Message(Message, why)			//server: fail
 */

public class RemoveAccount extends Message {
	public int customerID;
	public int accountID;
	public Customer customer;
	
	public RemoveAccount(Customer customer, int sessionID, Account acct) {
		super(sessionID, Process.CLOSE_ACCOUNT);
		customerID = customer.getID();
		accountID = acct.getID();
	}
	
	//Server use, success
	public RemoveAccount(Message m, Customer c) {
		super(m, true);
		customer = c;
	}
	
	//Server use, fail
	public RemoveAccount(Message m, String why) {
		super(m, why);
	}
}
