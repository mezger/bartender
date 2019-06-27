package de.shgruppe.bartender;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.shgruppe.bartender.model.Cocktail;

@RestController
public class BartenderController
{
	private static Logger log = LoggerFactory.getLogger(BartenderController.class);

	@Autowired
	private BartenderService bartenderService;


	@CrossOrigin(origins = "*")
	@PostMapping(value="/cocktailForImage"/*,  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE*/)
	public Cocktail cocktailForImage(@RequestParam(value="picture")MultipartFile picture) throws IOException
	{
		return bartenderService.getCocktailForImage(picture);
	}
}
