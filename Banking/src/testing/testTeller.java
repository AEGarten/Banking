package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import banking_dev.*;

public class testTeller {
	Teller teller;
	Customer customer;
	DataBase db;
	LastTransaction lTrans;
	Account acc;

	@Before
	public void initialize() throws ClassNotFoundException {
		teller = new Teller();

		db = new DataBase();
		customer = new Customer(444, "Sam Johnson", "secret", 2, 0, 0,
				new Date(System.currentTimeMillis() - 7243600000L));
		lTrans = new LastTransaction(new Money(0.01), new Money(6.00), "teller deposit");
		acc = new Account(34, AccountType.SAVINGS, new Money(1200.60), lTrans,
				new Date(System.currentTimeMillis() - 5243600000L), false, 0);
		customer.addAccount(acc);
		db.addCustomer(customer);
	}

	@Test
	public void testMoney() {
		Money money = new Money(100.05);
		assertEquals(money.toString(), teller.convertMoney(100.05).toString());
	}

	@Test
	public void testLogin() throws ClassNotFoundException, IOException {
		Boolean[] test = { true, true };
		Boolean[] data = teller.login("Login", "Password");
		assertEquals(test, data);
	}

	@Test
	public void testCustLogin() throws ClassNotFoundException, IOException {
		CustomerInfo custInfo = new CustomerInfo(true, customer);
		CustomerInfo retrieve = teller.custLogin("secret", 44);
		assertEquals(custInfo, retrieve);
	}

	@Test
	public void testDeposit() throws ClassNotFoundException, IOException {
		assertEquals(true, teller.deposit("34", "200"));
	}

	@Test
	public void testWithdraw() throws ClassNotFoundException, IOException {
		assertEquals(true, teller.withdraw("34", "125"));
	}

	@Test
	public void testTransfer() throws ClassNotFoundException, IOException {
		assertEquals(true, teller.transfer("34", "39", "97.4"));
	}
}