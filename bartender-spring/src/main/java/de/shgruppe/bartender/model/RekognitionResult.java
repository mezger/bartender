package de.shgruppe.bartender.model;

import java.util.List;


public class RekognitionResult
{
	private int age;
	private List<WeightedEmotion> emotions;


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



}
