package de.shgruppe.bartender.model;

public class Ingredient
{
	private String shortName;
	private String readableName;


	public Ingredient(String shortName, String readableName)
	{
		this.shortName = shortName;
		this.readableName = readableName;
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

}
