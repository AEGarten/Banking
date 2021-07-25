package testing;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import banking_dev.*;

public class testTeller {
	Teller teller;
	@Before
	public void initialize() throws ClassNotFoundException {
		teller = new Teller();
	}
	@Test
	public void testMoney() {
		Money money = new Money(100.05); 
		assertEquals(money, teller.convertMoney(100.05));
	}
	@Test
	public void testAddAccount() throws ClassNotFoundException, IOException {
		Enums enums = new Enums();
		Account account = new Account(0, enums.getAccountTypeChecking());
		assertEquals(true,teller.addAccount(account)); 
	}
	@Test
	public void testRemoveAccount() throws ClassNotFoundException, IOException {
		
	}
}