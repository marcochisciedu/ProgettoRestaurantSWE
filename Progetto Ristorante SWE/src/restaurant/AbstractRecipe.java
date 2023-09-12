package restaurant;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractRecipe {
	private String name;
	private double cost=0;
	Map<AbstractIngredient, Integer> ingredientsMap= new HashMap<AbstractIngredient, Integer>();
	
	public AbstractRecipe(String name, Map<AbstractIngredient, Integer> ingredients) {
		this.name=name;
		this.ingredientsMap=ingredients;
		for(AbstractIngredient key: ingredientsMap.keySet()) {
			cost+= key.getCost()*((double)(ingredientsMap.get(key)));                     //somma dei costi*quantità
		}
	}
	
	public String getName() {
		return name;
	}
	
	public double getCost() {
		return cost;
	}
	
	public Map<AbstractIngredient, Integer> getIngredients(){
		return ingredientsMap;
	}
	
}
