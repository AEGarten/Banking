package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountTest.class, AddAccountTest.class, ATMLoginTest.class, CustomerTest.class, MoneyTest.class,
		NewCustomerTest.class, NewEmployeeTest.class, TellerDepositTest.class, TellerTransferTest.class,
		TellerWithdrawalTest.class, testATMUser.class, testEmployee.class, testTeller.class, UniqueIDsTest.class })
public class AllTests {

}
