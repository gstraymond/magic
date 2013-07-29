package fr.gstraymond.oracle.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Patterns.*
import fr.gstraymond.oracle.card.RawCard;

class RawCardConverter {
	List cardAsString
	RawCard rawCard = new RawCard()
	
	@Override
	RawCard parse() {
		if (! cardAsString) {
			rawCard = null
		// TODO : handle vanguard / scheme and plane cards
		// TODO : double cards ex Rise // Fall
		} else if (	//cardAsString[0].contains(' // ') ||
					cardAsString[1].equals("Vanguard") ||
					cardAsString[1].equals("Phenomenon") ||
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
		def possibleCastingCost = cardAsString.first()
		if (VALID_CASTING_COST.matcher(possibleCastingCost).matches()) {
			rawCard.castingCost = transformCastingCost(possibleCastingCost)
			removeFirst()
		} else {
			println("No casting cost found for $possibleCastingCost for $VALID_CASTING_COST")
		}
	}
	
	def transformCastingCost(rawCastingCost) {
		def castingCost = []
		def inParenthesis = false
		def inParenthesisSymbol = ''
		rawCastingCost.each { symbol ->
			if ('('.equals(symbol)) {
				inParenthesis = true
				inParenthesisSymbol = ''
			}
			
			if (')'.equals(symbol)) {
				inParenthesis = false
				castingCost += inParenthesisSymbol
			}
			
			if (ALL_COLORS_SYMBOLS.contains(symbol.toUpperCase()) || symbol.isNumber()) {
				if (inParenthesis) {
					if (symbol.isNumber()) {
						inParenthesisSymbol += symbol.toUpperCase() + '/'
					} else {
						inParenthesisSymbol += symbol.toUpperCase()
					}
				} else if (symbol.isNumber() && castingCost.size() > 0 && castingCost.last().isNumber()) {
					castingCost += castingCost.pop() + symbol
				} else {
					castingCost += symbol.toUpperCase()
				}
			}
		}
		castingCost.join(' ')
	}
	
	void setDescription() {
		for(String line : cardAsString) {
			rawCard.description += line
		}
	}
}
