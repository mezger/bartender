package de.shgruppe.bartender.rekognition;

import com.amazonaws.services.rekognition.model.FaceDetail;
import de.shgruppe.bartender.model.RekognitionResult;
import java.util.List;

public interface RekognitionService
{
	public RekognitionResult getEmotionsForImage(byte[] image);
//	public List<FaceDetail> getFaceDetailsForImage(byte[] image);
}
