package fr.gstraymond.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Patterns.*

import java.util.List;

import fr.gstraymond.card.CommonRawCard
import fr.gstraymond.card.MagicCard
import groovy.transform.CompileStatic;

abstract class CommonCardConverter {
	CommonRawCard rawCard
	MagicCard card = new MagicCard()
	
	
	def getCastingCostAsList(castingCost) {
		def castingCostChars = []
		
		if (castingCost) {
			if (castingCost.contains(' ')) {
				castingCostChars = castingCost.split(' ')
			} else {
				castingCostChars += castingCost
			}
		}
		
		castingCostChars
	}
	
	int calculateCMC(String castingCost) {
		def getCastingCostAsList = getCastingCostAsList(castingCost)
		if(! getCastingCostAsList) {
			return 0
		}
		
		getCastingCostAsList.sum {
			// X
			if (X.equals(it)) {
				return 0
			}
			
			// 1, 3, 15...
			if(it.isNumber()) {
				return it.toInteger()
			}

			// B, G, U...
			if (ALL_COLORS_SYMBOLS.contains(it)) {
				return 1
			}
			
			// WG, BU...
			if (it.size() == 2) {
				return 1
			}
			
			// 2/W, 2/G...
			if (it.contains("2/")) {
				return 2
			}
		}
	}
	
	List<String> calculateColors(String castingCost) {
		def colors = getCastingCostAsList(castingCost).collect { colorBlock ->
			// X
			// B, G, U...
			// WG, BU...
			// PU...
			// 2/W, 2/G...
			colorBlock.findAll {
				ALL_COLORS_SYMBOLS.contains(it)
			}.collect {
				MAP_COLORS[it]
			}
		}.flatten().unique()
		
		// counting only colored symbols
		def colorSize = (colors - MAP_COLORS[LIFE] - MAP_COLORS[X]).size()
		if (! colorSize) {
			colors += UNCOLORED
		} else if (colorSize == 1) {
			colors += MONOCOLORED
		} else {
			colors += MULTICOLORED
		}
		
		colors
	}
	
	def formatPublication(edition, rarity, price = '', picture = '') {
		def formattedPrice = price ? ": ${price}€" : ''
		def formattedPicture = picture ? "<br/>${picture}" : ''
		"<li>${edition} - ${rarity}${formattedPrice}${formattedPicture}</li>"
	}
	
	def formatPublications(publications) {
		"<ul>${publications.join('')}</ul>"
	}

	void setConvertedManaCost() {
		card.convertedManaCost = calculateCMC(card.castingCost)
	}
	
	void setColors() {
		card.colors = calculateColors(card.castingCost)
	}
	
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
