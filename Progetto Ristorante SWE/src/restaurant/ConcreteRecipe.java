package restaurant;

import java.util.Map;

class ConcreteRecipe extends AbstractRecipe {
	private double newCost;
	
	public ConcreteRecipe(String name, Map<AbstractIngredient, Integer> ingredients) {
		super(name,ingredients);
		newCost=super.getCost();
	}
	
	public void changeCost(double cost) {
		this.newCost= cost;
	}
	
	@Override
	public double getCost() {
		return newCost;
	}
	
}
