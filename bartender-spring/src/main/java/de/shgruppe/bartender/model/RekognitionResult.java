package de.shgruppe.bartender.model;

import java.util.EnumSet;


public class RekognitionResult
{
	private EnumSet<Emotion> emotions;
	private int age;
	private WeightedEmotion weightedEmotion;


	public EnumSet<Emotion> getEmotions()
	{
		return emotions;
	}


	public void setEmotions(EnumSet<Emotion> emotions)
	{
		this.emotions = emotions;
	}


	public int getAge()
	{
		return age;
	}


	public void setAge(int age)
	{
		this.age = age;
	}

	public WeightedEmotion getWeightedEmotion() {
		return weightedEmotion;
	}

	public void setWeightedEmotion(WeightedEmotion weightedEmotion) {
		this.weightedEmotion = weightedEmotion;
	}

}
