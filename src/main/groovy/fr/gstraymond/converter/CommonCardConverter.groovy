package fr.gstraymond.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Pattern.*
import fr.gstraymond.card.CommonRawCard
import fr.gstraymond.card.MagicCard


class CommonCardConverter {
	CommonRawCard rawCard
	MagicCard card = new MagicCard()
	
	void setTitle() {
		card.title = rawCard.title
	}
	
	void setCastingCost() {
		card.castingCost = rawCard.castingCost
	}
	
	void setType() {
		card.type = rawCard.type
	}
	
	void setPower() {
		if (rawCard.powerToughness) {
			card.power = rawCard.powerToughness.split('/')[0]
		}
	}
	
	void setToughness() {
		if (rawCard.powerToughness) {
			card.toughness = rawCard.powerToughness.split('/')[1]
		}
	}

}
