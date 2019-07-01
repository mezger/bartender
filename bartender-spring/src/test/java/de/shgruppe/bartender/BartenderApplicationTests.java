package de.shgruppe.bartender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import de.shgruppe.bartender.cocktail.CocktailFinderImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BartenderApplicationTests {

	@MockBean
	private CocktailFinderImpl cocktailFinder;

	@Test
	public void contextLoads() {
	}

}
