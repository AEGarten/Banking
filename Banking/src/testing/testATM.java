package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.Test;
import banking_dev.*;


public class testATM {
	Money amount;
	Customer customer;
	DataBase db;
	LastTransaction lastT;
	Account account;
	ATM atm = new ATM();
	ATMGUI atmgui;
	
	

	
	
	@Test
	public void login() throws UnknownHostException, ClassNotFoundException, IOException {
		ATMGUI atmgui = new ATMGUI();
		//ATMGUI.main(null);
		boolean test = true;
		boolean data = atm.login("567890", "1234", atmgui.objectOutputStream, atmgui.inputStream);
		assertEquals(test, data); 
	}
	


}
