package de.shgruppe.bartender.rekognition;

import de.shgruppe.bartender.model.RekognitionResult;

public interface RekognitionService
{
	public RekognitionResult getEmotionsForImage(byte[] image);
}
