package banking_dev;

public class Enums {
	public AccountType getAccountTypeChecking() {
		return AccountType.CHECKING;
	}
	
	public AccountType getAccountTypeSavings() {
		return AccountType.SAVINGS;
	}
}

enum Process {
	DEPOSIT,			//Teller or ATM
	WITHDRAWAL,			//Teller or ATM
	TRANSFER,			//Teller or ATM
	BALANCE,			//ATM only
	CLOSE_ACCOUNT,		//Teller only
	ADD_ACCOUNT,		//Teller only
	NEW_CUSTOMER,		//Teller only
	CLOSE_CUSTOMER,		//Teller only
	ADD_EMPLOYEE,		//Supervisor only
	REMOVE_EMPLOYEE,	//Supervisor only
	CHANGE_PASSWORD,	//Supervisor only
	DIVIDEND,			//Supervisor only
	ONLINE,				//Supervisor only
	SHUTDOWN,			//Supervisor only
	SAVE,				//Supervisor only
	LOGIN,				//Teller or ATM
	LOGOUT, 			//Teller or ATM
	CUSTOMER_ACCESS,	//Teller: when an employee wants Access to a customer
	DISMISS,			//Teller: when a customer is finished with Teller (Customer logout)
	
	EMPLOYEE_ACCESS,	//Supervisor only
	EMPLOYEE_DISMISS	//Supervisor only
}

enum EmployeeType { EMPLOYEE, SUPERVISOR }

enum AccountType { SAVINGS, CHECKING }