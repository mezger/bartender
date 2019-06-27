package de.shgruppe.bartender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BartenderController
{
	private static Logger log = LoggerFactory.getLogger(BartenderController.class);

	@GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String testle()
	{
		return "{testle=ok}";
	}
}
