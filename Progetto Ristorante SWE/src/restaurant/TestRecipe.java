package restaurant;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestRecipe {
	static ConcreteIngredient farina;
	static ConcreteIngredient latte;
	static ConcreteIngredient uova;
	static Map<AbstractIngredient, Integer> ingredients= new HashMap<AbstractIngredient, Integer>();
	static ConcreteRecipe torta;
	
	@BeforeAll
	static void setUp() throws Exception {
		farina= new ConcreteIngredient("farina",1.7);
		latte= new ConcreteIngredient("latte",2.5);
		uova= new ConcreteIngredient("uova",3);
		ingredients.put(farina, 2);
		ingredients.put(latte, 1);
		ingredients.put(uova, 3);
		torta= new ConcreteRecipe("torta", ingredients);
	}

	@Test
	void testCreateRecipe() {
		assertEquals(14.9,torta.getCost());
	}
	
	@Test
	void testChangeCost() {
		assertEquals(14.9, torta.getCost());
		torta.changeCost(13);										//sconto
		assertEquals(13, torta.getCost());
	}

}
