package fr.gstraymond.oracle.converter

import static fr.gstraymond.card.constants.Pattern.*
import fr.gstraymond.oracle.card.RawCard;

class RawCardConverter {
	List cardAsString
	RawCard rawCard = new RawCard()
	
	@Override
	RawCard parse() {
		if (! cardAsString) {
			rawCard = null
		// TODO : handle vanguard / scheme and plane cards
		} else if (	cardAsString[1].equals("Vanguard") ||
					cardAsString[1].endsWith("Scheme") ||
					cardAsString[1].startsWith("Plane -- ")) {
			println "card skipped: ${cardAsString.join(' - ')}"
			rawCard = null
		} else {
			setTitle()
			setEditionRarity()
			setCastingCost()
			setType()
			setPowerToughness()
			setDescription()
		}
		rawCard
	}
	
	def removeFirst() {
		cardAsString = cardAsString.tail()
	}
	
	def getFirstAndRemove() {
		def first = cardAsString.first() 
		removeFirst()
		first
	}
	
	def getLastAndRemove() {
		cardAsString.pop()
	}
	
	void setTitle() {
		rawCard.title = getFirstAndRemove()
	}
	
	void setEditionRarity() {
		rawCard.editionRarity = getLastAndRemove()
	}
	
	void setType() {
		rawCard.type = getFirstAndRemove()
	}
	
	void setPowerToughness() {
		if (rawCard.type.contains("Creature")) {
			rawCard.powerToughness = getFirstAndRemove()
		}
	}
	
	void setCastingCost() {
		def possibleCastingCost = formatCastingCost(cardAsString.first())
		// TODO : cas Desecrator Hag 2(b/g)(b/g)
		if (VALID_CASTING_COST.matcher(possibleCastingCost).matches()) {
			rawCard.castingCost = possibleCastingCost
			removeFirst()
		}
	}
	
	def formatCastingCost(castingCost) {
		castingCost.replaceAll('\\(', '')	.replaceAll('/', '').replaceAll('\\)', '')
	}
	
	void setDescription() {
		for(String line : cardAsString) {
			rawCard.description += line
		}
	}
}
