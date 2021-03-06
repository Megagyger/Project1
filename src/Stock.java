import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Stock {
	private int units;
	private Product product;
	private boolean active;
	
	public Stock(int units, Product product) {
		this.units = units;
		this.product = product;
		this.active = true;
	}
	public Stock(int units, Product product,boolean active){
		this.units=units;
		this.product = product;
		this.active = active;
	}
	public void viewStockList(){
		for(Stock s: RetailSystem.getInstance().getStocks()){
		System.out.println("Product: "+s.getProduct().getName()+"\n Units: "+s.getUnits());
		}
	}
	public void addStockToList(Product product, int units){
		for(Stock s :RetailSystem.getInstance().getStocks()){
			if(s.getProduct().getProductID()==product.getProductID()){
				s.setUnits(s.getUnits()+units);
				JOptionPane.showMessageDialog(null, units+" units of "+product.getName()+" added to stock");
			}else{
				RetailSystem.getInstance().getStocks().add(new Stock(units,product));
				JOptionPane.showMessageDialog(null,"New product added to stock\nName: "+product.getName()+"\nUnits: "+units);
			}
		}
	}
	public void removeStockFromList(Product product, int units){
		boolean found = false;
		
		for(Stock s:RetailSystem.getInstance().getStocks()){
			if(s.getProduct().getProductID()==product.getProductID()){
				s.setUnits(s.getUnits()-units);
				JOptionPane.showMessageDialog(null, units+" units of "+product.getName()+" removed from stock");
				found = true;
				
			}
		}
		if(!found){
			JOptionPane.showMessageDialog(null,"No such product exists");
		}
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public static void updateStock(Product product, int quantity){
		ArrayList<Stock> stocks = RetailSystem.getInstance().getStocks();
		//ArrayList<LineItem> items;
		
		
			for(Stock stock: stocks){
				
				if(product.getProductID().equalsIgnoreCase(stock.getProduct().getProductID())){
					stock.setUnits(stock.getUnits()-quantity);
				}
				
			}
			
		
		saveStock();
	}
	public static void saveStock(){
		try {
			FileWriter userFile;
			userFile = new FileWriter("RetailSystem/stocks.txt");
			DataBase.writeStocks(RetailSystem.getInstance().getStocks(), userFile);
			userFile.close();// close the stock file
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	public static void increaseStock(Product product, int quantity){
		for(Stock stock : RetailSystem.getInstance().getStocks()) {
			if(stock.getProduct().getProductID().equals(product.getProductID())) {
				stock.setUnits(stock.getUnits()+quantity);
				//order.setActive(false);
				
				Stock.saveStock();
			}
		}
	}
	
	public Stock(Stock other){
		this.units=other.units;
		this.product = other.product;
		this.active = other.active;
	}
	public static ArrayList<Stock> createStockTemp(ArrayList<Stock> stocks){
		ArrayList<Stock> temp = new ArrayList<Stock>(); 
		for(Stock stock : stocks){
			Stock stockTemp = new Stock(stock);
			temp.add(stockTemp);
		}
		return temp;
	}

}
