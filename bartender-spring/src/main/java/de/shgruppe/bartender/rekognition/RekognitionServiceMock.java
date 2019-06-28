package de.shgruppe.bartender.rekognition;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.shgruppe.bartender.model.Emotion;
import de.shgruppe.bartender.model.RekognitionResult;
import de.shgruppe.bartender.model.WeightedEmotion;

@Component("RekognitionServiceMock")
@Lazy
public class RekognitionServiceMock implements RekognitionService
{
	private static Logger log = LoggerFactory.getLogger(RekognitionServiceMock.class);


	@Override
	public RekognitionResult getEmotionsForImage(byte[] image)
	{
		log.warn("RekognitionServiceMock aktiv!");

		RekognitionResult rekognitionResult = new RekognitionResult();
		List<WeightedEmotion> emotions = new ArrayList<>();
		emotions.add(new WeightedEmotion(Emotion.ANGRY, 99d));
		emotions.add(new WeightedEmotion(Emotion.CALM, 2d));
		emotions.add(new WeightedEmotion(Emotion.CONFUSED, 77d));
		emotions.add(new WeightedEmotion(Emotion.DISGUSTED, 42d));
		emotions.add(new WeightedEmotion(Emotion.SURPRISED, 21d));
		rekognitionResult.setEmotions(emotions);
		return rekognitionResult;
	}
}
