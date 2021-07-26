package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import banking_dev.*;

public class testATMUser {
	ATMUser user = new ATMUser();
	

	@Test
	public void set_getATMUserID() {
		user.setATMUserID(567890);
		assertEquals(567890, user.getATMUserID()); 
	}
	
	@Test
	public void set_getCheckingID() {
		user.setCheckingID(1435);
		assertEquals(1435, user.getCheckingID()); 
	}
	
	@Test
	public void set_getSavingsID() {
		user.setSavingsID(444);
		assertEquals(444, user.getSavingsID()); 
	}
	
	@Test
	public void set_getATMpin() {
		user.setATMpin(1234);
		assertEquals(1234, user.getATMpin()); 
	}
	
	@Test
	public void set_isCheckingPositive() {
		user.setCheckingPositive(false);
		assertEquals(false, user.isCheckingPositive()); 
	}
	
	@Test
	public void set_isSavingsPositive() {
		user.setCheckingPositive(true);
		assertEquals(true, user.isCheckingPositive()); 
	}
	
	

}
