package de.shgruppe.bartender.emomapper.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IngredientEntity
{

	@Id
	private String shortName;
	private String readableName;
	private String emotion;
	private boolean alcohol;

	public IngredientEntity()
	{	}

	public IngredientEntity(final String shortName, final String readableName, final String emotion, final boolean alcohol)
	{
		this.shortName = shortName;
		this.readableName = readableName;
		this.emotion = emotion;
		this.alcohol = alcohol;
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


	public String getEmotion() {
		return emotion;
	}


	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}


	public boolean isAlcohol() {
		return alcohol;
	}


	public void setAlcohol(boolean alcohol) {
		this.alcohol = alcohol;
	}

}
