import java.util.Date;

public class TestToString {

	public static void main(String[] args) {
		enum Stuff {BOX, CONTROLLER, TV, LAPTOP}
		
		LastTransaction lTrans = new LastTransaction(new Money(34,26, true), new Money(5,00,false), "withdrawal");
		
//		System.out.println(lTrans);
		
		Account acct = new Account(456, AccountType.CHECKING);
		acct.setBalance(lTrans.priorBalance.add(lTrans.changeInBalance));
//		System.out.println(acct);
		
		Fee fee = new Fee(new Date(), new Money(25,00,true), "overdraft");
//		System.out.println(fee);
		
		Customer customer = new Customer(9237, "Aaron Garten");
		
		acct.addFee(fee);
		acct.setLastTransaction(lTrans);
		customer.addAccount(acct);
		
		System.out.println(customer);
		
		Account acct2 = customer.findAccount(456);
		acct2.setBalance(acct2.getBalance().add(new Money(63,50, true)));
		System.out.println(customer);
		
		
//		System.out.println(Stuff.BOX);
	}

}
