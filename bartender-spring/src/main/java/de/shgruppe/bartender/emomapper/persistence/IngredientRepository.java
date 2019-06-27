package de.shgruppe.bartender.emomapper.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.shgruppe.bartender.emomapper.entities.IngredientEntity;

public interface IngredientRepository extends CrudRepository<IngredientEntity, String>
{

	public List<IngredientEntity> findByEmotion(String emotion);

	public List<IngredientEntity> findByEmotionAndAlcohol(String emotion, boolean alcohol);

}
