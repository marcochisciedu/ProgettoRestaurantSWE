package Observer;

public abstract class Observable {
	Observer observer;
	
	public void attach(Observer o) {}
	public void notifyObserver() {}
	
	public Observer getObserver() {
		return observer;
	}
	public void setObserver(Observer observer) {
		this.observer = observer;
	}
}
