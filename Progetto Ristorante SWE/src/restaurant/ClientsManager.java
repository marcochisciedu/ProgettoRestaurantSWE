package restaurant;

import java.util.ArrayList;

import Observer.*;

class ClientsManager extends Observable implements Observer {
	private boolean canPantry=true;
	private int numClientsDay=0;
	private boolean canAddClient=true;
	private ArrayList<Waiter> waiters= new ArrayList<Waiter>();
	private ArrayList<Client> clients= new ArrayList<Client>();
	
	public ClientsManager() {
	}
	
	public void addClient(Client client) {
		if(canAddClient) {
			if(clients.contains(client)==false){
				numClientsDay+=1;
				clients.add(client);
			}
			else {
				System.out.println("You already added this client");
			}
		}
		else {
			System.out.println("We are closed, you can't add a new client");
		}
	}
	public void chooseWaiter(String name) {
		boolean isHere=false;
		int i=0;
		while(i<waiters.size()&&isHere==false) {
			if((waiters.get(i).getName()).equals(name)) { 			
				isHere=true;
				giveClient(waiters.get(i));
			}
			i+=1;
		}
		if(isHere==false) {
			System.out.println("The waiter is not here");
		}
	}
	public void giveClient(Waiter waiter) {
		if(waiter.getNumClients()<waiter.getMaxClients()) {
			waiter.getServices().addClient(clients.get(0));
			waiter.setNumClients(waiter.getNumClients()+1);
			clients.remove(0);
		}
		else {
			System.out.println("This waiter already has too many clients");
		}
;	}
	
	public void startDay() {
		if(waiters.size()>0){
			if(waiters.get(0).getServices().getPantry().isMorning()) {
				canAddClient=true;
				waiters.get(0).getServices().getPantry().setMorning(false);
				waiters.get(0).getServices().takeMoney();
				if(canPantry) {								//first day
					this.attach(waiters.get(0).getServices().getPantry());
					canPantry=false;
				}
			}
			else {
				System.out.println("The day has not ended yet");
			}
		}
		else {
			System.out.println("There is no waiter");
		}
	}
	
	public void endDay() {    
		if(waiters.get(0).getServices().getPantry().isMorning()==false&&canAddClient) {
				canAddClient=false;
			}
		else {
			System.out.println("The restaurant is not open, you can't close it");
		}
	}
	
	@Override
	public void update() {
		this.numClientsDay-=1;
		if(this.numClientsDay==0) {
			this.notifyObserver();
			if(canAddClient) {                       // se non ci sono più clienti chiudo
				this.endDay();          
			}
		}
	} 
	
	@Override
	public void attach(Observer o) {
		this.setObserver(o);
	}
	
	@Override
	public void notifyObserver() {
		this.getObserver().update();
	}
	
	public int getNumClientsDay() {
		return numClientsDay;
	}
	public void setNumClientsDay(int numClientsDay) {
		this.numClientsDay = numClientsDay;
	}
	public boolean isCanAddClient() {
		return canAddClient;
	}
	public void setCanAddClient(boolean canAddClient) {
		this.canAddClient = canAddClient;
	}
	public ArrayList<Waiter> getWaiters() {
		return waiters;
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public void addWaiter(Waiter waiter) {
		waiters.add(waiter);
		waiter.attach(this);
	}
	public boolean getCanPantry() {
		return canPantry;
	}
}
