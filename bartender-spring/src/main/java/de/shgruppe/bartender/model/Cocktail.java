package de.shgruppe.bartender.model;

import java.util.List;


public class Cocktail
{
	private String id;
	private String name;
	private String zubereitung;
	private String image;
	private String emotion;
	private boolean alkoholisch;
	private List<String> listIngredients;
	private RekognitionResult rekognitionResult;


	public Cocktail()
	{}


	public Cocktail(Cocktail cocktail)
	{
		this(cocktail.getId(), cocktail.getName(), cocktail.getZubereitung(), cocktail.getImage(), cocktail.getEmotion(), cocktail.isAlkoholisch(), cocktail.getListIngredients());
	}


	public Cocktail(String id, String name, String zubereitung, String image, String emotion, boolean alkoholisch, List<String> listIngredients)
	{
		super();
		this.id = id;
		this.name = name;
		this.zubereitung = zubereitung;
		this.image = image;
		this.emotion = emotion;
		this.alkoholisch = alkoholisch;
		this.listIngredients = listIngredients;
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getZubereitung()
	{
		return zubereitung;
	}


	public void setZubereitung(String zubereitung)
	{
		this.zubereitung = zubereitung;
	}


	public String getImage()
	{
		return image;
	}


	public void setImage(String image)
	{
		this.image = image;
	}


	public boolean isAlkoholisch()
	{
		return alkoholisch;
	}


	public void setAlkoholisch(boolean alkoholisch)
	{
		this.alkoholisch = alkoholisch;
	}


	public List<String> getListIngredients()
	{
		return listIngredients;
	}


	public void setListIngredients(List<String> listIngredients)
	{
		this.listIngredients = listIngredients;
	}


	public RekognitionResult getRekognitionResult()
	{
		return rekognitionResult;
	}


	public void setRekognitionResult(RekognitionResult rekognitionResult)
	{
		this.rekognitionResult = rekognitionResult;
	}


	public String getEmotion()
	{
		return emotion;
	}


	public void setEmotion(String emotion)
	{
		this.emotion = emotion;
	}

}
