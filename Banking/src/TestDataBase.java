
public class TestDataBase {

	public static void main(String[] args) {
		
		DataBase db = new DataBase();
		
		Customer cust1 = new Customer(db.getNewCustomerID(), "Aaron Garten");
//		Customer cust2 = new Customer(db.getNewCustomerID(), "Huan Le");
		
		System.out.println(db.addCustomer(cust1));
//		System.out.println(db.addCustomer(cust2));
//		System.out.println(cust2.getID());
		
		Customer cust = db.findCustomer(1);
		System.out.println(cust.getName());
		cust.setName("Huan Le");
		db.setCustomer(cust);
		
		cust = db.findCustomer(1);
		System.out.println(cust.getName());
		
		

	}

}
