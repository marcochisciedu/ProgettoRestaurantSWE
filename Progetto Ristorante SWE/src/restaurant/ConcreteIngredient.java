package restaurant;

class ConcreteIngredient extends AbstractIngredient {
	private double newCost;
	
	public ConcreteIngredient(String name, double cost) {
		super(name,cost);
		this.newCost=cost;
	}
	
	public void changeCost(double cost) {
		this.newCost= cost;
	}
	
	@Override
	public double getCost() {
		return newCost;
	}
	
}
