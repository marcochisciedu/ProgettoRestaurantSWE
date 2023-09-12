package restaurant;

import java.util.ArrayList;

class Services {
	private CashRegister register;
	private ArrayList<Client> clients= new ArrayList<Client>();
	private Pantry pantry;
	private ArrayList<ConcreteRecipe> menu= new ArrayList<ConcreteRecipe>();
	
	public Services(CashRegister register, Pantry pantry,ArrayList<ConcreteRecipe> menu ) {
		this.register=register;
		this.pantry=pantry;
		this.menu=menu;
	}
	
	public void takeMoney() {
		pantry.setDailyBudget(pantry.getDailyBudget()+(register.getMoney()/2));
		register.setMoney(register.getMoney()/2);
	}
	
	public boolean checkCost(Client client, ConcreteRecipe recipe) {
		boolean isEnough=false;
		if(client.getBugdet()>recipe.getCost()) {
			isEnough=true;
		}
		return isEnough;
	}
	
	public void orderFood() {
		if(clients.size()!=0) {
			Client currentClient=clients.remove(0);             //prende il primo cliente
			boolean chosen=false;
			int dissatisfaction=0;
			while(dissatisfaction<3&&chosen==false) {
				int rand= (int) (Math.random()*(menu.size()));
				if(pantry.checkIngredients(menu.get(rand).getIngredients())&& 
						this.checkCost(currentClient, menu.get(rand))) {
					chosen=true;
					pantry.removeIngredients(menu.get(rand).getIngredients());
					this.pay(menu.get(rand),currentClient);
					System.out.println("the client got his food");
				}
				else {
					dissatisfaction+=1;
				}
			}
			if(dissatisfaction==3) {
				System.out.println("the client went away");
			}
		}	
		else {
			System.out.println("There are no clients left");
		}
	}
	
	public void pay(ConcreteRecipe recipe, Client client) {
		register.setMoney(register.getMoney()+recipe.getCost());
		client.setBugdet(client.getBugdet()-(recipe.getCost()));
	}
	
	public ArrayList<ConcreteRecipe> getMenu() {
		return menu;
	}
	public void setMenu(ArrayList<ConcreteRecipe> menu) {
		this.menu = menu;
	}
	public CashRegister getRegister() {
		return register;
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public void addClient(Client client) {
		clients.add(client);
	}
	public Pantry getPantry() {
		return pantry;
	}
}
