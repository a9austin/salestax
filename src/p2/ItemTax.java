package p2;

import java.text.DecimalFormat;

// (np/100 rounded up to the nearest 0.05)
public class ItemTax extends Item{
	
	private int tax;
	private double salesTax;
	private double taxPrice;
	private boolean imported;
	private boolean taxedGood;

	public ItemTax(String _label, int _quantity, double _price, boolean _taxedgood, boolean _imported) {
		super(_label, _quantity, _price);
		taxPrice = 0; // Set Default to be 0
		imported = _imported;
		taxedGood = _taxedgood;
		
		
		
		// Loop through depending on how much the quantity is
		for (int i = 0; i < _quantity; i++){
			if (imported)
			{
				tax = 5;
				salesTax += add_tax(_price); 
			}
			
			if (taxedGood)
			{
			    tax = 10;
				salesTax += add_tax(_price);
			
			}
			
			if (imported == false && taxedGood == false)
			{
				taxPrice += getPrice();
			}
			else
			{
				DecimalFormat f = new DecimalFormat("#0.000");
				salesTax = Double.parseDouble(f.format(salesTax)); // Format sales tax
				double res = salesTax + _price;
				double result = Double.parseDouble(f.format(res)); // Formats to two decimal places
				taxPrice = result;
			}
		}
	}
	
	
	// Adds tax to the price
	private double add_tax(double price){
		
		double v = (Math.round( ((price * tax) / 100) * 10));
		double tax = v/10;
	    return tax;
	}
	
	public double getSalesTax(){
		return salesTax;
	}
	
	// Setters and Getters
	public double getTaxPrice()
	{
		return taxPrice;
	}
	
	public void setTaxPrice(double _price)
	{
		taxPrice = add_tax(_price);
	}
	
	public int getTax()
	{
		return tax;
	}
	
	public void setTax(int _tax)
	{
		tax = _tax;
	}
	
	public boolean hasTax()
	{
		return taxedGood;
	}
	
	public boolean hasImportTax()
	{
		return imported;
	}

}
