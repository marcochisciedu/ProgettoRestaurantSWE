package restaurant;

import java.util.ArrayList;

class Market {
	private ArrayList<ConcreteIngredient> availableIngredients;

	public Market(ArrayList<ConcreteIngredient> availableIngredients) {
		this.availableIngredients=availableIngredients;
	}
	
	public ArrayList<ConcreteIngredient> getAvailableIngredients() {
		return availableIngredients;
	}
}
