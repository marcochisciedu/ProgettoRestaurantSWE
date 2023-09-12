package restaurant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class TestServices {
	static ConcreteIngredient farina;
	static ConcreteIngredient latte;
	static ConcreteIngredient uova;
	static ConcreteIngredient zucchero;
	static ConcreteRecipe torta;
	static ArrayList<ConcreteIngredient> ingredients= new ArrayList<ConcreteIngredient>();
	static Map<AbstractIngredient, Integer> availableIngredients= new HashMap<AbstractIngredient, Integer>();
	static Map<AbstractIngredient, Integer> cakeIngredients= new HashMap<AbstractIngredient, Integer>();
	static ArrayList<ConcreteRecipe> menu= new ArrayList<ConcreteRecipe>();
	static Market market;
	static Pantry pantry;
	static CashRegister register;
	static CashRegister register2;
	static Services services;
	static Client client;
	static Client client2;
	static Client client3;

	@BeforeAll
	static void setUp() throws Exception {
		farina= new ConcreteIngredient("farina",1.5);
		latte= new ConcreteIngredient("latte",2.5);
		uova= new ConcreteIngredient("uova",3);
		zucchero= new ConcreteIngredient("zucchero",1);
		ingredients.add(farina);
		ingredients.add(uova);
		ingredients.add(latte);
		ingredients.add(zucchero);
		market= new Market(ingredients);
		cakeIngredients.put(farina, 3);
		cakeIngredients.put(latte, 2);
		cakeIngredients.put(uova, 1);
		torta= new ConcreteRecipe("torta",cakeIngredients);
		menu.add(torta);
		availableIngredients.put(farina, 5);
		availableIngredients.put(latte, 2);
		availableIngredients.put(uova, 4);
		pantry=Pantry.getInstance(market, availableIngredients);
		client=new Client(50);
		client2=new Client(10);
		client3=new Client(40);
		register=CashRegister.getInstance(1000);
		register2=CashRegister.getInstance(500);               
		services=new Services(register, pantry, menu);
		services.addClient(client);
		services.addClient(client2);
		services.addClient(client3);
		}
	
	@Test
	@Order(1)
	void testTakeMoney() {
		services.takeMoney();
		assertEquals(500,pantry.getDailyBudget());
		assertEquals(500,register.getMoney());
	} 
	@Test
	void testCheckCost() {
		assertTrue(services.checkCost(client, torta));
	}
	@Test
	@Order(2)
	void testPay() {
		services.pay(torta, client);
		assertEquals(512.5,register.getMoney());
		assertEquals(37.5,client.getBugdet());
	}
	
	@Test
	@Order(3)
	void testOrderFood() {
		services.orderFood();
		assertEquals(525,register.getMoney());
		assertEquals(25,client.getBugdet());
		services.orderFood();                              //secondo cliente non ha abbastanza soldi
		assertEquals(525,register.getMoney());
		services.orderFood();                              //non ci sono abbastanza ingredienti
		assertEquals(525,register.getMoney());
		services.orderFood();                              //non ci sono più clienti
	}
	
	@Test
	void testSingleton() {
		assertSame(register,register2);
	}
	
}
