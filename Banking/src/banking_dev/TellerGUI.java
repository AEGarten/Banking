package banking_dev;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class TellerGUI {
	private Teller teller;
	private JFrame frame;
	private Customer customer;
	private CustomerInfo cust;
	// private JPanel panel;

	JLabel userName, accountNumber, balanceDisplay;

	public TellerGUI() throws ClassNotFoundException {
		teller = new Teller();
		buildGUI();
	}

	public TellerGUI(Teller teller) {
		this.teller = teller;
		buildGUI();
	}

	public void buildGUI() {
		frame = new JFrame("Banking");
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel loginPanel = new JPanel();
		frame.add(loginPanel);

		Box loginBox = Box.createVerticalBox();
		loginBox.add(Box.createVerticalStrut(10));
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(20, 20, 80, 80);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		loginBox.add(loginButton);
		loginPanel.add(loginBox);
		frame.setVisible(true);
		frame.pack();
	}

	public void employeePanel() {
		JPanel employeePanel = new JPanel();
		frame.getContentPane().removeAll();
		;
		frame.add(employeePanel);

		// User Information
		Box box = Box.createVerticalBox();
		userName = new JLabel("Name:");
		box.add(userName);
		accountNumber = new JLabel("Account Number:");
		box.add(accountNumber);
		balanceDisplay = new JLabel("Balance:");
		box.add(balanceDisplay);
		employeePanel.add(box);
		employeePanel.add(Box.createHorizontalStrut(50));

		// Employee Box with basic functions
		Box buttonBox = Box.createVerticalBox();

		// Customer Login button
		JButton custLoginButton = new JButton("Customer Login");
		custLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					custLogin();
					customer = cust.customer;
					userName.setText("Name: " + customer.getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(custLoginButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// New Customer button
		JButton newCustomerButton = new JButton("New Customer");
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					newCustomer();
					customer = cust.customer;
					userName.setText("Name: " + customer.getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(newCustomerButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Remove Customer button
		JButton removeCustomerButton = new JButton("Remove Customer");
		removeCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					removeCustomer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(removeCustomerButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Deposit button
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deposit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(depositButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Withdraw button
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					withdraw();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(withdrawButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add new savings account button
		JButton addSavingsButton = new JButton("Add Savings Account");
		addSavingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addSavings();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(addSavingsButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add new checking account button
		JButton addCheckingButton = new JButton("Add Checking Account");
		addCheckingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addChecking();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(addCheckingButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add new checking account button
		JButton removeAccountButton = new JButton("Remove Account");
		removeAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					removeAccount();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(removeAccountButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add Dismiss button
		JButton dismissButton = new JButton("Dismiss");
		dismissButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dismiss();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(dismissButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add box to panel
		employeePanel.add(buttonBox);

		frame.setVisible(true);
		frame.pack();
	}

	public void supervisorPanel() {
		JPanel supervisorPanel = new JPanel();
		frame.getContentPane().removeAll();
		;
		frame.add(supervisorPanel);

		// User Information
		Box box = Box.createVerticalBox();
		userName = new JLabel("Name:");
		box.add(userName);
		accountNumber = new JLabel("Account Number:");
		box.add(accountNumber);
		balanceDisplay = new JLabel("Balance:");
		box.add(balanceDisplay);
		supervisorPanel.add(box);
		supervisorPanel.add(Box.createHorizontalStrut(50));
		Box buttonBox = Box.createVerticalBox();

		// Customer Login button
		JButton custLoginButton = new JButton("Customer Login");
		custLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					custLogin();
					customer = cust.customer;
					userName.setText("Name: " + customer.getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(custLoginButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// New Customer button
		JButton newCustomerButton = new JButton("New Customer");
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					newCustomer();
					customer = cust.customer;
					userName.setText("Name: " + customer.getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(newCustomerButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Remove Customer button
		JButton removeCustomerButton = new JButton("Remove Customer");
		removeCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					removeCustomer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(removeCustomerButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Deposit button
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deposit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(depositButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Withdraw button
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					withdraw();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(withdrawButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add new savings account button
		JButton addSavingsButton = new JButton("Add Savings Account");
		addSavingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addSavings();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(addSavingsButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add new checking account button
		JButton addCheckingButton = new JButton("Add Checking Account");
		addCheckingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addChecking();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(addCheckingButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add new checking account button
		JButton removeAccountButton = new JButton("Remove Account");
		removeAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					removeAccount();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(removeAccountButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add Dismiss button
		JButton dismissButton = new JButton("Dismiss");
		dismissButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dismiss();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(dismissButton);
		buttonBox.add(Box.createVerticalStrut(10));

		// Add box to panel
		supervisorPanel.add(buttonBox);

		// Supervisor additional functions
		Box supervisorBox = Box.createVerticalBox();

		// Add employee button
		JButton addEmployeeButton = new JButton("New Employee");
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addEmployee();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Remove employee button
		JButton removeEmployeeButton = new JButton("Remove Employee");
		removeEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					removeEmployee();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		supervisorPanel.add(supervisorBox);
		frame.setVisible(true);
		frame.pack();
	}

	public void login() throws IOException, ClassNotFoundException {
		Boolean success = false;
		Boolean[] arr = null;
		while (!success) {
			String username = JOptionPane.showInputDialog(frame, "Enter Username:", "Username",
					JOptionPane.INFORMATION_MESSAGE);
			if (username == null)
				return;
			String password = JOptionPane.showInputDialog(frame, "Enter Password:", "Password",
					JOptionPane.INFORMATION_MESSAGE);
			if (password == null)
				return;

			arr = teller.login(username, password);
			success = arr[0];
		}

		if (arr[1])
			supervisorPanel();
		else
			employeePanel();
	}

	public void custLogin() throws IOException, ClassNotFoundException {
		Boolean success = false;
		while (!success) {
			String ID = JOptionPane.showInputDialog(frame, "Enter ID:", "ID", JOptionPane.INFORMATION_MESSAGE);
			if (ID == null)
				return;
			String password = JOptionPane.showInputDialog(frame, "Enter Password:", "Password",
					JOptionPane.INFORMATION_MESSAGE);
			if (password == null)
				return;

			cust = teller.custLogin(password, Integer.parseInt(ID));
			success = cust.bool;
			
		}
	}

	public void newCustomer() throws IOException, ClassNotFoundException {
		String name = JOptionPane.showInputDialog(frame, "Enter Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
		String username = JOptionPane.showInputDialog(frame, "Enter Username:", "Username", JOptionPane.INFORMATION_MESSAGE);
		String password = JOptionPane.showInputDialog(frame, "Enter Password:", "Password",
				JOptionPane.INFORMATION_MESSAGE);
		Boolean success = teller.newCustomer(name, username, password);
		if (success) {
			JOptionPane.showMessageDialog(frame, "Customer Successfully Created");
			userName.setText("Name:" + name);
			accountNumber.setText("Account Number:");
			balanceDisplay.setText("Balance:");
		}
	}

	public void removeCustomer() throws IOException, ClassNotFoundException {
		Boolean success = teller.removeCustomer();
		if (success) {
			JOptionPane.showMessageDialog(frame, "Customer Successfully Deleted");
			userName.setText("Name:");
			accountNumber.setText("Account Number:");
			balanceDisplay.setText("Balance:");
		}
	}

	public void addSavings() throws IOException, ClassNotFoundException {
		// String accountType = JOptionPane.showInputDialog(frame, "Enter Account Type
		// (SAVINGS or CHECKINGS", "Account Type", JOptionPane.INFORMATION_MESSAGE);
		Account newAccount = new Account(0, AccountType.SAVINGS);
		Boolean success = teller.addAccount(newAccount);
		if (success)
			JOptionPane.showMessageDialog(frame, "Successfully Added Savings Account");
	}

	public void addChecking() throws IOException, ClassNotFoundException {
		Account newAccount = new Account(0, AccountType.CHECKING);
		Boolean success = teller.addAccount(newAccount);
		if (success)
			JOptionPane.showMessageDialog(frame, "Successfully Added Checking Account");
	}

	public void removeAccount() throws IOException, ClassNotFoundException {
		String deleteAccount = JOptionPane.showInputDialog(frame, "Enter Account Number:", "Account to Delete",
				JOptionPane.INFORMATION_MESSAGE);
		if (deleteAccount == null)
			return;

		Boolean success = teller.removeAccount(deleteAccount);
		if (success)
			JOptionPane.showMessageDialog(frame, "Successfully Deleted Account:" + deleteAccount);
	}

	public void deposit() throws IOException, ClassNotFoundException {
		String amount = JOptionPane.showInputDialog(frame, "Enter Amount:", "Amount", JOptionPane.INFORMATION_MESSAGE);
		if (amount == null)
			return;
		String accountNumber = JOptionPane.showInputDialog(frame, "Enter Account Number:", "Account Number",
				JOptionPane.INFORMATION_MESSAGE);
		if (accountNumber == null)
			return;

		Boolean success = teller.deposit(accountNumber, amount);
		if (success)
			getBalance(accountNumber);
	}

	public void withdraw() throws IOException, ClassNotFoundException {
		String amount = JOptionPane.showInputDialog(frame, "Enter Amount:", "Amount", JOptionPane.INFORMATION_MESSAGE);
		if (amount == null)
			return;
		String accountNumber = JOptionPane.showInputDialog(frame, "Enter Account Number:", "Account Number",
				JOptionPane.INFORMATION_MESSAGE);
		if (accountNumber == null)
			return;

		Boolean success = teller.withdraw(accountNumber, amount);

		if (success)
			getBalance(accountNumber);
	}

	public void transfer() throws IOException, ClassNotFoundException {
		String amount = JOptionPane.showInputDialog(frame, "Enter Amount:", "Amount", JOptionPane.INFORMATION_MESSAGE);
		if (amount == null)
			return;
		String fromAccount = JOptionPane.showInputDialog(frame, "Enter Account Number to Transfer From:",
				"From Account Number", JOptionPane.INFORMATION_MESSAGE);
		if (fromAccount == null)
			return;
		String toAccount = JOptionPane.showInputDialog(frame, "Enter Account number to Transfer To:",
				"To Account Number", JOptionPane.INFORMATION_MESSAGE);
		if (toAccount == null)
			return;

		Boolean success = teller.transfer(fromAccount, toAccount, amount);

		if (success)
			getBalance(fromAccount);
	}

	public void dismiss() throws IOException, ClassNotFoundException {
		Boolean success = teller.dismiss();
		if (success) {
			JOptionPane.showMessageDialog(frame, "Account session dismissed");
			userName.setText("Name:");
			accountNumber.setText("Account Number:");
			balanceDisplay.setText("Balance:");
		}
	}

	public void logout() throws IOException, ClassNotFoundException {
		Boolean success = teller.logout();
		if (success) {
			frame.dispose();
			buildGUI();
		}
	}

	public void addEmployee() throws IOException, ClassNotFoundException {
		String name = JOptionPane.showInputDialog(frame, "Enter Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
		String username = JOptionPane.showInputDialog(frame, "Enter username:", "Username", JOptionPane.INFORMATION_MESSAGE);
		String password = JOptionPane.showInputDialog(frame, "Enter Password:", "Password",
				JOptionPane.INFORMATION_MESSAGE);
		String employeeType = JOptionPane.showInputDialog(frame, "Enter Employee Type:", "Employee Type",
				JOptionPane.INFORMATION_MESSAGE);
		Boolean success = teller.addEmployee(name, username, password, employeeType);
		if (success) JOptionPane.showMessageDialog(frame, "Successfully Added Employe: " + name);
	}

	public void removeEmployee() throws IOException, ClassNotFoundException {
		String name = JOptionPane.showInputDialog(frame, "Enter Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
		String ID = JOptionPane.showInputDialog(frame, "Enter ID:", "ID", JOptionPane.INFORMATION_MESSAGE);
		Boolean success = teller.removeEmployee(name,ID);
		if (success) JOptionPane.showMessageDialog(frame, "Successfully Removed Employee: " + name);
	}
	
	public void getBalance(String accountNumber) {
		String displayBalance = teller.getBalance(Integer.parseInt(accountNumber));
		balanceDisplay.setText(displayBalance);
		frame.pack();
	}
}
