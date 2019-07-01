package de.shgruppe.bartender.rekognition;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class RekognitionExample {
  
  @Value("${accesskey}")
  private String accesskey;
  @Value("${secretkey}")
  private String secretkey;

  public void test() {
    String photo="Bild/angry.jpg";
    DetectFacesResult faceResult = null;

    File file = new File(
        getClass().getClassLoader().getResource("Bild/angry.jpg").getFile()
    );

    System.out.println(accesskey + " : " + secretkey);
    BasicAWSCredentials credentials = new BasicAWSCredentials(accesskey, secretkey);

    ByteBuffer imageBytes = null;
    try (InputStream inputStream = new FileInputStream(file)) {
      imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    } catch (IOException e) {
      System.out.println(e);
    }

    AmazonRekognition rekognitionClient =
        AmazonRekognitionClientBuilder.standard()
            .withRegion(Regions.EU_WEST_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();

    DetectLabelsRequest request = new DetectLabelsRequest()
        .withImage(new Image()
            .withBytes(imageBytes))
        .withMaxLabels(10)
        .withMinConfidence(77F);

    DetectFacesRequest facesRequest =
        new DetectFacesRequest().withImage(new Image().withBytes(imageBytes)).withAttributes(
            Attribute.ALL);


    try {

      DetectLabelsResult result = rekognitionClient.detectLabels(request);
      List<Label> labels = result.getLabels();
      faceResult = rekognitionClient.detectFaces(facesRequest);
      List<FaceDetail> faces = faceResult.getFaceDetails();

      System.out.println("Detected labels for " + photo);
      for (Label label: labels) {
        System.out.println(label.getName() + ": " + label.getConfidence().toString());
      }

      System.out.println("Detected FaceDetail for " + photo);
      for (FaceDetail faceDetail: faces) {
        List<Emotion> emotions = faceDetail.getEmotions();
        for(Emotion emotion : emotions) {
          System.out.println(emotion.getType() + ": " + emotion.getConfidence());
        }
      }

    } catch (AmazonRekognitionException e) {
      e.printStackTrace();
    }
  }

}
