import java.io.FileWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;

public class Order {

	private String orderID;
	private Date orderDate;
	private Product product;
	private int quantity;
	private Date expectedDeliveryDate;
	private Date dateReceived;
	private boolean received;
	
	private boolean active;
	
	public Order(Date orderDate, 
			Product product, int quantity, 
			Date expectedDeliveryDate) throws ParseException  {
		
		this.orderID = "Order"+(RetailSystem.getInstance().getOrders().size()+1);
		this.orderDate = orderDate;
		this.product = product;
		this.quantity = quantity;
		this.expectedDeliveryDate = expectedDeliveryDate;
		this.dateReceived = expectedDeliveryDate ;
		this.active = true;
	}
	
	public Order(Date orderDate, 
			Product product, int quantity, 
			Date expectedDeliveryDate, Date dateReceived, boolean received) throws ParseException  {

		this.orderID = "Order"+(RetailSystem.getInstance().getOrders().size()+1);
		this.orderDate = orderDate;
		this.product = product;
		this.quantity = quantity;
		this.expectedDeliveryDate = expectedDeliveryDate;
		this.dateReceived = dateReceived;
		this.received = received;
		this.active = true;
	}
	
	public Order(Date orderDate, 
			Product product, int quantity, 
			Date expectedDeliveryDate, Date dateReceived, boolean received, boolean active) throws ParseException  {

		this.orderID = "Order"+(RetailSystem.getInstance().getOrders().size()+1);
		this.orderDate = orderDate;
		this.product = product;
		this.quantity = quantity;
		this.expectedDeliveryDate = expectedDeliveryDate;
		this.dateReceived = dateReceived;
		this.received = received;
		this.active = active;
	}
	
	public Order(String orderID, Date orderDate, 
			Product product, int quantity, 
			Date expectedDeliveryDate, Date dateReceived, boolean received, boolean active) throws ParseException  {

		this.orderID = orderID;
		this.orderDate = orderDate;
		this.product = product;
		this.quantity = quantity;
		this.expectedDeliveryDate = expectedDeliveryDate;
		this.dateReceived = dateReceived;
		this.received = received;
		this.active = active;
	}
	
	/*
	 * 
	 * Method to populate a JComboBox with Products
	 * 
	 * */
	public static JComboBox<String> getProducts(JComboBox<String> comboBoxList) {
		
		JComboBox<String> productList = new JComboBox<String>();
		
		for(Product p : RetailSystem.getInstance().getProducts()) {
				
				if(p.isActive()==true) {
					
					comboBoxList.addItem(p.getProductID());
					
					productList = comboBoxList;
					
				}
			}
		
		return productList;
		
	}
	
	public static Product getProductFromComboBox( String productID ) {
		
		Product product = null;
		
		for( Product p : RetailSystem.getInstance().getProducts() ) {
			
			if( productID.contains(p.getProductID()) ) {
				
				product = p;
				
			}
			
		}
		
		return product;
		
	}
	
	/*
	 * 
	 * Method to populate a JComboBox with Orders
	 * 
	 * */
	public static JComboBox<String> getOrders(JComboBox<String> comboBoxList) {
		
		JComboBox<String> orderList = new JComboBox<String>();
		
		for(Order o : RetailSystem.getInstance().getOrders()) {
					
				comboBoxList.addItem(o.getOrderID());
				
			}
		
		return orderList;
		
	}
	
	/*
	 * 
	 * Method to populate a JComboBox with Orders
	 * 
	 * */
	public static JComboBox<String> getOrdersCheck(JComboBox<String> comboBoxList) {
		
		JComboBox<String> orderList = new JComboBox<String>();
		
		for(Order o : RetailSystem.getInstance().getOrders()) {
			
				if( o.isActive() && !o.isReceived() ) {
					
					comboBoxList.addItem(o.getOrderID());
					
				}
				
			}
		
		return orderList;
		
	}
	
	/*
	 * 
	 * If the ReceivedDate is equal to the ExpectedDeliveryDate
	 * set iReceived to true
	 * 
	 * */
	public static boolean receivedOrder() {
		
		boolean received = false;
		
		for(Order p : RetailSystem.getInstance().getOrders()) {
			
			if(p.dateReceived.equals(p.expectedDeliveryDate)) {
				
				received=true;
			
			}
			
		}
		
		return received;
	
	}
	
	/*
	 * 
	 * AutoGeneratedOrder:
	 * Creates a new Order in the RetailSystem if when CreatingAnInvoice 
	 * and that Invoice causes Stock to be depleted below a minimum Stock amount
	 * 
	 * */
	public static void orderMore() {
			
			for(Stock stock : RetailSystem.getInstance().getStocks()) {
				
				boolean isOK = true;
				
				if(stock.getUnits() < 5) {
					
					for(Order order : RetailSystem.getInstance().getOrders()) {
					
						if(order.getProduct().getProductID().equals(stock.getProduct().getProductID()) && !order.isReceived() && order.isActive()) {
							
							isOK = false;
							
							break;
							
						}
							
					}
					
					if( isOK ) {
						
						try {
						
						//Order newOrder = new Order( new Date(), stock.getProduct(),10, new Date() );
						Order newOrder = new Order( new Date(), stock.getProduct(),10, Order.addDaysToDate(new Date(), 5));
						RetailSystem.getInstance().getOrders().add(newOrder);
						
						saveOrder();
						
					} catch (ParseException e) {
						
						e.printStackTrace();
					
					}
						
				}	
			
			}		
		
		}
	
	}
	
	public static String calculateOrderCost( double cost, int quantity ) {
		
		double orderCost = 0;
		
		orderCost = cost * quantity;
		
		String s = "�"+orderCost;
		
		return s;
		
	}
	
	/*
	 * 
	 * Check if an Order has already been created for a Product - 
	 * if that Product has been already Ordered and the Order has not been received 
	 * another Order should not be allowed
	 * 
	 * */
	
	public static boolean checkForOrders( String productName ) {
		
		boolean canOrder = true;
		
		ArrayList<Object> obj = new ArrayList<Object>();
			
		for(Order o : RetailSystem.getInstance().getOrders()) {
			
			if( o.getProduct().getName().contains(productName) && !o.isReceived() && o.isActive() ) {
				
				obj.add(o.getProduct().getName());
				
			}
			
			if( obj.size() >= 1 ) {
				
				canOrder = false;
				
			}
			
		}
		
		return canOrder;
		
	}
	
	public static void saveOrder() {
		
	  	 try {
	  		 
	  		 FileWriter orderFile;
	  		 
	  		orderFile = new FileWriter("RetailSystem/orders.txt");
	  		
	  		DataBase.writeOrders(RetailSystem.getInstance().getOrders(), orderFile);
	  		
	  		orderFile.close();
	  		
	  	 } catch (Exception exception) {
	  		 
	  		 exception.printStackTrace();
	  		 
	  	 }
	  	 
	}
	
	public Date checkOverdue() {
		return expectedDeliveryDate;
	}
	
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void displayOrder() {
		System.out.println(orderID 
				+ " | " + orderDate 
				+ " | " + product 
				+ " | " + quantity 
				+ " | " + expectedDeliveryDate 
				+ " | " + dateReceived 
				+ " | " + received);
	}
	
	public static Date addDaysToDate(Date date, int days) {
		
		Date newDate = new Date();
		
		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(date);
		
		calendar.add(Calendar.DATE, days);
		
		newDate = calendar.getTime();
		
		return newDate;
		
	}
	
	public static boolean validateDatePattern(String date) {
		
		boolean isMatch = false;
		
		Pattern pattern = Pattern.compile(
				"\\b"
				+ "(\\d{2})"
				+ "-"
				+ "(\\bJan\\b|\\bFeb\\b|\\bMar\\b|\\bApr\\b|\\bMay\\b|\\bJun\\b|\\bJul\\b|\\bAug\\b|\\bSep\\b|\\bOct\\b|\\bNov\\b|\\bDec\\b)"
				+ "-"
				+ "(\\d{4})"
				+ "*$");
		
		Matcher matchr = pattern.matcher(date);
		
		if(matchr.find()) {
			
			int d = Integer.parseInt(matchr.group(1));
			
			int y = Integer.parseInt(matchr.group(3));
			
			if( (d < 32 && d > 00) && (y < 3000 && y > 1899) ) {
				
				isMatch = true;
				
			}
			
			} else {
				
				isMatch = false;
				
			}
			
			return isMatch;
		
	}
	
}