package de.shgruppe.bartender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.shgruppe.bartender.cocktail.CocktailFinder;
import de.shgruppe.bartender.cocktail.CocktailFinderMock;

@Configuration
public class BartenderConfig
{
	@Bean
	CocktailFinder cocktailFinder()
	{
		return new CocktailFinderMock();
	}

}
