package de.shgruppe.bartender.cocktail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Ingredient;

@Service("CocktailFinderImpl")
@Lazy
public class CocktailFinderImpl implements CocktailFinder
{
	private static Logger log = LoggerFactory.getLogger(CocktailFinderImpl.class);

	String cocktailsByIngredientsURL	= "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";
	String cocktailsByCoctailIDURL		= "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";
	String cocktailsAlcOrNoAlcoholicURL	= "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=";

	JSONObject jsonCocktailsAlcoholic;
	JSONObject jsonCocktailsNonAlcoholic;

	@PostConstruct
	public void initCocktailFinder()
	{
		// Listen der alkoholischen / nicht alkoholischen Cocktails wird zum Start geladen und sind die Anwendung über dann verfügbar
		RestTemplate restTemplate				= new RestTemplate();
		String responseCocktailsAlcoholic		= restTemplate.getForObject(cocktailsAlcOrNoAlcoholicURL + "Alcoholic", 		String.class);
		String responseCocktailsNonAlcoholic	= restTemplate.getForObject(cocktailsAlcOrNoAlcoholicURL + "Non_Alcoholic",		String.class);

		try
		{
			jsonCocktailsAlcoholic		= new JSONObject(responseCocktailsAlcoholic);
			jsonCocktailsNonAlcoholic	= new JSONObject(responseCocktailsNonAlcoholic);

			log.info("Alkoholische und nicht-alkoholische wurden geladen.");
		}
		catch( JSONException e)
		{
			log.warn("Error: Alkoholische und nicht-alkoholische konnten nicht geladen werden. " + e.getMessage());
		}
	}


	@Override
	public Cocktail getCocktailForIngredients(List<Ingredient> ingredients, boolean noAlcohol)
	{
		// 1.) Setze die Cocktail-Liste, gefiltert nach alkoholisch oder nicht alkoholischen Drinks
		JSONObject  jsonCocktailsFilteredByAlcOrNoAlc;
		if(noAlcohol)
		{
			jsonCocktailsFilteredByAlcOrNoAlc = jsonCocktailsNonAlcoholic;
		}
		else
		{
			jsonCocktailsFilteredByAlcOrNoAlc = jsonCocktailsAlcoholic;
		}

		// Speichere die Drink-IDs in der Liste
		List<String> listCocktailsFilteredByAlcNoAlc = new ArrayList<String>();
		JSONArray arrayCocktailsFilteredByAlcNoAlc = jsonCocktailsFilteredByAlcOrNoAlc.getJSONArray("drinks");
		for(int i = 0 ; i < arrayCocktailsFilteredByAlcNoAlc.length() ; i++)
		{
			listCocktailsFilteredByAlcNoAlc.add(arrayCocktailsFilteredByAlcNoAlc.getJSONObject(i).getString("idDrink"));
		}

		// 2.) Hole mir eine Liste von DrinkIds mit der übergebenen Zutat
		JSONObject jsonCocktailById				= null;
		RestTemplate restTemplate				= new RestTemplate();
		List<String> listCocktailsByIngredient	= new ArrayList<String>();
		if( ingredients.size() > 0)
		{
			String responseCocktailsByIngredients = restTemplate.getForObject(cocktailsByIngredientsURL + ingredients.get(0).getShortName(), String.class);
			log.info("Drinks anhand der Zutat " + ingredients.get(0).getShortName() + " geladen");
			JSONObject jsonCocktailsByIngredients = new JSONObject(responseCocktailsByIngredients);
			JSONArray arrayDrinksByIngridient = jsonCocktailsByIngredients.getJSONArray("drinks");
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

			boolean foundDrink = false;

			for(int i= 0; i < listCocktailsByIngredient.size(); i++)
			{
				if( listCocktailsFilteredByAlcNoAlc.contains(listCocktailsByIngredient.get(i)) )
				{
					drinkId = listCocktailsByIngredient.get(i);
					foundDrink = true;
					break;
				}
			}

			if(    foundDrink
				&& !drinkId.isEmpty())
			{
				String responseCocktailById = restTemplate.getForObject(cocktailsByCoctailIDURL + drinkId, String.class);
				log.info("Konkreten Drink geladen: DrinkID: " + drinkId);
				log.info("...Convert JSONResponse to Cocktail");
				jsonCocktailById = new JSONObject(responseCocktailById);
			}
			else
			{
				log.warn("Es wurde kein konkreter Drink anhand den Parametern gefunden");
				log.info("Es wird ein Random Drink geladen...");
				Random rand = new Random();
				String randomDrinkID = listCocktailsFilteredByAlcNoAlc.get(rand.nextInt(listCocktailsFilteredByAlcNoAlc.size()));
				jsonCocktailById = getDrinkById(randomDrinkID);

			}
		}

		return convertResponseToCocktail(jsonCocktailById);
	}


	/**
	 * Liest aus dem JSOBObject die relevanten Daten und gibt ein Cocktail-Objekt zurück
	 */
	private Cocktail convertResponseToCocktail(JSONObject jsonCocktailById)
	{
		Cocktail cocktail = new Cocktail();

		if( jsonCocktailById != null )
		{
			JSONArray drinks = jsonCocktailById.getJSONArray("drinks");
			JSONObject drink = drinks.getJSONObject(0);

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
				String ingredient = "";
				try
				{
					ingredient = drink.getString("strIngredient" + i);
					if( !ingredient.isEmpty())
					{
						listIngredients.add(ingredient);
					}
				}
				catch(Exception e)
				{
					log.info("Ingredient " + i + " konnte nicht geladen werden");
				}
			}

			cocktail.setListIngredients(listIngredients);
			log.info("Cocktail-Ojekt erstellt");
		}

		return cocktail;
	}

	private JSONObject getDrinkById( String drinkID )
	{
		JSONObject jsonDrink = new JSONObject();
		RestTemplate restTemplate = new RestTemplate();
		String responseRandomCocktail = restTemplate.getForObject(cocktailsByCoctailIDURL + drinkID, String.class);
		try
		{
			jsonDrink = new JSONObject(responseRandomCocktail);

			log.info("Random Drink wurde geladen");
		}
		catch( JSONException e)
		{
			log.warn("Random Drink konnte nicht geladen werdne");
		}

		return jsonDrink;

	}
}
