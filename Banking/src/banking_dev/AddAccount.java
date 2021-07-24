package banking_dev;

public class AddAccount extends Message {
	public Account account;
	public int customerID;
	public Customer customer;
	
	public AddAccount(Customer customer, int sessionID, Account account) {
		super(sessionID, Process.ADD_ACCOUNT);
		
		this.account = account;
		customerID = customer.getID();
	}
	
	//Server use, success
	public AddAccount(Message m, Customer customer) {
		super(m, true);
		this.customer = customer;
	}
	
	//Server use, fail
	public AddAccount(Message m, String why) {
		super(m, why);
	}
}
