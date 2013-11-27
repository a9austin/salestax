package p2;

public class Item {
	
	private String label;
	private int quantity;
	private double price;
	
	public Item(String _label, int _quantity, double _price)
	{
		label = _label;
		quantity = _quantity;
		price = _price;
		
		for (int i = 1; i < quantity; i++)
		{
			price += price;
		}
	}
	
	// Setters and Getters
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String _label)
	{
		label = _label;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int _quantity)
	{
		quantity = _quantity;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice(double _price)
	{
		price = _price;
	}
	

}
