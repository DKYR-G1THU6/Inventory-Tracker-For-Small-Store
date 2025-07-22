public class Item {
	private String name;
	private String itemType;
	private double sellCost;
	private double buyCost;
	private int quantity;
	private String location;
	private String itemId;
	private String brand;
	
	//getter and setter 
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getItemType()
	{
		return itemType;
	}
	public void setItemType(String itemType)
	{
		this.itemType = itemType;
	}
	public double getSellCost()
	{
		return sellCost;
	}
	public void setSellCost(double sellCost)
	{
		this.sellCost = sellCost;
	}
	public double getBuyCost()
	{
		return buyCost;
	}
	public void setBuyCost(double buyCost)
	{
		this.buyCost = buyCost;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getItemId()
	{
		return itemId;
	}
	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}
	public String getBrand()
	{
		return brand;
	}
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	//constructor
	public Item(String itemId, String name, String itemType, double sellCost, double buyCost, int quantity, 
			String location, String brand)
	{
		this.itemId = itemId;
		this.name = name;
		this.itemType = itemType;
		this.sellCost = sellCost;
		this.buyCost = buyCost;
		this.quantity = quantity;
		this.location = location;
		this.brand = brand;
	}
	//toString function apply
	public String toString()
	{
		return itemId + "," + name + "," + itemType + "," + sellCost + "," + buyCost + "," + quantity + "," + location + ","
				+  brand + ",";
	}
}