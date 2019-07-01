package de.shgruppe.bartender.model;

public class WeightedEmotion {

	private Emotion emotion;
	private double weight;

	public WeightedEmotion() {

	}

	public WeightedEmotion(Emotion emotion, double weight){
		this.emotion = emotion;
		this.weight = weight;
	}

	public Emotion getEmotion() {
		return emotion;
	}

	public void setEmotion(Emotion emotion) {
		this.emotion = emotion;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
