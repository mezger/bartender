package de.shgruppe.bartender.cocktail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Ingredient;

public class CocktailFinderImpl implements CocktailFinder
{
	// private static Logger log = LoggerFactory.getLogger(CocktailFinderImpl.class);

	String cocktailsByIngredientsURL	= "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";
	String cocktailsByCoctailIDURL		= "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
	String cocktailsAlcOrNoAlcoholicURL	= "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=";


	/*ToDo: postProcessAfterInitialization
	JSONObject responseCocktailsAlcoholic;
	JSONObject responseCocktailsNonAlcoholic;
	JSONObject responseCocktailsByIngredient;

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
	{
		RestTemplate restTemplate = new RestTemplate();
		responseCocktailsAlcoholic		= restTemplate.getForObject(cocktailsAlcOrNoAlcoholicURL + "Non_Alcoholic", JSONObject.class);
		responseCocktailsNonAlcoholic	= restTemplate.getForObject(cocktailsAlcOrNoAlcoholicURL + "Alcoholic",		JSONObject.class);

		return bean;
	}
	*/

	@Override
	public Cocktail getCocktailForIngredients(List<Ingredient> ingredients, boolean noAlcohol)
	{
		// 1.) Hole mir eine Liste an alkoholischen/nicht alkoholischen Drinks
		String urlDrinkList = cocktailsAlcOrNoAlcoholicURL;
		if(noAlcohol)
		{
			urlDrinkList += "Non_Alcoholic";
		}
		else
		{
			urlDrinkList += "Alcoholic";
		}

		RestTemplate restTemplate = new RestTemplate();
		JSONObject  responseCocktailsAlcOrNoAlc = restTemplate.getForObject(urlDrinkList, JSONObject.class);

		List<String> listDrinksAlcOrNoAlc = new ArrayList<String>();
		JSONArray arrayDrinksAlcOrNocAlc = responseCocktailsAlcOrNoAlc.getJSONArray("drinks");
		for(int i = 0 ; i < arrayDrinksAlcOrNocAlc.length() ; i++)
		{
			listDrinksAlcOrNoAlc.add(arrayDrinksAlcOrNocAlc.getJSONObject(i).getString("idDrink"));
		}

		// 2.) Hole mir eine Liste von DrinkIds mit der übergebenen Zutat
		JSONObject responseCocktailById = null;
		List<String> listDrinksByIngredient = new ArrayList<String>();
		if( ingredients.size() > 0)
		{
			JSONObject responseCocktailsByIngredients = restTemplate.getForObject(cocktailsByIngredientsURL + ingredients.get(0).getShortName(), JSONObject.class);
			JSONArray arrayDrinksByIngridient = responseCocktailsByIngredients.getJSONArray("drinks");
			for(int i = 0 ; i < arrayDrinksByIngridient.length() ; i++)
			{
				listDrinksByIngredient.add(arrayDrinksByIngridient.getJSONObject(i).getString("idDrink"));
			}
		}

		// 3.) Hole mir den Drink, welcher in beiden Listen vertreten ist
		if(    listDrinksAlcOrNoAlc.size() > 0
			&& listDrinksByIngredient.size() > 0)
		{
			// Beide Listen werden durchgemischt, um einen Random Effekt zu erzielen
			Collections.shuffle(listDrinksAlcOrNoAlc);
			Collections.shuffle(listDrinksByIngredient);
			String drinkId = "";

			for(int i= 0; i < listDrinksByIngredient.size(); i++)
			{
				if( listDrinksAlcOrNoAlc.contains(listDrinksByIngredient.get(i)) )
				{
					drinkId = listDrinksByIngredient.get(i);
					break;
				}
			}

			if( !drinkId.isEmpty())
			{
				responseCocktailById = restTemplate.getForObject(cocktailsByCoctailIDURL + drinkId, JSONObject.class);
			}
		}

		return convertResponseToCocktail(responseCocktailById);
	}

	private Cocktail convertResponseToCocktail(JSONObject response)
	{
		Cocktail cocktail = new Cocktail();

		if( response != null )
		{
			JSONObject drink = response.getJSONObject("drinks");

			cocktail.setId			(drink.getString("idDrink"));
			cocktail.setName		(drink.getString("strDrink"));
			cocktail.setZubereitung	(drink.getString("strInstructions"));
			cocktail.setImage		(drink.getString("strDrinkThumb"));

			String alkoholisch			= drink.getString("strAlcoholic");
			boolean isDrinkAlcoholic	= alkoholisch.equals("Alcoholic");
			cocktail.setAlkoholisch(isDrinkAlcoholic);

			// Es sind maximal 15 Ingredient im JSON-Object vorhanden
			List<String> listIngredients = new ArrayList<String>();
			for(int i = 1; i <= 15; i++)
			{
				String ingredient = drink.getString("strIngredient" + i);
				if( !ingredient.isEmpty())
				{
					listIngredients.add(ingredient);
				}
			}

			cocktail.setListIngredients(listIngredients);
		}

		return cocktail;
	}
}
