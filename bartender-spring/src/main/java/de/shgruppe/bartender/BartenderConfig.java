package de.shgruppe.bartender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import de.shgruppe.bartender.cocktail.CocktailFinder;
import de.shgruppe.bartender.emomapper.EmoMapper;
import de.shgruppe.bartender.rekognition.RekognitionService;

@Configuration
@PropertySource(value = "classpath:bartender.properties", ignoreResourceNotFound = true)
public class BartenderConfig
{
	@Autowired
	private ApplicationContext context;

	@Bean
	CocktailFinder cocktailFinder(@Value("${cocktailfinder.class:CocktailFinderMock}") String qualifier)
	{
		return (CocktailFinder) context.getBean(qualifier);
	}

	@Bean
	EmoMapper emoMapper(@Value("${emomapper.class:EmoMapperMock}") String qualifier)
	{
		return (EmoMapper) context.getBean(qualifier);
	}

	@Bean
	RekognitionService rekognitionService(@Value("${rekognitionservice.class:RekognitionServiceMock}") String qualifier)
	{
		return (RekognitionService) context.getBean(qualifier);
	}
}
