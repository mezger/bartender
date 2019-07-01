package de.shgruppe.bartender.cocktail;

import java.util.List;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.EmotionalIngredient;

public interface CocktailFinder
{
	public Cocktail getCocktailForIngredients(List<EmotionalIngredient> ingredients, boolean noAlcohol);
}
