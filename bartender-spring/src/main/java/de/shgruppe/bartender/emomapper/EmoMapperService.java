package de.shgruppe.bartender.emomapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.shgruppe.bartender.emomapper.entities.IngredientEntity;
import de.shgruppe.bartender.emomapper.persistence.IngredientRepository;
import de.shgruppe.bartender.emomapper.persistence.RepoInitializer;
import de.shgruppe.bartender.model.Ingredient;
import de.shgruppe.bartender.model.WeightedEmotion;

@Service("EmoMapperService")
@Lazy
public class EmoMapperService implements EmoMapper
{

	private static final Logger LOG = LoggerFactory.getLogger(EmoMapperService.class);

	@Autowired
	private IngredientRepository repository;

	@Value("${de.shgruppe.bartenter.emomapper.csvfile}")
	private String fileName;

	@PostConstruct
	public void initRepository() throws IOException
	{
		RepoInitializer.initRepo(fileName, repository);
	}

	@Override
	public Ingredient getIngredientForEmotions(List<WeightedEmotion> emotions, boolean noAlcohol)
	{
		List<WeightedEmotion> sortedEmotions = sortEmotions(emotions);
		List<IngredientEntity> ingredientEntities = new ArrayList<>();
		for (WeightedEmotion emotion : sortedEmotions)
		{
			ingredientEntities = repository.findByEmotionAndAlcohol(emotion.getEmotion().name(), !noAlcohol);
			if (!ingredientEntities.isEmpty())
			{
				break;
			}
		}
		if (ingredientEntities.isEmpty())
		{
			throw new IllegalArgumentException("Could not find any ingredients for given emotions");
		}
		int selectedIndex = new Random().nextInt(ingredientEntities.size());
		IngredientEntity entity = ingredientEntities.get(selectedIndex);
		LOG.info(String.format("Found ingredient '%s' for emotion '%s'", entity.getReadableName(), entity.getEmotion()));
		return new Ingredient(entity.getShortName(), entity.getReadableName());
	}

	private static List<WeightedEmotion> sortEmotions(List<WeightedEmotion> emotions) {
		List<WeightedEmotion> sortedEmotions = new ArrayList<>(emotions);
		sortedEmotions.sort(new WeightComparatorDesc());
		return sortedEmotions;
	}

}
