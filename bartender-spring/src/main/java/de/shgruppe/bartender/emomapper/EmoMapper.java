package de.shgruppe.bartender.emomapper;

import java.util.EnumSet;
import java.util.List;

import de.shgruppe.bartender.model.Emotion;
import de.shgruppe.bartender.model.Ingredient;

public interface EmoMapper
{
	public List<Ingredient> getIngredientsForEmotions(EnumSet<Emotion> emotions);
}
