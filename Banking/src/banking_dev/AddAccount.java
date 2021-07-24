package banking_dev;

public class AddAccount extends Message {
	public Account account;
	public int customerID;
	
	public AddAccount(Customer customer, int sessionID, Account account) {
		super(sessionID, Process.ADD_ACCOUNT);
		
		this.account = account;
		customerID = customer.getID();
	}
	
	//Server use, success
	public AddAccount(Message m, Account account) {
		super(m, true);
		this.account = account;
	}
	
	//Server use, fail
	public AddAccount(Message m, String why) {
		super(m, why);
	}
}
