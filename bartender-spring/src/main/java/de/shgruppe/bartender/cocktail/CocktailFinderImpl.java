package de.shgruppe.bartender.cocktail;

import java.util.ArrayList;
import java.util.List;

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
		JSONObject response = null;
		if( ingredients.size() > 0)
		{
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.getForObject(cocktailsByIngredientsURL + ingredients.get(0), JSONObject.class);

			List<String> drinksList = new ArrayList<String>();
			JSONArray array = response.getJSONArray("drinks");
			for(int i = 0 ; i < array.length() ; i++)
			{
				drinksList.add(array.getJSONObject(i).getString("idDrink"));
			}
		}

		return convertResponseToCocktail(response);
	}

	private Cocktail convertResponseToCocktail(JSONObject response)
	{
		Cocktail cocktail = new Cocktail();


		return cocktail;
	}
}
