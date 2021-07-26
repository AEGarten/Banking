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

	JLabel userName, checkingNumber, checkingBalance, savingsNumber, savingsBalance, temp;

	TellerGUI() throws ClassNotFoundException {
		teller = new Teller();
		buildGUI();
	}

	private void buildGUI() {
		frame = new JFrame("Banking");
		frame.setPreferredSize(new Dimension(1000, 500));
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

	private void employeePanel() {
		JPanel employeePanel = new JPanel();
		frame.getContentPane().removeAll();
		;
		frame.add(employeePanel);

		// User Information
		Box box = Box.createVerticalBox();
		userName = new JLabel("Name:");
		box.add(userName);
		checkingNumber = new JLabel("Checking Number:");
		box.add(checkingNumber);
		checkingBalance = new JLabel("Checking Balance:");
		box.add(checkingBalance);
		savingsNumber = new JLabel("Savings Number:");
		box.add(savingsNumber);
		savingsBalance = new JLabel("Savings Balance:");
		box.add(savingsBalance);

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
					update();
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
					update();
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
					update();
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

		// Transfer button
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					transfer();
					update();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(transferButton);
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

		// Logout button
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logout();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(logoutButton);
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
		checkingNumber = new JLabel("Account Number:");
		box.add(checkingNumber);
		checkingBalance = new JLabel("Checking Balance:");
		box.add(checkingBalance);

		supervisorPanel.add(box);
		supervisorPanel.add(Box.createHorizontalStrut(50));
		Box buttonBox = Box.createVerticalBox();

		// Customer Login button
		JButton custLoginButton = new JButton("Customer Login");
		custLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					custLogin();
					update();
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
					update();
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
					update();
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

		// Transfer button
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					transfer();
					update();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonBox.add(transferButton);
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
		supervisorBox.add(addEmployeeButton);
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
		supervisorBox.add(removeEmployeeButton);

		// Logout button
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logout();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		supervisorBox.add(logoutButton);
		supervisorBox.add(Box.createVerticalStrut(10));

		// Add box to panel
		supervisorPanel.add(supervisorBox);
		frame.setVisible(true);
		frame.pack();
	}

	private void login() throws IOException, ClassNotFoundException {
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

	private void custLogin() throws IOException, ClassNotFoundException {
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

	private void newCustomer() throws IOException, ClassNotFoundException {
		String name = JOptionPane.showInputDialog(frame, "Enter Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
		if (name == null)
			return;
		String passcode = JOptionPane.showInputDialog(frame, "Enter Passcode:", "Passcode",
				JOptionPane.INFORMATION_MESSAGE);
		if (passcode == null)
			return;
		String numSavings = JOptionPane.showInputDialog(frame, "Enter Number of Savings:", "Number of Savings",
				JOptionPane.INFORMATION_MESSAGE);
		if (numSavings == null)
			return;
		String numChecking = JOptionPane.showInputDialog(frame, "Enter Number of Checking:", "Number of Checking",
				JOptionPane.INFORMATION_MESSAGE);
		if (numChecking == null)
			return;
		Boolean success = teller.newCustomer(name, passcode, Integer.parseInt(numSavings),
				Integer.parseInt(numChecking));
		if (success) {
			JOptionPane.showMessageDialog(frame, "Customer Successfully Created");
			userName.setText("Name:" + name);
			checkingNumber.setText("Checking Number:");
			checkingBalance.setText("Checking Balance:");
		}
	}

	private void removeCustomer() throws IOException, ClassNotFoundException {
		Boolean success = teller.removeCustomer();
		if (success) {
			JOptionPane.showMessageDialog(frame, "Customer Successfully Deleted");
			userName.setText("Name:");
			checkingNumber.setText("Checking Number:");
			checkingBalance.setText("Checking Balance:");
		}
	}

	private void addSavings() throws IOException, ClassNotFoundException {
		/*
		 * Account newAccount = new Account(0, AccountType.SAVINGS); Boolean success =
		 * teller.addAccount(newAccount); if (success)
		 * JOptionPane.showMessageDialog(frame, "Successfully Added Savings Account");
		 */
	}

	private void addChecking() throws IOException, ClassNotFoundException {
		/*
		 * Account newAccount = new Account(0, AccountType.CHECKING); Boolean success =
		 * teller.addAccount(newAccount); if (success)
		 * JOptionPane.showMessageDialog(frame, "Successfully Added Checking Account");
		 */
	}

	private void removeAccount() throws IOException, ClassNotFoundException {
		String deleteAccount = JOptionPane.showInputDialog(frame, "Enter Account Number:", "Account to Delete",
				JOptionPane.INFORMATION_MESSAGE);
		if (deleteAccount == null)
			return;

		Boolean success = teller.removeAccount(deleteAccount);
		if (success)
			JOptionPane.showMessageDialog(frame, "Successfully Deleted Account:" + deleteAccount);
	}

	private void deposit() throws IOException, ClassNotFoundException {
		String amount = JOptionPane.showInputDialog(frame, "Enter Amount:", "Amount", JOptionPane.INFORMATION_MESSAGE);
		if (amount == null || Integer.parseInt(amount) < 0)
			return;
		String accountNumber = JOptionPane.showInputDialog(frame, "Enter Account Number:", "Account Number",
				JOptionPane.INFORMATION_MESSAGE);
		if (accountNumber == null)
			return;

		Boolean success = teller.deposit(accountNumber, amount);
		if (success)
			// getBalance(accountNumber);
			update();
	}

	private void withdraw() throws IOException, ClassNotFoundException {
		String amount = JOptionPane.showInputDialog(frame, "Enter Amount:", "Amount", JOptionPane.INFORMATION_MESSAGE);
		if (amount == null || Integer.parseInt(amount) < 0)
			return;
		String accountNumber = JOptionPane.showInputDialog(frame, "Enter Account Number:", "Account Number",
				JOptionPane.INFORMATION_MESSAGE);
		if (accountNumber == null)
			return;

		Boolean success = teller.withdraw(accountNumber, amount);

		if (success)
			update();
	}

	private void transfer() throws IOException, ClassNotFoundException {
		String amount = JOptionPane.showInputDialog(frame, "Enter Amount:", "Amount", JOptionPane.INFORMATION_MESSAGE);
		if (amount == null || Integer.parseInt(amount) < 0)
			return;
		String fromAccount = JOptionPane.showInputDialog(frame, "Enter Account Number to Transfer From:",
				"From Account Number", JOptionPane.INFORMATION_MESSAGE);
		if (fromAccount == null)
			return;
		String toAccount = JOptionPane.showInputDialog(frame, "Enter Account Number to Transfer To:",
				"To Account Number", JOptionPane.INFORMATION_MESSAGE);
		if (toAccount == null)
			return;

		Boolean success = teller.transfer(fromAccount, toAccount, amount);

		if (success)
			update();
	}

	private void dismiss() throws IOException, ClassNotFoundException {
		Boolean success = teller.dismiss();
		if (success) {
			JOptionPane.showMessageDialog(frame, "Account session dismissed");
			userName.setText("Name:");
			checkingNumber.setText("Checking Number:");
			checkingBalance.setText("Checking Balance:");
			savingsNumber.setText("Savings Number:");
			savingsBalance.setText("Savings Balance:");
		}
	}

	private void logout() throws IOException, ClassNotFoundException {
		Boolean success = teller.logout();
		if (success) {
			frame.dispose();
			teller.closeConnection();
			TellerGUI newTeller = new TellerGUI();
		}
	}

	private void addEmployee() throws IOException, ClassNotFoundException {
		String name = JOptionPane.showInputDialog(frame, "Enter Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
		String username = JOptionPane.showInputDialog(frame, "Enter username:", "Username",
				JOptionPane.INFORMATION_MESSAGE);
		String password = JOptionPane.showInputDialog(frame, "Enter Password:", "Password",
				JOptionPane.INFORMATION_MESSAGE);
		String employeeType = JOptionPane.showInputDialog(frame, "Enter Employee Type:", "Employee Type",
				JOptionPane.INFORMATION_MESSAGE);
		Boolean success = teller.addEmployee(name, username, password, employeeType);
		if (success)
			JOptionPane.showMessageDialog(frame, "Successfully Added Employe: " + name);
	}

	private void removeEmployee() throws IOException, ClassNotFoundException {
		String name = JOptionPane.showInputDialog(frame, "Enter Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
		String ID = JOptionPane.showInputDialog(frame, "Enter ID:", "ID", JOptionPane.INFORMATION_MESSAGE);
		Boolean success = teller.removeEmployee(name, ID);
		if (success)
			JOptionPane.showMessageDialog(frame, "Successfully Removed Employee: " + name);
	}

	private void update() {
		customer = cust.customer;
		userName.setText("Name: " + customer.getName());
		String[] custData = customer.toString().split("\n");
		String[] splitChecking = custData[1].split(" ");
		String[] splitSavings = custData[2].split(" ");
		checkingNumber.setText("Checking Number: " + splitChecking[0]);
		checkingBalance.setText("Checking Balance: " + splitChecking[2]);
		savingsNumber.setText("Savings Number: " + splitSavings[0]);
		savingsBalance.setText("Savings Balance: " + splitSavings[2]);
	}
}
