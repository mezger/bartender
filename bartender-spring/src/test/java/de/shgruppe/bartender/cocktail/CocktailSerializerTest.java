package de.shgruppe.bartender.cocktail;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.shgruppe.bartender.model.Cocktail;

public class CocktailSerializerTest {

	private final XmlMapper xmlMapper = new XmlMapper();


	@Test
	public void testCreateCocktailXml() throws Exception {
		Cocktail c = new Cocktail();
		c.setAlkoholisch(true);
		c.setId("Caipirinha");
		c.setImage("caipi.png");
		c.setListIngredients(Arrays.asList("Limette", "Rohrzucker", "Limettensirup", "Curshed Ice", "Cachaca"));
		c.setName("Caipirinha");
		c.setZubereitung("Limetten waschen und achteln.\r\n4 Achtel Limetten mit einem Esslöffel Rohrzucker in einem Glas zerdrücken.\r\nLimettensirup hinzufügen.\r\nMit Crushed Ice auffüllen und Cachaca darüberleeren. Umrühren");
		String xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
		System.out.println(xml);
		assertNotNull(xml);
	}

}
