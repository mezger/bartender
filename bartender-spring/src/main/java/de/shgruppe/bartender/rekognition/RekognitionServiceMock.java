package de.shgruppe.bartender.rekognition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import de.shgruppe.bartender.model.RekognitionResult;

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
		return rekognitionResult;
	}
}
