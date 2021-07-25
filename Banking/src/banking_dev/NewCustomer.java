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

public class NewCustomer extends Message {
	public Customer customer;
	
	public NewCustomer(Customer customer, int sessionID) {
		super(sessionID, Process.NEW_CUSTOMER);
		this.customer = customer;
	}
	
	//Server use, success
	public NewCustomer(Message m, Customer customer) {
		super(m, true);
		this.customer = customer;
	}
	
	//Server use, fail
	public NewCustomer(Message m, String why) {
		super(m, why);
	}
} 