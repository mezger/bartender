package de.shgruppe.bartender.model;

public class Ingredient
{
	private int id;
	private String shortName;
	private String readableName;


	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
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
