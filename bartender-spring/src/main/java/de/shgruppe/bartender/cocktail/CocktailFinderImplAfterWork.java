package de.shgruppe.bartender.cocktail;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.shgruppe.bartender.model.Cocktail;
import de.shgruppe.bartender.model.Cocktails;
import de.shgruppe.bartender.model.EmotionalIngredient;

@Service("CocktailFinderImplAfterWork")
@Lazy
public class CocktailFinderImplAfterWork implements CocktailFinder {

	private static final Logger logger = LoggerFactory.getLogger(CocktailFinderImplAfterWork.class);

	private final MultiValueMap<String, Cocktail> cocktails = new LinkedMultiValueMap<>();
	private String fileName = "cocktails_AfterWork.xml";

	@PostConstruct
	public void initCocktails() throws Exception
	{
		XmlMapper xmlMapper = new XmlMapper();
		Cocktails xmlCocktails = xmlMapper.readValue( CocktailFinderImplAfterWork.class.getClassLoader().getResourceAsStream(fileName), Cocktails.class);
		xmlCocktails.forEach(cocktail -> cocktail.getListIngredients().forEach(ingredient -> this.cocktails.add(ingredient, cocktail)));
		logger.info(String.format("Initialized CocktailDB with %s cocktails.", cocktails.size()));
	}

	@Override
	public Cocktail getCocktailForIngredients(List<EmotionalIngredient> ingredients, boolean noAlcohol) {
		List<Cocktail> suitableCocktails = this.cocktails.get(ingredients.get(0).getShortName());
		List<Cocktail> filteredCocktails = suitableCocktails.stream().filter(cocktail -> !noAlcohol || !cocktail.isAlkoholisch()).collect(Collectors.toList());
		return filteredCocktails.get(new Random().nextInt(filteredCocktails.size()-1));
	}

}
