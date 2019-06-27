package de.shgruppe.bartender.model;

import java.util.List;

public class Cocktail
{
	// private static Logger log = LoggerFactory.getLogger(Cocktail.class);

	String id;
	String name;
	String zubereitung;
	String image;
	boolean alkoholisch;
	List<Ingredient> listIngredients;
}
