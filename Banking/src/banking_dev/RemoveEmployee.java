package banking_dev;

import java.util.ArrayList;

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

public class RemoveEmployee extends Message {
	int employeeID;
	String initials;
	ArrayList<Employee> employeeShortList;
	
	//Teller requests short list with matching initials
	public RemoveEmployee(int sessionID, String initials) {
		super(sessionID, Process.REMOVE_EMPLOYEE);
		this.initials = initials;
	}
	
	//server responds with list of employees with matching initials
	public RemoveEmployee(Message m, ArrayList<Employee> employeeShortList) {
		super(m, true);
		this.employeeShortList = employeeShortList;
	}
	
	//Teller responds with employee id to be removed
	public RemoveEmployee(int sessionID, int employeeID) {
		super(sessionID, Process.REMOVE_EMPLOYEE);
		this.employeeID = employeeID;
	}
	
	//Server use, success
	public RemoveEmployee(Message m) {
		super(m, true);
	}
	
	//Server use, fail
	public RemoveEmployee(Message m, String why) {
		super(m, why);
	}
}
