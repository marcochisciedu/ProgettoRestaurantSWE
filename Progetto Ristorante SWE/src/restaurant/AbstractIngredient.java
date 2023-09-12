package restaurant;

abstract class AbstractIngredient {
	private String name;
	private double cost;
	
	public AbstractIngredient(String name, double cost) {
		this.name=name;
		this.cost=cost;
	}
	
	public String getName() {
		return name;
	}
	
	public double getCost() {
		return cost;
	}
	
}
