package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import banking_dev.*;

public class testEmployee {
	EmployeeType employeetype = null;
	
	@Test
	public void constructor1() {
		Employee employee = new Employee("Dolu", "dolu123", "doluodumosu");
		assertEquals("Dolu", employee.getName());
		assertEquals("dolu123", employee.getLoginusername());
		assertEquals("doluodumosu", employee.getLoginpwd());
	}
	
	@Test
	public void constructor2() {
		Employee employee = new Employee("Dolu", 123, "dolu123", "doluodumosu", EmployeeType.EMPLOYEE);
		assertEquals("Dolu", employee.getName());
		assertEquals(123, employee.getEmployeeID());
		assertEquals("dolu123", employee.getLoginusername());
		assertEquals("doluodumosu", employee.getLoginpwd());
		assertEquals(employeetype.EMPLOYEE, employee.getType());
	}
	
	@Test
	public void set_getName() {
		Employee employee = new Employee("Dolu", 123, "dolu123", "doluodumosu", EmployeeType.EMPLOYEE);
		employee.setName("Dolu");
		assertEquals("Dolu", employee.getName()); 
	}
	
	@Test
	public void set_getEmployeeID() {
		Employee employee = new Employee("Dolu", 123, "dolu123", "doluodumosu", EmployeeType.EMPLOYEE);
		employee.setEmployeeID(1234);
		assertEquals(1234, employee.getEmployeeID()); 
	}
	
	@Test
	public void set_getLoginpwd() {
		Employee employee = new Employee("Dolu", 123, "dolu123", "doluodumosu", EmployeeType.EMPLOYEE);
		employee.setLoginpwd("doluodumosu");
		assertEquals("doluodumosu", employee.getLoginpwd()); 
	}
	
	@Test
	public void set_getType() {
		Employee employee = new Employee("Dolu", 123, "dolu123", "doluodumosu", EmployeeType.EMPLOYEE);
		employee.setType(EmployeeType.SUPERVISOR);
		assertEquals(EmployeeType.SUPERVISOR, employee.getType()); 
	}
	
	@Test
	public void set_getLoginUsername() {
		Employee employee = new Employee("Dolu", 123, "dolu123", "doluodumosu", EmployeeType.EMPLOYEE);
		employee.setLoginusername("dolu123");
		assertEquals("dolu123", employee.getLoginusername()); 
	}

}
