package de.shgruppe.bartender.emomapper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import de.shgruppe.bartender.emomapper.persistence.IngredientRepository;
import de.shgruppe.bartender.model.Emotion;
import de.shgruppe.bartender.model.Ingredient;
import de.shgruppe.bartender.model.WeightedEmotion;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({ "/EmoMapperServiceTest.properties" })
public class EmoMapperServiceTest
{

	@Autowired
	private IngredientRepository repository;

	@Autowired
	private EmoMapper emoMapper;


	@Test
	public void testInit() throws Exception {
		assertNotNull(emoMapper);
		assertNotNull(repository);
	}

	@Test
	public void testGetIngredientSimple() throws Exception
	{
		Ingredient result = emoMapper.getIngredientForEmotions(Arrays.asList(new WeightedEmotion(Emotion.ANGRY, 0.8)), false);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void testGetIngredientMultipleEmotions() throws Exception
	{
		Ingredient result = emoMapper.getIngredientForEmotions(Arrays.asList(new WeightedEmotion(Emotion.ANGRY, 0.3), new WeightedEmotion(Emotion.SURPRISED, 0.7)), false);
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void testRepository() throws Exception {
		assertEquals(160, StreamSupport.stream(repository.findAll().spliterator(), false).count());
		assertEquals(1, repository.findByShortName("Gin").size());
		assertEquals(23, repository.findByEmotion(Emotion.SURPRISED.name()).size());
	}


}
