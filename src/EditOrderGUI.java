import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditOrderGUI extends JFrame implements ActionListener {
	private JPanel panel;
	private Container container;
	private JComboBox<String> orderList;
	private JButton editOrderButton;
	
	private static Order orderToEdit;

	public EditOrderGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 200);
		
		panel = new JPanel();
		container = getContentPane();
		container.add(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new GridLayout(0,1));
		
		orderList = new JComboBox<String>();
		editOrderButton = new JButton("Edit Order");
		
		for(Order order: RetailSystem.getInstance().getOrders()){
			orderList.addItem(order.getOrderID());
		}
		
		panel.add(orderList);
		panel.add(editOrderButton);
		
		editOrderButton.addActionListener(this);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		Object target = event.getSource();
		
		if(target == editOrderButton) {
			
			String orderID = orderList.getSelectedItem().toString();
			boolean orderFound = false;
			
			for(Order order: RetailSystem.getInstance().getOrders()){
				if(orderID.equalsIgnoreCase(order.getOrderID())){
					
					orderFound = true;
					orderToEdit = order;
					OrderEditorGUI editThisOrder = new OrderEditorGUI();
					break;
				}
			}
			if(!orderFound){
				
				JOptionPane.showMessageDialog(this, "No Order With This ID in System!");
			}
		}
	}

	public static Order getOrderToEdit() {
		return orderToEdit;
	}

	public void setOrderToEdit(Order orderToEdit) {
		this.orderToEdit = orderToEdit;
	}
}