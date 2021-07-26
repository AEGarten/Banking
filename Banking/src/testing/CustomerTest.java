package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;

import banking_dev.*;

import org.junit.Test;

public class CustomerTest {
	public Customer customer;
	public Account account;
	public LastTransaction lTrans;
	public Date date;
	
	@Before
	public void setupCustomer(){
		lTrans = new LastTransaction(
				new Money(12.00), new Money(3.00), 
				"atm deposit");
		
		date = new Date();
		
		account = new Account(511, 
				AccountType.SAVINGS, new Money(1.10), 
				lTrans, date, 
				false, 0);
		
		customer = new Customer(422, "Sam Johnson", "secret", 1, 0, 0, date);
	}
	
	@Test
	public void getSetNameAsExpected() {
		customer.setName("Aaron Garten");
		
		assertEquals("Aaron Garten", customer.getName());
	}
	
	@Test
	public void getSetPasscodeAsExpected() {
		customer.setPasscode("sesame");
		
		assertEquals("sesame", customer.getPasscode());
	}
	
	@Test
	public void getNumSavingsAsExpected() {
		customer.addAccount(account);
		assertEquals(1, customer.getNumSavings());
	}
	
	@Test
	public void getNumCheckingAsExpected() {
		assertEquals(0, customer.getNumChecking());
	}
	
	@Test
	public void getSetOpenedAsExpected() {
		Date newDate = new Date(System.currentTimeMillis() - 24*3600*1000);
		customer.setOpened(newDate);
		
		assertEquals(newDate, customer.getOpened());
	}
	
	@Test
	public void getSetClosedAsExpected() {
		customer.setClosed(date);
		
		assertEquals(date, customer.getClosed());
	}
	
	@Test
	public void getSetPINAsExpected() {
		customer.setPIN(4321);
		
		assertEquals(4321, customer.getPIN());
	}
	
	@Test
	public void getIDAsExpected() {
		assertEquals(422, customer.getID());
	}
	
	@Test
	public void getSetAccountsAsExpected() {
		ArrayList<Account> newAccounts = new ArrayList<>();
		newAccounts.add(new Account(AccountType.SAVINGS, new Money(1.00)));
		newAccounts.add(new Account(AccountType.CHECKING, new Money(0.00)));
		newAccounts.add(new Account(null, null));
		customer.setAccounts(newAccounts);
		
		assertEquals(newAccounts, customer.getAccounts());
	}

	@Test
	public void addAccountIncreasesArrayListSize() {
		int beforeSize = customer.getAccounts().size();
		customer.addAccount(account);
		
		assert(customer.getAccounts().size() > beforeSize);
	}
	
	@Test
	public void findAccountFindsNewAccount() {
		Account newAccount = new Account(244, null, null, lTrans, date, false, 0);
		
		customer.addAccount(account);
		customer.addAccount(newAccount);
		customer.addAccount(new Account(AccountType.CHECKING, new Money(0.00)));
		
		assertEquals(newAccount, customer.findAccount(244));
	}
	
	@Test
	public void findAccountDoesNotFindNonExisting() {
		Account newAccount = new Account(244, null, null, lTrans, date, false, 0);
		customer.addAccount(account);
		customer.addAccount(new Account(AccountType.CHECKING, new Money(0.00)));
		
		assertEquals(null, customer.findAccount(244));
	}
	
	
	@Test
	public void removeNewAccountTrue() {
		Account newAccount = new Account(244, null, null, lTrans, date, false, 0);
		customer.addAccount(account);
		customer.addAccount(newAccount);
		customer.addAccount(new Account(AccountType.CHECKING, new Money(0.00)));
		
		assert(customer.removeAccount(244));
	}
	
	@Test
	public void dontRemoveAccount() {
		Account newAccount = new Account(244, null, null, lTrans, date, false, 0);
		customer.addAccount(account);
		customer.addAccount(new Account(AccountType.CHECKING, new Money(0.00)));
		
		assertFalse(customer.removeAccount(244));
	}
	
}
