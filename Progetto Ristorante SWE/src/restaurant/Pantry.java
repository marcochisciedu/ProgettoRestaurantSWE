package restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Observer.Observer;

class Pantry implements Observer{
	private static Pantry instance=null;
	private double dailyBudget=0;
	private boolean isMorning=true;
	private ArrayList<AbstractIngredient> wantedIngredients=new ArrayList<AbstractIngredient>();
	private Map<AbstractIngredient, Integer> availableIngredients= new HashMap<AbstractIngredient, Integer>();
	private Market market;
	
	private Pantry(Market market, Map<AbstractIngredient, Integer> availableIngredients) {
		this.market=market;
		this.availableIngredients=availableIngredients;
	}
	
	public static Pantry getInstance(Market market, Map<AbstractIngredient, Integer> availableIngredients) {
		if (instance==null) {
			instance=new Pantry(market,availableIngredients);
		}
		return instance;
	}
	
	public boolean checkIngredients(Map<AbstractIngredient, Integer> neededIngredients) {
		boolean allPresent=true;
		for(AbstractIngredient key: neededIngredients.keySet()) {
			if((availableIngredients.containsKey(key)==false)
					||(neededIngredients.get(key)>availableIngredients.get(key))) {
				allPresent=false;
				if(wantedIngredients.contains(key)==false) {
					this.orderIngredients(key);
					}
				}
			}
		return allPresent;
	}
	
	public void removeIngredients(Map<AbstractIngredient, Integer> neededIngredients) {
		for(AbstractIngredient key: neededIngredients.keySet()) {
			availableIngredients.replace(key, availableIngredients.get(key)-neededIngredients.get(key));
		}
	}
	
	public void orderIngredients(AbstractIngredient needed) {
		this.wantedIngredients.add(needed);
	}
	
	public void buy() {
		if(isMorning==true) {
			for(AbstractIngredient wanted: wantedIngredients) {
				if(market.getAvailableIngredients().contains(wanted)){
					if(wanted.getCost()*10<dailyBudget) {	
						if(availableIngredients.containsKey(wanted)) {
							availableIngredients.replace(wanted, availableIngredients.get(wanted)+10);
						}
						else {
							availableIngredients.put(wanted, 10);
						}
						dailyBudget-=wanted.getCost()*10;
					}
				}
			}
		}
		else {
			System.out.println("It's not morning yet");
		}
	}
	
	@Override
	public void update() {
		isMorning=true;
	}

	public double getDailyBudget() {
		return dailyBudget;
	}

	public void setDailyBudget(double dailyBudget) {
		this.dailyBudget = dailyBudget;
	}

	public boolean isMorning() {
		return isMorning;
	}

	public void setMorning(boolean isMorning) {
		this.isMorning = isMorning;
	}

	public ArrayList<AbstractIngredient> getWantedIngredients() {
		return wantedIngredients;
	}

	public Map<AbstractIngredient, Integer> getAvailableIngredients() {
		return availableIngredients;
	}

	public Market getMarket() {
		return market;
	}
}
