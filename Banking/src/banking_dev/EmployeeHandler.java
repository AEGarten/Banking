package banking_dev;
import java.util.*;
import java.io.*;

public class EmployeeHandler {
	public void parse() throws IOException {
		String fileName = "BankEmployees.csv";
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				data.add(line.split(","));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void save(Employee employee) throws IOException {
		String fileName = "BankEmployees.csv";
		String name = employee.getName();
		int ID = employee.getEmployeeID();
		String userName = employee.getLoginusername();
		String passWord = employee.getLoginpwd();
		String[] data = {name, String.valueOf(ID), userName, passWord};
		try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
			StringBuilder sb = new StringBuilder();
			for (String item : data) {
				sb.append(item);
				sb.append(",");
			}
			br.write(sb.toString());
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
