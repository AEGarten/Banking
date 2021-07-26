package testing;
import banking_dev.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddAccountTest {

	@Test
	public void accountUsedToSetAccountIDProperty() {
		Customer customer = new Customer(55, null, null, 0, 0, 0, null);
		AddAccount addAccount = new AddAccount(customer, 0, null);

		assertEquals(55, addAccount.customerID);
	}

}
