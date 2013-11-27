// Author: Austin Truong
// Sales Tax Problem - ThoughtWorks
package p2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class SalesTax {
	
	private ArrayList<String> items_tax;
	private ArrayList<String> items_nontax;
	
	private ArrayList<ArrayList<ItemTax>> inputs; 
 	
	public static void main(String [] args)
	{
		SalesTax program = new SalesTax();
	}
	
	public SalesTax()
	{
		//itemTaxes = new ArrayList<ItemTax>();
		inputs = new ArrayList<ArrayList<ItemTax>>();
		
		
		setup("items.txt");
		parse("test.txt");
		printRecipt();

	}
	
	private void printRecipt()
	{
		try
		{
			File file = new File("receipt.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("OUTPUT");
			bw.write("\n \n");
			
			for (int i = 0; i < inputs.size(); i++){
				int icount = i+1;
				bw.write("Output " + icount + ": ");
				bw.write("\n");
				for (int j = 0; j < inputs.get(i).size(); j++)
				{
					int quantity = inputs.get(i).get(j).getQuantity();
					String output = Integer.toString(quantity) + " " + inputs.get(i).get(j).getLabel() + ": " +
								    inputs.get(i).get(j).getTaxPrice() + "\n";
					bw.write(output);
				}
				bw.write("Sales Tax: " + computeSalesTax(inputs.get(i)));
				bw.write("\n");
				bw.write("Total: " + computeTotal(inputs.get(i)));
				bw.write("\n");
				bw.write("\n");
				
			}
			
			bw.close();
		}
		catch (IOException exception)
		{
			
		}
	}
	
	private double computeSalesTax(ArrayList<ItemTax> items){
		double sum = 0;
		for (int i = 0; i < items.size(); i++)
		{
			sum += items.get(i).getSalesTax();
		}
		
		DecimalFormat f = new DecimalFormat("#0.000");
		
		double result = Double.parseDouble(f.format(sum)); // Formats to two decimal places
		return result;
	}
	
	private double computeTotal(ArrayList<ItemTax> items)
	{
		double sum = 0;
		for (int i = 0; i < items.size(); i++)
		{
			sum += items.get(i).getTaxPrice();
		}
		
		DecimalFormat f = new DecimalFormat("#0.000");
		
		double result = Double.parseDouble(f.format(sum)); // Formats to two decimal places
		return result;
	}
	
	private boolean checkString(String s)
	{
		String str = s.toLowerCase();
		str = str.replace(" " , ""); // remove all whitespace
		if (str.equals("")){
			return false;
		}

		if (str.equals("input:")){
			return false;
		}
		
		return true;
	}
	
	private void parse(String filename)
	{
		// Import means add extra 5% tax
		BufferedReader br = null;
		try
		{
			String curr;
			br = new BufferedReader(new FileReader(filename));
			ArrayList<ItemTax> items = null;
			// Process
			// - Set quantity and price
			// - Check if the item needs to be taxed
			while ((curr = br.readLine()) != null)
			{
				//Item item = null;
				 // Splits string into array by space
			    String[] splitArray = curr.split("\\s+");
			    String[] origArray = curr.split("");
			    ItemTax item = null;
			    
			    if (checkString(curr) == true){ // Ignore empty lines
				    // Currently skip the first line
				    if (splitArray[0].equals("Input") == true){
				    	if (items != null)
				    	{
				    		inputs.add(items);
				    	}
				    	items = new ArrayList<ItemTax>();
				    }
				    else{ 
					    // The first element in the array will be the quantity
					    int quantity = Integer.parseInt(splitArray[0]);
					    // The last element in the array will be the price
					    double price = Double.parseDouble(splitArray[splitArray.length-1]);
					    
					    // Two Cases
					    // - Tax and no Import Tax
					    // - Tax and Import Tax
				    	String label = getItemLabel(origArray);
		
					    if (checkTax(splitArray) == true)
					    {
					    	// Tax and Import Tax
					    	if (checkImportTax(splitArray) == true)
					    	{
					    		item = new ItemTax(label, quantity, price, true, true);
					    	}
					    	// Tax and No Import Tax
					    	else
					    	{
					    		item = new ItemTax(label, quantity, price, true, false);
					    	}
					    }
					    // Two Cases
					    // - No Tax and No Import Tax
					    // - No Tax and Import Tax
					    else
					    {
					    	// No Tax and Import Tax
					    	if (checkImportTax(splitArray) == true)
					    	{
					    		
					    		item = new ItemTax(label, quantity, price, false, true);
					    	}
					    	// No Tax and No Import Tax
					    	else
					    	{
					    	    item = new ItemTax(label, quantity, price, false, false);
					    	}
					    }
					}
				    if (item != null)
				    {
				    	items.add(item);
				    }
				}
			}
			inputs.add(items); // Add the last input list
		}
		catch (IOException e)
		{
			
		}
		
		
	}
	
	// The purpose of this function is to determine with the given string 
	// to determine if it has tax or not
	private boolean checkTax(String[] arr)
	{
		
		// We can ignore the first and last element
		for (int i = 1; i < arr.length-1; i++){
			if (checkTaxExempt(arr[i]) == true)
			{
				return false;
			}
		}
		return true;
	}
	
	// Gets the item label from a given array
	private String getItemLabel(String[] arr)
	{
		String label = "";
		String curr = "";
		for (int i = 3; i < arr.length; i++){
			if (arr[i].equals(" ") == true)
			{
				if (curr.equals("at"))
				{
				    String lbl = label.substring(0, label.length()-3);

					return lbl;
					
				} 
				else
				{  
					curr = "";
				}
				
			}
			else
			{
				curr += arr[i];
			}
			
			label += arr[i];
		}
		return label;
	}
	
    // The purpose of this function is to determine with the given string 
	// to determine if it has import tax or not
	private boolean checkImportTax(String[] arr)
	{
		// We can ignore the first and last element
		for (int i = 1; i < arr.length-1; i++){
			if (arr[i].equals("imported"))
			{
				return true;
			}
		}
		return false;
	}
	
	// Checks to determine if the item has does not have tax
	private boolean checkTaxExempt(String item)
	{
		for (int i = 0; i < items_nontax.size(); i++){
			if (items_nontax.get(i).equals(item)){
				return true;
			}
		}
		return false;
	}


	
	private void setup(String filename)
	{
		items_tax = new ArrayList<String>();
		items_nontax = new ArrayList<String>();
		BufferedReader br = null;
		try
		{
			String curr;
			br = new BufferedReader(new FileReader(filename));
			
			while ((curr = br.readLine()) != null)
			{
				boolean flag = false;
				String tax_key = "";
				String item = "";
				for (char ch : curr.toCharArray()){
					if (flag == false)
					{
				        if (ch == ',')
				        {
				        	flag = true;
				        }
				        else
				        {
				        	tax_key += ch;
				        }
					}
					else
					{
						item += ch;
					}
			    }
				if (tax_key.equals("T"))
				{
					items_tax.add(item);
				}
				else
				{
					items_nontax.add(item);
				}
			}
		}
		catch(IOException e)
		{
			
		}
		
		
	}

}
