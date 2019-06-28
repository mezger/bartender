package de.shgruppe.bartender.cocktail;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.EmotionalIngredient;

@Component("CocktailFinderMock")
@Lazy
public class CocktailFinderMock implements CocktailFinder
{
	private static Logger log = LoggerFactory.getLogger(CocktailFinderMock.class);


	@Override
	public Cocktail getCocktailForIngredients(List<EmotionalIngredient> ingredients, boolean noAlcohol)
	{
		log.warn("CocktailFinderMock aktiv!");

		List<String> cocktailIngredients = new ArrayList<>();
		cocktailIngredients.add("Tequila");
		cocktailIngredients.add("Triple sec");
		cocktailIngredients.add("Lime juice");
		cocktailIngredients.add("Salt");

		Cocktail cocktail = new Cocktail("11007",
			"Margarita",
			"Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten only the outer rim and sprinkle the salt on it. The salt should present to the lips of the imbiber and never mix into the cocktail. Shake the other ingredients with ice, then carefully pour into the glass.",
			"https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
			true,
			cocktailIngredients);

		return cocktail;
	}
}
