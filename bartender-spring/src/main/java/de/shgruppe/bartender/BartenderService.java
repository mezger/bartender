package de.shgruppe.bartender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.shgruppe.bartender.cocktail.CocktailFinder;
import de.shgruppe.bartender.emomapper.EmoMapper;
import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Ingredient;
import de.shgruppe.bartender.model.RekognitionResult;
import de.shgruppe.bartender.rekognition.RekognitionService;

@Service
public class BartenderService
{
	private static Logger log = LoggerFactory.getLogger(BartenderService.class);

	@Autowired
	private RekognitionService rekognitionService;

	@Autowired
	private EmoMapper emoMapper;

	@Autowired
	private CocktailFinder cocktailFinder;


	public Cocktail getCocktailForImage(MultipartFile picture) throws IOException
	{
		byte[] imageBytes = picture.getBytes();
		log.debug("image-size: {} bytes.", imageBytes.length);
		RekognitionResult rekognitionResult = rekognitionService.getEmotionsForImage(imageBytes);

		boolean noAlcohol = true;
		if(rekognitionResult.getAge()>=18)
		{
			noAlcohol = false;
		}

		Ingredient ingredient = emoMapper.getIngredientForEmotions(rekognitionResult.getEmotions(), noAlcohol);
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(ingredient);

		return cocktailFinder.getCocktailForIngredients(ingredients, noAlcohol);
	}
}
