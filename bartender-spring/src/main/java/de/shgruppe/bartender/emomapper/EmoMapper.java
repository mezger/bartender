package de.shgruppe.bartender.emomapper;

import java.util.EnumSet;
import java.util.List;

import de.shgruppe.bartender.model.Emotion;
import de.shgruppe.bartender.model.Ingredient;
import de.shgruppe.bartender.model.WeightedEmotion;

public interface EmoMapper
{
	public List<Ingredient> getIngredientsForEmotions(EnumSet<Emotion> emotions);

	public Ingredient getIngredientForEmotions(List<WeightedEmotion> emotions, boolean noAlcohol);
}
