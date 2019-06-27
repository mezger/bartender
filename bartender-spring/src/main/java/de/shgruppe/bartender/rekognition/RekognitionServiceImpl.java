package de.shgruppe.bartender.rekognition;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Emotion;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.util.IOUtils;
import de.shgruppe.bartender.model.RekognitionResult;
import de.shgruppe.bartender.model.WeightedEmotion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class RekognitionServiceImpl implements RekognitionService {

  @Override
  public RekognitionResult getEmotionsForImage(byte[] image) {

    AWSCredentials credentials;
    try {
      credentials = new ProfileCredentialsProvider("adminuser").getCredentials();
    } catch (Exception e) {
      throw new AmazonClientException(
          "Cannot load the credentials from the credential profiles file. "
              + "Please make sure that your credentials file is at the correct "
              + "location (/Users/userid/.aws/credentials), and is in a valid format.",
          e);
    }

    ByteBuffer imageBytes = null;
    DetectLabelsResult result = null;
    DetectFacesResult faceResult = null;

    imageBytes = ByteBuffer.wrap(image);

    AmazonRekognition rekognitionClient =
        AmazonRekognitionClientBuilder.standard()
            .withRegion(Regions.EU_WEST_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();

    DetectLabelsRequest request =
        new DetectLabelsRequest()
            .withImage(new Image().withBytes(imageBytes))
            .withMaxLabels(10)
            .withMinConfidence(77F);

    DetectFacesRequest facesRequest =
        new DetectFacesRequest().withImage(new Image().withBytes(imageBytes)).withAttributes(
            Attribute.ALL);

    try {
      faceResult = rekognitionClient.detectFaces(facesRequest);
      result = rekognitionClient.detectLabels(request);
      List<Label> labels = result.getLabels();
      List<FaceDetail> faces = faceResult.getFaceDetails();


      //Map Response to Rekognition Result
      RekognitionResult rekognitionResult = new RekognitionResult();

      Integer high = faces.get(0).getAgeRange().getHigh();
      Integer low = faces.get(0).getAgeRange().getLow();
      Integer alter = (high + low) / 2;

      List<Emotion> emotions = faces.get(0).getEmotions();
      List<WeightedEmotion> weightedEmotionsList = new ArrayList<>();
      for (Emotion emotion : emotions) {
        WeightedEmotion weightedEmotion = new WeightedEmotion();

        String emotionType = emotion.getType();
        Float confidence = emotion.getConfidence();

        switch (emotionType) {
          case "Happy":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.HAPPY);
            break;
          case "Surprised":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.SURPRISED);
            break;
          case "Disgusted":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.DISGUSTED);
            break;
          case "Sad":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.SAD);
            break;
          case "Calm":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.CALM);
            break;
          case "Angry":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.ANGRY);
            break;
          case "confused":
            weightedEmotion.setEmotion(de.shgruppe.bartender.model.Emotion.CONFUSED);
            break;
        }
        weightedEmotion.setWeight(confidence);
      }

      rekognitionResult.setAge(alter);

      return null;
  } catch (AmazonRekognitionException e) {
      e.printStackTrace();
    }

  return null;
  }
}
