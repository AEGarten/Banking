package testing;
import banking_dev.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	LastTransaction ltrans;
	Date date;
	Money posMoney;
	Money negMoney;
	Account account;
	
	@Before
	public void setupAccount() {
		ltrans = new LastTransaction(
				new Money(12.00), new Money(3.00), 
				"atm deposit");
		
		date = new Date();
		
		posMoney = new Money(0.01);
		negMoney = new Money(-0.02);
		
		account = new Account(511, 
				AccountType.SAVINGS, posMoney, 
				ltrans, date, 
				false, 0);

	}

	@Test
	public void isPostiveReturnsTrue() {
		assert(account.isPositiveStatus());
	}
	
	@Test
	public void isPostiveReturnsFalse() {
		Account account1 = new Account(AccountType.CHECKING, negMoney);
		
		assertFalse(account1.isPositiveStatus());
	}
	
	@Test
	public void getSetLastTransactionAsExpected() {
		LastTransaction newLtrans = new LastTransaction(posMoney, negMoney, "teller withdrawal");
		account.setLastTransaction(newLtrans);
		
		assertEquals(newLtrans, account.getLastTransaction());
	}
	
	@Test
	public void getSetBalanceAsExpected() {
		Money newBalance = new Money(1.12);
		account.setBalance(newBalance);
		
		assertEquals(newBalance, account.getBalance());
	}
	
	@Test
	public void getSetOpenedDateAsExpected() {
		Date newDate = new Date();
		account.setOpened(newDate);
		
		assertEquals(newDate, account.getOpened());
	}
	
	@Test
	public void getSetClosedDateAsExpected() {
		Date newDate = new Date();
		account.setClosed(newDate);
		
		assertEquals(newDate, account.getClosed());
	}
	
	@Test
	public void getSetAttachedCardAsExpected() {
		account.setAttachedCard(false);
		
		assertEquals(false, account.hasAttachedCard());
	}
	
	@Test
	public void getSetCardIDAsExpected() {
		account.setCardID(456);
		
		assertEquals(456, account.getCardID());
	}
	
	@Test
	public void getSetFeesAsExpected() {
		ArrayList<Fee> fees = new ArrayList<>();
		fees.add(new Fee(date, posMoney, "overdraft"));
		account.setFees(fees);
		
		assertEquals(fees, account.getFees());
	}
	
	@Test
	public void getIDAsExpected() {
		Account newAccount = new Account(512, null, null, null, null, false, 0);
		
		assertEquals(512, newAccount.getID());
	}
	
	@Test
	public void getTypeAsExpected() {
		Account newAccount = new Account(AccountType.SAVINGS, null);
		
		assertEquals(AccountType.SAVINGS, newAccount.getType());
	}
	
	@Test
	public void addsActualFee() {
		Fee fee = new Fee(date, negMoney, null);
		
		String beforeFee = account.details();
		int beforeFeesSize = account.getFees().size();
		account.addFee(fee);
		String afterFee = account.details();
		
		assertFalse(beforeFee.equals(afterFee));
	}
	
	@Test
	public void findFee2() {
		Date oldDate = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		Date olderDate = new Date(System.currentTimeMillis() - 8*24*3600*1000);
		Fee fee = new Fee(date, negMoney, null);
		Fee fee2 = new Fee(oldDate, posMoney, null);
		Fee fee3 = new Fee(olderDate, posMoney, null);
		account.addFee(fee);
		account.addFee(fee2);
		account.addFee(fee3);
		
		assertEquals(fee2, account.findFee(oldDate));
	}
	
	@Test
	public void doesNotFindFee2() {
		Date oldDate = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		Date olderDate = new Date(System.currentTimeMillis() - 8*24*3600*1000);
		Fee fee = new Fee(date, negMoney, null);
		Fee fee2 = new Fee(oldDate, posMoney, null);
		Fee fee3 = new Fee(olderDate, posMoney, null);
		account.addFee(fee);
		account.addFee(fee3);
		
		assertEquals(null, account.findFee(oldDate));
	}
	
	@Test
	public void removesFee2FindNull() {
		Date oldDate = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		Date olderDate = new Date(System.currentTimeMillis() - 8*24*3600*1000);
		Fee fee = new Fee(date, negMoney, null);
		Fee fee2 = new Fee(oldDate, posMoney, null);
		Fee fee3 = new Fee(olderDate, posMoney, null);
		account.addFee(fee);
		account.addFee(fee2);
		account.addFee(fee3);
		
		account.removeFee(fee2);
		
		assertEquals(null, account.findFee(oldDate));
	}
	
	@Test
	public void removeFee2ReturnsTrue() {
		Date oldDate = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		Date olderDate = new Date(System.currentTimeMillis() - 8*24*3600*1000);
		Fee fee = new Fee(date, negMoney, null);
		Fee fee2 = new Fee(oldDate, posMoney, null);
		Fee fee3 = new Fee(olderDate, posMoney, null);
		account.addFee(fee);
		account.addFee(fee2);
		account.addFee(fee3);
		
		assert(account.removeFee(fee2));
	}
	
	@Test
	public void doesNotRemoveCantFindFee() {
		Date oldDate = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		Date olderDate = new Date(System.currentTimeMillis() - 8*24*3600*1000);
		Fee fee = new Fee(date, negMoney, null);
		Fee fee2 = new Fee(oldDate, posMoney, null);
		Fee fee3 = new Fee(olderDate, posMoney, null);
		account.addFee(fee);
		account.addFee(fee3);
		
		assertFalse(account.removeFee(fee2));
	}
	
	@Test
	public void toStringFormat() {
		String out = account.toString();
	
		assertEquals(out, "#511 SAVINGS $0.01");
	}
	
	
}
