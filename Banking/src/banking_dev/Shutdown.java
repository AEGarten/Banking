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

public class Shutdown extends Message {
	//Teller use
	public Shutdown(int sessionID) {
		super(sessionID, Process.ONLINE);
	}
		
	//server use, success
	public Shutdown(Message m) {
		super(m, true);
	}
	
	//Server use, fail
	public Shutdown(Message m, String why) {
		super(m, why);
	}
}
