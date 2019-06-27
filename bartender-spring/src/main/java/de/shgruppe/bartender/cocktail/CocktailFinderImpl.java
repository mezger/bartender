package de.shgruppe.bartender.cocktail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Ingredient;

public class CocktailFinderImpl implements CocktailFinder
{
	// private static Logger log = LoggerFactory.getLogger(CocktailFinderImpl.class);

	String cocktailsByIngredientsURL	= "https://www.thecocktaildb.com/api/json/v1/1/search.php?i=";
	String cocktailsByCoctailIDURL		= "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?iid=";

	@Override
	public Cocktail getCocktailForIngredients(List<Ingredient> ingredients, boolean noAlcohol)
	{
		JSONObject responseCocktailById = null;
		if( ingredients.size() > 0)
		{
			// 1.) Hole mir eine Liste von DrinkIds mit dieser Zutat
			RestTemplate restTemplate = new RestTemplate();
			JSONObject  responsCocktailByIngrdients = restTemplate.getForObject(cocktailsByIngredientsURL + ingredients.get(0).getShortName(), JSONObject.class);

			List<String> listDrinks = new ArrayList<String>();
			JSONArray array = responsCocktailByIngrdients.getJSONArray("drinks");
			for(int i = 0 ; i < array.length() ; i++)
			{
				listDrinks.add(array.getJSONObject(i).getString("idDrink"));
			}

			// 2.) Hole mir eine DrinkId aus der Liste
			if( listDrinks.size() > 0 )
			{
				// Falls sich mehrere DrinkIds in der Liste befinden, wird sich per Zufall eine ID geholt
				Random random = new Random();
				String randomDrinkID = listDrinks.get(random.nextInt(listDrinks.size()));
				responseCocktailById = restTemplate.getForObject(cocktailsByCoctailIDURL + randomDrinkID, JSONObject.class);
			}
		}

		return convertResponseToCocktail(responseCocktailById);
	}

	private Cocktail convertResponseToCocktail(JSONObject response)
	{
		Cocktail cocktail = new Cocktail();
		JSONObject drink = response.getJSONObject("drinks");

		cocktail.setId(drink.getString("idDrink"));
		cocktail.setName(drink.getString("strDrink"));
		cocktail.setZubereitung(drink.getString("strInstructions"));
		cocktail.setImage(drink.getString("strDrinkThumb"));

		String alkoholisch = drink.getString("strAlcoholic");
		boolean isDrinkAlcoholic = alkoholisch.equals("Alcoholic");
		cocktail.setAlkoholisch(isDrinkAlcoholic);

		// Es sind maximal 15 Ingredient im JSON-Object vorhanden
		List<String> listIngredients = new ArrayList<String>();
		for(int i = 0; i <= 15; i++)
		{
			listIngredients.add(drink.getString("strIngredient" + i));
		}
		cocktail.setListIngredients(listIngredients);

		return cocktail;
	}
}
