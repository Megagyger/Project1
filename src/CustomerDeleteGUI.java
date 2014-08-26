import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CustomerDeleteGUI extends JPanel {

	private JComboBox customersDropDown;
	private JLabel labelTitleMain;
	private JLabel labelTitle;
	private JButton deleteButton;
	private JButton customerMenuButton;
	private String selectedCustomerID;
	private Customer customerRemove;
	
	public CustomerDeleteGUI() {
		
		this.setLayout(new GridLayout(0, 1));

		labelTitleMain = new JLabel("Delete Customer");
		labelTitleMain.setFont(new Font("Arial", Font.BOLD, 20));
		labelTitle = new JLabel(
				"Please pick the customer you want to delete from the list below");
		customersDropDown = new JComboBox();
		buildCustomersDropDown();
		deleteButton = new JButton("Delete Customer");

		this.add(labelTitle);
		this.add(customersDropDown);
		this.add(deleteButton);
		

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteCustomerButton();
			}

		});
		

	}

	public void deleteCustomerButton() {
		customerRemove = null;
		String selectedCustomerString = customersDropDown.getSelectedItem().toString();
		String[] selectedCustomerArray = selectedCustomerString.split(";");
		String selectedCustomerIDString = selectedCustomerArray[0];
		String[] selectedCustomerIDArray = selectedCustomerIDString.split(":");
		selectedCustomerID = selectedCustomerIDArray[1].trim();
		customerRemove = Customer.retrieveCustomer(selectedCustomerID);
		if (customerRemove != null) {
			customerRemove.setActive(false);
			JOptionPane.showMessageDialog(null, "Customer removed",
					"Customer Deleted", JOptionPane.INFORMATION_MESSAGE);
			Customer.saveCustomer();
			addAndRefresh();
		}

	}

	public void addAndRefresh() {
		this.remove(labelTitle);
		this.remove(customersDropDown);
		this.remove(deleteButton);
		this.remove(customerMenuButton);
		this.add(labelTitle);
		customersDropDown = new JComboBox();
		buildCustomersDropDown();
		this.add(customersDropDown);
		this.add(deleteButton);
		this.revalidate();
		revalidate();
		repaint();
	}
	
	public void buildCustomersDropDown(){
		for (Customer customer : RetailSystem.getInstance().getCustomers()){
			if(!customer.getCustomerID().equals(RetailSystem.getInstance()) && customer.isActive()){
				String string = "ID: " + customer.getCustomerID() + " ; Name: "
						+ customer.getName();
				customersDropDown.addItem(string);
			}
		}
	}
	
	
	}


