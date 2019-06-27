package de.shgruppe.bartender.cocktail;

import java.util.List;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Ingredient;

public interface CocktailFinder
{
	public Cocktail getCocktailForIngredients(List<Ingredient> ingredients, boolean noAlcohol);
}
