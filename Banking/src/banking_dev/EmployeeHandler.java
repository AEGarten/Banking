package banking_dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmployeeHandler {
	public ArrayList<Employee> parse(String filename) throws IOException {
		String fileName = filename;
		ArrayList<Employee> employees = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if(data.length == 5) {
					if (data[4] == "EMPLOYEE") {
						Employee employee = new Employee(data[0], Integer.parseInt(data[1]), data[2], data[3], EmployeeType.EMPLOYEE);
						employees.add(employee);
					}
					else {
						Employee employee = new Employee(data[0], Integer.parseInt(data[1]), data[2], data[3], EmployeeType.SUPERVISOR);
						employees.add(employee);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	public void save(Employee employee) throws IOException {
		String fileName = "BankEmployees.csv";
		String name = employee.getName();
		int ID = employee.getEmployeeID();
		String userName = employee.getLoginusername();
		String passWord = employee.getLoginpwd();
		EmployeeType type = employee.getType();
		String[] data = {name, String.valueOf(ID), userName, passWord, type.toString()};
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
