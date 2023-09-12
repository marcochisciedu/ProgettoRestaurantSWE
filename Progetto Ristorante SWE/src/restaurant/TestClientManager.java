package restaurant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestClientManager {
	static ConcreteIngredient farina;
	static ConcreteIngredient latte;
	static ConcreteIngredient uova;
	static ConcreteRecipe torta;
	static ArrayList<ConcreteIngredient> ingredients= new ArrayList<ConcreteIngredient>();
	static Map<AbstractIngredient, Integer> availableIngredients= new HashMap<AbstractIngredient, Integer>();
	static Map<AbstractIngredient, Integer> cakeIngredients= new HashMap<AbstractIngredient, Integer>();
	static ArrayList<ConcreteRecipe> menu= new ArrayList<ConcreteRecipe>();
	static Market market;
	static Pantry pantry;
	static CashRegister register;
	static Services services;

	ClientsManager manager;
	Client client;
	Client client2;
	Client client3;
	Client client4;
	
	Waiter mario;
	Waiter sara;
	@BeforeAll
	static void basics() throws Exception{
		farina= new ConcreteIngredient("farina",1.5);
		latte= new ConcreteIngredient("latte",2.5);
		uova= new ConcreteIngredient("uova",3);
		ingredients.add(farina);
		ingredients.add(uova);
		ingredients.add(latte);
		market= new Market(ingredients);
		cakeIngredients.put(farina, 3);
		cakeIngredients.put(latte, 2);
		cakeIngredients.put(uova, 1);
		torta= new ConcreteRecipe("torta",cakeIngredients);
		menu.add(torta);
		availableIngredients.put(farina, 10);
		availableIngredients.put(latte, 10);
		availableIngredients.put(uova, 10);
		pantry=Pantry.getInstance(market, availableIngredients);
		register=CashRegister.getInstance(1000);
		services=new Services(register, pantry, menu);
	}
	
	@BeforeEach
	void setUp() throws Exception {
		manager=new ClientsManager();
		client=new Client(50);
		client2=new Client(40);
		client3=new Client(30);
		client4=new Client(20);
		mario=new Waiter("mario",services);
		manager.addWaiter(mario);
		sara=new Waiter("sara",services);
		manager.addWaiter(sara);
	}

	@Test
	void testAddClient() {
		manager.addClient(client);
		assertTrue(manager.getClients().contains(client));
		manager.addClient(client);                          	//questo cliente è già presente
		manager.setCanAddClient(false);
		manager.addClient(client2);
	}
	
	@Test 
	void testChooseWaiter() {
		manager.addClient(client);
		manager.chooseWaiter("mario");
		manager.chooseWaiter("giovanni");            			//questo cameriere non esiste
		assertTrue(mario.getClients().contains(client));
		manager.addClient(client2);
		manager.addClient(client3);
		manager.addClient(client4);
		manager.giveClient(mario);
		manager.giveClient(mario);
		manager.giveClient(mario);                				 //ha troppi clienti
	}
	
	@Test
	void testDayNight() {	
		pantry.setMorning(true);         				 //è la mattina prima del giorno lavorativo
		manager.startDay();
		assertTrue(manager.isCanAddClient());
		assertFalse(pantry.isMorning());
		assertEquals(500,pantry.getDailyBudget());
		manager.startDay();               				 //ancora non posso ripartire
		manager.endDay();	
		assertFalse(manager.isCanAddClient());
		manager.endDay();                 				 //ancora non posso chiudere
		pantry.setMorning(true);               			  //reset perchè singleton
	}
	
	 @Test
	 void testObserver() {               
		 manager.startDay();
		 manager.addClient(client);
		 manager.addClient(client2);
		 manager.addClient(client3);
		 manager.chooseWaiter("mario");
		 manager.chooseWaiter("mario");
		 manager.chooseWaiter("sara");
		 mario.orderFood();
		 sara.orderFood();
		 assertEquals(1,manager.getNumClientsDay());
		 manager.endDay();
		 mario.orderFood();
		 assertEquals(0,manager.getNumClientsDay());
		 assertTrue(pantry.isMorning());
	 }

}
