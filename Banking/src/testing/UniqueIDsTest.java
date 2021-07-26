package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import banking_dev.UniqueIDs;

public class UniqueIDsTest {

	public UniqueIDs uniques;
	
	@Before
	public void setupUniqueIDS( ) {
		uniques = new UniqueIDs();
	}
	
	@Test
	public void addsNewID() {
		assert(uniques.addID(5));
	}
	
	@Test
	public void doesNotAddDuplicate() {
		uniques.addID(5);
		assert(uniques.addID(5));
	}

}
