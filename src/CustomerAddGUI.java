

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomerAddGUI extends JPanel {
	
	
	private JTextField textCustomerName;
	private JTextField textCustomerAddress;
	private JTextField textCustomerPhone;
	private JLabel labelTitle;
	private JLabel labelCustomerName;
	private JLabel labelCustomerAddress;
	private JLabel labelCustomerPhone;
	private JButton doneButton;
	

	public CustomerAddGUI() {
		
		this.setLayout(new GridLayout(0, 1));
		
		textCustomerName = new JTextField();
		textCustomerAddress = new JTextField();
		textCustomerPhone = new JTextField();
		labelTitle = new JLabel("Add a new customer");
		labelTitle.setFont(new Font("Arial", Font.BOLD, 20));
		labelCustomerName = new JLabel("Name");
		labelCustomerAddress = new JLabel("Address");
		labelCustomerPhone = new JLabel("Phone");
		doneButton = new JButton("Add");
		
		this.add(labelTitle);
		this.add(labelCustomerName);
		this.add(textCustomerName);
		this.add(labelCustomerAddress);
		this.add(textCustomerAddress);
		this.add(labelCustomerPhone);
		this.add(textCustomerPhone);
		this.add(doneButton);
		
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = addCustomer();
				if (returnValue == 0) {
					JOptionPane.showMessageDialog(null,
							"Customer added to list", "New Customer created",
							JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"ID already exists or missing fields", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		this.setVisible(true);
	}
		
	
	public int addCustomer() {
		String name = textCustomerName.getText();
		String address = textCustomerAddress.getText();
		String phone = textCustomerPhone.getText();
		if (customerValidation(name, address, phone)) {
			Customer customer = new Customer(name, address, phone);
			RetailSystem.getInstance().getCustomers().add(customer);
			Customer.saveCustomer();
			return 0;
		} else {
			return 1;
		}
	}

	
	public boolean customerValidation(String name, String address, String phoneNumber){
		boolean correct = true;
		if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()){
			correct=false;
		}
		
		return correct;
	}
	
	
}
