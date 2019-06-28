package de.shgruppe.bartender.emomapper.persistence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.shgruppe.bartender.emomapper.entities.IngredientEntity;
import de.shgruppe.bartender.model.Emotion;

public class RepoInitializer
{

	private static final String HAS_ALCOHOL = "yes";

	private static Logger LOG = LoggerFactory.getLogger(RepoInitializer.class);


	public static void initRepo(final String fileName, final IngredientRepository repository) throws IOException
	{
		LOG.info("Init Ingredients Repository from file: " + fileName);
		repository.deleteAll();
		CSVParser csvParser = CSVParser.parse(RepoInitializer.class.getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"), CSVFormat.DEFAULT.withDelimiter(';'));
		csvParser.forEach(entry -> processRow(entry, repository));
	}


	private static void processRow(CSVRecord entry, IngredientRepository repository) {
		try
		{
			LOG.debug("Add Ingredient '" + entry.get(0) + "'");
			repository.save(new IngredientEntity(
					entry.get(0),
					entry.get(1),
					entry.get(2),
					HAS_ALCOHOL.equalsIgnoreCase(entry.get(3))));
		}
		catch (Exception e)
		{
			LOG.error("Could not load CSVEntry: " + entry.toString(), e);
		}
	}


	protected static EnumSet<Emotion> parseEmotions(final String emotionsString)
	{
		String[] split = emotionsString.split(",");
		return EnumSet.copyOf(
				Arrays.asList(split)
				.stream()
				.map(Emotion::valueOf)
				.collect(Collectors.toSet()));
	}

}
