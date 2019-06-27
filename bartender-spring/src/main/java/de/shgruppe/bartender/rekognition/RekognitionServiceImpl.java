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
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.util.IOUtils;
import de.shgruppe.bartender.model.RekognitionResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
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
    InputStream inputStream = null;
//    try {
      //inputStream = new FileInputStream(new File(photo));
//      imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(picture.getInputStream()));
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

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

    return null;
  } catch (AmazonRekognitionException e) {
      e.printStackTrace();
    }

  return null;
  }
}
