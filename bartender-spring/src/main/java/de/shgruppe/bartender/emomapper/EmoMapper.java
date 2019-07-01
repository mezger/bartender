package de.shgruppe.bartender.emomapper;

import java.util.List;

import de.shgruppe.bartender.model.EmotionalIngredient;
import de.shgruppe.bartender.model.WeightedEmotion;

public interface EmoMapper
{

	public EmotionalIngredient getIngredientForEmotions(List<WeightedEmotion> emotions, boolean noAlcohol);

}
