package banking_dev;

import java.io.Serializable;

public class Employee implements Serializable {

	private String name;
	private int employeeID;
	private String loginusername;
	private String loginpwd;
	private EmployeeType type = EmployeeType.EMPLOYEE;
	
	//Server
	public Employee(String name, int employeeID, String loginusername, String loginpwd) {
		this.name = name;
		this.employeeID = employeeID;
		this.loginusername = loginusername;
		this.loginpwd = loginpwd;
	}
	
	//Supervisor
	public Employee(String name, String loginusername, String loginpwd) {
		this.name = name;
		this.loginusername = loginusername;
		this.loginpwd = loginpwd;
	}
	
	public Employee(String name, String loginusername, String loginpwd, EmployeeType type) {
		this.name = name;
		this.loginusername = loginusername;
		this.loginpwd = loginpwd;
		this.type = type;
	}

	public Employee(String name, int id, String loginusername, String loginpwd, EmployeeType type) {
		this.name = name;
		this.employeeID = id;
		this.loginusername = loginusername;
		this.loginpwd = loginpwd;
		this.type = type;
	}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}


	public int getEmployeeID() {return employeeID;}

	public void setEmployeeID(int employeeID) {this.employeeID = employeeID;}



	public String getLoginpwd() {return loginpwd;}
	public void setLoginpwd(String loginpwd) {this.loginpwd = loginpwd;}

		

	public EmployeeType getType() { return this.type; }
	
	public void setType(EmployeeType type) { this.type = type; }

	
	
	public String getLoginusername() { return loginusername; }

	public void setLoginusername(String loginusername) { this.loginusername = loginusername; }

}
