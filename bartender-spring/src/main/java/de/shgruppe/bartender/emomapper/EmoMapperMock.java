package de.shgruppe.bartender.emomapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.shgruppe.bartender.model.EmotionalIngredient;
import de.shgruppe.bartender.model.WeightedEmotion;

@Component("EmoMapperMock")
@Lazy
public class EmoMapperMock implements EmoMapper
{
	private static Logger log = LoggerFactory.getLogger(EmoMapperMock.class);

	@Override
	public EmotionalIngredient getIngredientForEmotions(List<WeightedEmotion> emotions, boolean noAlcohol)
	{
		log.warn("EmoMapperMock aktiv!");

		return new EmotionalIngredient("Gin", "Gin", "Drunk");
	}
}
