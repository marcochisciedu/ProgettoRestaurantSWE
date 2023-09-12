package restaurant;

class CashRegister {
	private double money;
	private static CashRegister instance= null;
	
	private CashRegister(double money) {
		this.money=money;
	}
	
	public static CashRegister getInstance(double money){
		if(instance==null)
			instance= new CashRegister(money);
		return instance;
	}
	
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}
