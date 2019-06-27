package de.shgruppe.bartender.cocktail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Ingredient;

@Service
public class CocktailFinderImpl implements CocktailFinder
{
	// private static Logger log = LoggerFactory.getLogger(CocktailFinderImpl.class);

	String cocktailsByIngredientsURL	= "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";
	String cocktailsByCoctailIDURL		= "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
	String cocktailsAlcOrNoAlcoholicURL	= "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=";

	JSONObject responseCocktailsAlcoholic;
	JSONObject responseCocktailsNonAlcoholic;

	@PostConstruct
	public void initCocktailFinder()
	{
		RestTemplate restTemplate		= new RestTemplate();
		responseCocktailsAlcoholic		= restTemplate.getForObject(cocktailsAlcOrNoAlcoholicURL + "Non_Alcoholic", JSONObject.class);
		responseCocktailsNonAlcoholic	= restTemplate.getForObject(cocktailsAlcOrNoAlcoholicURL + "Alcoholic",		JSONObject.class);
	}


	@Override
	public Cocktail getCocktailForIngredients(List<Ingredient> ingredients, boolean noAlcohol)
	{
		// 1.) Setze die Cocktail-Liste, gefiltert nach alkoholisch oder nicht alkoholischen Drinks
		JSONObject  jsonCocktailsFilteredByAlcOrNoAlc;
		if(noAlcohol)
		{
			jsonCocktailsFilteredByAlcOrNoAlc = responseCocktailsNonAlcoholic;
		}
		else
		{
			jsonCocktailsFilteredByAlcOrNoAlc = responseCocktailsAlcoholic;
		}

		// Speichere die Drink-IDs in der Liste
		List<String> listCocktailsFilteredByAlcNoAlc = new ArrayList<String>();
		JSONArray arrayCocktailsFilteredByAlcNoAlc = jsonCocktailsFilteredByAlcOrNoAlc.getJSONArray("drinks");
		for(int i = 0 ; i < arrayCocktailsFilteredByAlcNoAlc.length() ; i++)
		{
			listCocktailsFilteredByAlcNoAlc.add(arrayCocktailsFilteredByAlcNoAlc.getJSONObject(i).getString("idDrink"));
		}

		// 2.) Hole mir eine Liste von DrinkIds mit der übergebenen Zutat
		JSONObject responseCocktailById			= null;
		RestTemplate restTemplate				= new RestTemplate();
		List<String> listCocktailsByIngredient	= new ArrayList<String>();
		if( ingredients.size() > 0)
		{
			JSONObject responseCocktailsByIngredients = restTemplate.getForObject(cocktailsByIngredientsURL + ingredients.get(0).getShortName(), JSONObject.class);
			JSONArray arrayDrinksByIngridient = responseCocktailsByIngredients.getJSONArray("drinks");
			for(int i = 0 ; i < arrayDrinksByIngridient.length() ; i++)
			{
				listCocktailsByIngredient.add(arrayDrinksByIngridient.getJSONObject(i).getString("idDrink"));
			}
		}

		// 3.) Es wird ein Cocktail geholt, welcher in beiden Listen vertreten ist
		if(    listCocktailsFilteredByAlcNoAlc.size() > 0
			&& listCocktailsByIngredient.size() > 0)
		{
			// Beide Listen werden durchgemischt, um einen Random Effekt zu erzielen
			Collections.shuffle(listCocktailsFilteredByAlcNoAlc);
			Collections.shuffle(listCocktailsByIngredient);
			String drinkId = "";

			for(int i= 0; i < listCocktailsByIngredient.size(); i++)
			{
				if( listCocktailsFilteredByAlcNoAlc.contains(listCocktailsByIngredient.get(i)) )
				{
					drinkId = listCocktailsByIngredient.get(i);
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
