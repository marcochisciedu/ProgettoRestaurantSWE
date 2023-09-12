package restaurant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestPantry {
	static ConcreteIngredient farina;
	static ConcreteIngredient latte;
	static ConcreteIngredient uova;
	static ConcreteIngredient zucchero;
	static ConcreteIngredient vaniglia;
	static ArrayList<ConcreteIngredient> ingredients= new ArrayList<ConcreteIngredient>();
	static Map<AbstractIngredient, Integer> availableIngredients= new HashMap<AbstractIngredient, Integer>();
	static Pantry pantry;
	static Map<AbstractIngredient, Integer> ingredients1= new HashMap<AbstractIngredient,Integer>();
	static Map<AbstractIngredient, Integer> ingredients2= new HashMap<AbstractIngredient,Integer>();
	static Map<AbstractIngredient, Integer> ingredients3= new HashMap<AbstractIngredient,Integer>();

	static Market market;
	@BeforeAll
	static void setUp() throws Exception {
		farina= new ConcreteIngredient("farina",1.7);
		latte= new ConcreteIngredient("latte",2.5);
		uova= new ConcreteIngredient("uova",3);
		zucchero= new ConcreteIngredient("zucchero",1);
		vaniglia= new ConcreteIngredient("vaniglia",3.5);
		ingredients.add(farina);
		ingredients.add(uova);
		ingredients.add(latte);
		ingredients.add(zucchero);
		ingredients.add(vaniglia);
		availableIngredients.put(farina, 5);
		availableIngredients.put(latte, 3);
		availableIngredients.put(uova, 4);
		market= new Market(ingredients);
		pantry= Pantry.getInstance(market, availableIngredients);
	}

	@Test
	void testCheckIngredients() {
		ingredients1.put(farina, 2);
		ingredients1.put(latte, 1);
		ingredients1.put(uova, 3);
		assertTrue(pantry.checkIngredients(ingredients1));
		ingredients2.put(zucchero, 2);
		assertFalse(pantry.checkIngredients(ingredients2));               //zucchero non c'è
		assertTrue(pantry.getWantedIngredients().contains(zucchero));      //ordina lo zucchero
		ingredients3.put(farina, 30);
		assertFalse(pantry.checkIngredients(ingredients3));					//farina non abbastanza
		assertTrue(pantry.getWantedIngredients().contains(farina));
	}
	
	@Test
	void testRemoveIngredients() {
		ingredients1.put(latte, 2);
		pantry.removeIngredients(ingredients1);
		assertEquals(1,pantry.getAvailableIngredients().get(latte));
	}
	
	@Test
	void testOrderIngredients() {
		pantry.orderIngredients(uova);
		assertTrue(pantry.getWantedIngredients().contains(uova));
	}
	
	@Test
	void testBuy() {
		pantry.setMorning(false);
		pantry.buy();                         				  //non è ancora mattina
		pantry.setMorning(true);
		pantry.setDailyBudget(100);
		pantry.orderIngredients(farina);
		pantry.orderIngredients(vaniglia);
		pantry.buy();                                         //dopo questo ho 15 farina in dispensa
		ingredients1.put(farina, 7);
		assertTrue(pantry.checkIngredients(ingredients1));
		ingredients2.put(vaniglia, 8);
		assertTrue(pantry.checkIngredients(ingredients2));
		assertEquals(48,pantry.getDailyBudget());
	}

}
