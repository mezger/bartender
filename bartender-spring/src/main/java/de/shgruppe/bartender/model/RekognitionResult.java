package de.shgruppe.bartender.model;

import java.util.List;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Label;


public class RekognitionResult
{
	private int age;
	private List<WeightedEmotion> emotions;
	private List<Label> labelList;
	private List<FaceDetail>faceList;


	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public List<WeightedEmotion> getEmotions() {
		return emotions;
	}

	public void setEmotions(List<WeightedEmotion> emotions) {
		this.emotions = emotions;
	}

  public List<Label> getLabelList() {
    return labelList;
  }

  public void setLabelList(List<Label> labelList) {
    this.labelList = labelList;
  }

  public List<FaceDetail> getFaceList() {
    return faceList;
  }

  public void setFaceList(List<FaceDetail> faceList) {
    this.faceList = faceList;
  }



}
