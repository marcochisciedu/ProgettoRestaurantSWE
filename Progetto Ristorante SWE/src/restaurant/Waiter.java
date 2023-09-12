package restaurant;

import java.util.ArrayList;
import Observer.*;

class Waiter extends Observable {
	private Services services;
	private int maxClients=3;
	private int numClients=0;
	private String name;
	
	public Waiter(String name, Services services) {
		this.name=name;
		this.services=services;
	}
	public void orderFood() {
		services.orderFood();
		if(numClients!=0) {
			numClients-=1;
		}
		this.notifyObserver();
	}
	
	@Override
	public void attach(Observer o) {
		this.setObserver(o);
	}
	
	@Override
	public void notifyObserver() {
		this.getObserver().update();
	}
	
	public Services getServices() {
		return services;
	}
	public void setServices(Services services) {
		this.services = services;
	}
	public int getMaxClients() {
		return maxClients;
	}
	public void setMaxClients(int maxClients) {
		this.maxClients = maxClients;
	}
	public int getNumClients() {
		return numClients;
	}
	public void setNumClients(int numClients) {
		this.numClients = numClients;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Client> getClients(){
		return services.getClients();
	}
	

}
