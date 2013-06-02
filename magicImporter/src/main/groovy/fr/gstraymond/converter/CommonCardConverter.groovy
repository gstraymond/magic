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
	
	void setConvertedManaCost() {
		if (card.castingCost) {
			// compte les mana colorés
			card.convertedManaCost = countColoredMana() 
			// compte les mana bicolores
			card.convertedManaCost += countBiColoredMana()
			// compte les mana incolores
			def matches = UNCOLORED_CASTING_COST.matcher(card.castingCost)[0]
			if (matches[1]) {
				card.convertedManaCost += matches[1].toInteger()
			}
		}
	}
	
	def countColoredMana() {
		ALL_COLORS.collect { card.castingCost.count it }.sum()
	}
	
	def countBiColoredMana = {
		ALL_COLORS.collect { card.castingCost.count(it.toLowerCase()) / 2 }.sum()
	}
	
	void setColors() {
		if (card.castingCost) {
			setColor(BLACK, 'Black')
			setColor(GREEN, 'Green')
			setColor(BLUE, 'Blue')
			setColor(WHITE, 'White')
			setColor(RED, 'Red')
		}
		setColorType()
	}
	
	void setColor(colorPrefix, colorName) {
		if (card.castingCost.toLowerCase().contains(colorPrefix.toLowerCase())) {
			card.colors += colorName
		}
	}
	
	void setColorType() {
		def colorSize = card.colors?.size()
		if (! colorSize) {
			card.colors += ['Uncolored']
		} else if (colorSize == 1) {
			card.colors += ['Monocolored']
		} else {
			card.colors += ['Multicolored']
		}
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
