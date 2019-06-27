package de.shgruppe.bartender.emomapper;

import java.util.List;

import de.shgruppe.bartender.model.Ingredient;
import de.shgruppe.bartender.model.WeightedEmotion;

public interface EmoMapper
{

	public Ingredient getIngredientForEmotions(List<WeightedEmotion> emotions, boolean noAlcohol);

}
