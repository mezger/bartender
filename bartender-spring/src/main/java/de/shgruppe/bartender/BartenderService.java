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
import de.shgruppe.bartender.model.EmotionalIngredient;
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
		Cocktail cocktail;
		try
		{
			byte[] imageBytes = picture.getBytes();
			log.debug("image-size: {} bytes.", imageBytes.length);
			RekognitionResult rekognitionResult = rekognitionService.getEmotionsForImage(imageBytes);

			boolean noAlcohol = true;
			int alter = rekognitionResult.getAge();
			if(alter>=18)
			{
				noAlcohol = false;
				log.info("erkanntes Alter {}, alle Cocktails möglich.", alter);
			}
			else
			{
				log.info("erkanntes Alter {}, nur alkoholfreie Cocktails möglich.", alter);
			}

			EmotionalIngredient ingredient = emoMapper.getIngredientForEmotions(rekognitionResult.getEmotions(), noAlcohol);
			log.info("EmoMapper wählte die Zutat {} aus.",ingredient.getReadableName());

			List<EmotionalIngredient> ingredients = new ArrayList<>();
			ingredients.add(ingredient);

			cocktail = cocktailFinder.getCocktailForIngredients(ingredients, noAlcohol);
			log.info("CocktailFinder wählte den Cocktail {} aus.", cocktail.getName());
			cocktail.setRekognitionResult(rekognitionResult);
			cocktail.setEmotion(getEmotion(ingredient.getEmotion()));
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);

			List<String> cocktailIngredients = new ArrayList<>();
			cocktailIngredients.add("Milk");
			cocktail = new Cocktail("42666",
				"Mocktail",
				"Oooops! Hier ist was schiefgelaufen. Zur Beruhigung gibt es erst mal ein Glas Milch.",
				"https://alimentazionesportiva.it/wp-content/uploads/2018/05/quante-calorie-in-un-bicchiere-di-latte.jpg",
				"fehlerhaft",
				false,
				cocktailIngredients);
		}

		return cocktail;
	}


	private String getEmotion(String emoIngredient)
	{
		String emotion = "fehlerhaft";

		switch (emoIngredient)
		{
			case "HAPPY":
				emotion = "glücklich";
				break;

			case "SAD":
				emotion = "traurig";
				break;

			case "ANGRY":
				emotion = "wütend";
				break;

			case "CONFUSED":
				emotion = "verwirrt";
				break;

			case "DISGUSTED":
				emotion = "angewidert";
				break;

			case "SURPRISED":
				emotion = "überrascht";
				break;

			case "CALM":
				emotion = "ruhig";
				break;

			default:
				emotion = "nicht analysierbar";
		}
		return emotion;
	}
}
