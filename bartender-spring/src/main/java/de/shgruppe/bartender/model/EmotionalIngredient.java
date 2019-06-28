package de.shgruppe.bartender.model;

public class EmotionalIngredient
{
	private String shortName;
	private String readableName;
	private String emotion;


	public EmotionalIngredient(String shortName, String readableName, String emotion)
	{
		this.shortName = shortName;
		this.readableName = readableName;
		this.emotion = emotion;
	}


	public String getShortName()
	{
		return shortName;
	}


	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}


	public String getReadableName()
	{
		return readableName;
	}


	public void setReadableName(String readableName)
	{
		this.readableName = readableName;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", shortName, readableName);
	}


	public String getEmotion() {
		return emotion;
	}


	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

}
