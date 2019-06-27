package de.shgruppe.bartender.emomapper;

import static org.junit.Assert.*;

import org.junit.Test;

import de.shgruppe.bartender.model.Emotion;
import de.shgruppe.bartender.model.WeightedEmotion;

public class WeightComparatorDescTest {

	private final WeightComparatorDesc objectUnderTest = new WeightComparatorDesc();

	@Test
	public void testCompareMoreWight() throws Exception
	{
		WeightedEmotion o1 = new WeightedEmotion(Emotion.ANGRY, 0.8);
		WeightedEmotion o2 = new WeightedEmotion(Emotion.ANGRY, 0.7);
		int result = objectUnderTest.compare(o1, o2);
		assertEquals(-1, result);
	}
}
