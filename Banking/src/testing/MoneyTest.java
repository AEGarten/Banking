package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import banking_dev.Money;

public class MoneyTest {
	Money money;
	
	@Before
	public void setupMoney() {
		money = new Money(4, 25, true);
	}

	@Test
	public void getSetDollarsAsExpected() {
		money.setDollars(4);
		
		assertEquals(4, money.getDollars());
	}
	
	@Test
	public void getSetCentsAsExpected() {
		money.setCents(12);
		
		assertEquals(12, money.getCents());
	}

	@Test
	public void getFractionAsExpected() {
		Money newMoney = new Money(1, 21, true);
		newMoney = newMoney.mult(1.01);
		
		assertEquals(0.21, newMoney.getFraction(), 0.001);
	}
	
	@Test
	public void getSetIsPositiveAsExpected() {
		money.setIsPositive(false);
		
		assertFalse(money.isPositive());
	}
	
	@Test
	public void sumCorrect() {
		money = money.add(new Money(3,75,true));
		
		assert(money.equals(new Money(8,0,true)));
	}
	
	@Test
	public void sumCorrectWithNegNums() {
		money.setIsPositive(false);
		money = money.add(new Money(3,75,false));
		
		assert(money.equals(new Money(8,0,false)));
	}
	
	@Test
	public void sumCorrectWithPositivePlusNeg() {
		money = money.add(new Money(3,75,false));
		
		assert(money.equals(new Money(0,50,true)));
	}
	
	@Test
	public void differenceCorrect() {
		money = money.sub(new Money(3,75,true));
		
		assert(money.equals(new Money(0,50,true)));
	}
	
	@Test
	public void differenceWithNegNums() {
		money.setIsPositive(false);
		money = money.sub(new Money(3,75,false));
		
		assert(money.equals(new Money(0,50,false)));
	}
	
	@Test
	public void differencePosMinusNeg() {
		money = money.sub(new Money(3,75,false));
		
		assert(money.equals(new Money(8,0,true)));
	}
	
	@Test
	public void differenceNegMinusPos() {
		money.setIsPositive(false);
		money = money.sub(new Money(3,75,true));
		
		assert(money.equals(new Money(8,0,false)));
	}
	
	@Test
	public void multiplyCorrect() {
		money = new Money(3, 33, true);
		money = money.mult(1.116);
		
		assert(new String(money.toString()).equals("3.71"));
	}
	
	@Test
	public void multiplySeveralDoublesCorrect() {
		money = new Money(3, 33, true);
		money = money.mult(1.116);
		money = money.mult(1.116);
		money = money.mult(1.116);
		money = money.mult(1.116);
		money = money.mult(1.116);
		
		assert(money.getDollars() == 5 && 
				money.getCents() == 76 && 
				(Math.abs(money.getFraction()) - 0.4547 < 0.001));
	}
	
	@Test
	public void divCorrectQuotient() {
		money = new Money(3, 33, true);
		money = money.div(1.116);
		
		assert(new String(money.toString()).equals("2.98"));
	}
	
	@Test
	public void divMultipleQuotient() {
		money = new Money(3, 33, true);
		money = money.div(1.116);
		money = money.div(1.116);
		money = money.div(1.116);
		money = money.div(1.116);
		money = money.div(1.116);
		
		assert(money.getDollars() == 1 && 
				money.getCents() == 92 && 
				(Math.abs(money.getFraction()) - 0.3637 < 0.001));
	}
}
