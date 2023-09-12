package restaurant;

class Client {
	private double bugdet;
	
	Client(double budget){
		this.bugdet=budget;
	}
	
	public double getBugdet() {
		return bugdet;
	}

	public void setBugdet(double budget) {
		this.bugdet = budget;
	}
}
