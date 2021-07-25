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

public class NewEmployee extends Message {
	Employee employee;
	
	public NewEmployee(Employee employee, int sessionID) {
		super(sessionID, Process.ADD_EMPLOYEE);
		this.employee = employee;
	}
	
	//Server use, success
	public NewEmployee(Message m) {
		super(m, true);
	}
	
	//Server use, fail
	public NewEmployee(Message m, String why) {
		super(m, why);
	}
}
