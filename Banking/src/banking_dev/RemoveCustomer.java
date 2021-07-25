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

public class RemoveCustomer extends Message {
	public int customerID;
	
	public RemoveCustomer(Customer customer, int sessionID) {
		super(sessionID, Process.CLOSE_CUSTOMER);
		this.customerID = customer.getID();
	}
	
	//Server use, success
	public RemoveCustomer(Message m) {
		super(m, true);
	}
	
	//Server use, fail
	public RemoveCustomer(Message m, String why) {
		super(m, why);
	}
}
