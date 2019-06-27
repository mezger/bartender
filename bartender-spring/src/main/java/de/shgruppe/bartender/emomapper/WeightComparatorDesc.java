package de.shgruppe.bartender.emomapper;

import java.util.Comparator;

import de.shgruppe.bartender.model.WeightedEmotion;

class WeightComparatorDesc implements Comparator<WeightedEmotion> {

	@Override
	public int compare(WeightedEmotion o1, WeightedEmotion o2) {
		if (o1.getWeight() > o2.getWeight())
		{
			return -1;
		}
		else if (o1.getWeight() < o2.getWeight())
		{
			return 1;
		}
		return 0;
	}

}
