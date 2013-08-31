package fr.gstraymond.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Patterns.*

import java.util.List;

import fr.gstraymond.card.CommonRawCard
import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.constants.Color;
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
	
	List<String> calculateColors(String castingCost, List hiddenHints) {
		def colors = getCastingCostAsList(castingCost)
		
		hiddenHints?.findAll {
			it.contains('color indicator')
		}.each { colorIndicator ->
			Color.MAP_COLORS.entrySet().each { entry ->
				if (colorIndicator.contains(entry.value)) {
					colors += entry.key
				}
			}
		}
		
		def textColors = colors.collect { colorBlock ->
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
		def coloredColors = textColors - MAP_COLORS[LIFE] - MAP_COLORS[X]
		def colorSize = coloredColors.size()
		if (! colorSize) {
			textColors += UNCOLORED
		} else if (colorSize == 1) {
			textColors += MONOCOLORED
		} else {
			textColors += MULTICOLORED.replace('{X}', "$colorSize")
			
			// card is part of a guild ?
			if (GUILDS.find { colors.contains(it) }) {
				textColors += GUILD
			}
			
			// card is gold ?
			if (colors.findAll { (it - '2/').size() == 1 }.size() > 1) {
				textColors += GOLD
			}
		}
		
		textColors
	}
	
	
	def formatPublication(edition, rarity, price = '', picture = '') {
		if (!edition || !rarity) {
			throw new Exception("error with $edition - $rarity (${card.dump()}")
		}
		
		def formattedPrice = price ? ": ${price}€" : ''
		def formattedPicture = picture ? "${picture}" : ''
		"<li><a href='${formattedPicture}' title=\"${edition} - ${rarity}\">${edition} - ${rarity}${formattedPrice}</a></li>"
	}
	
	def formatPublications(publications) {
		def sortedPublications = publications.sort {
			it.split('>')[2]
		} 
		"<ul class='unstyled'>${sortedPublications.join('')}</ul>"
	}

	void setConvertedManaCost() {
		card.convertedManaCost = calculateCMC(card.castingCost)
	}
	
	void setColors() {
		card.colors = calculateColors(card.castingCost, card.hiddenHints)
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
			card.power = getPTSplit(rawCard.powerToughness)[0]
		}
	}
	
	void setToughness() {
		if (rawCard.powerToughness) {
			card.toughness = getPTSplit(rawCard.powerToughness)[1]
		}
	}
	
	def getPTSplit(powerToughness) {
		powerToughness.split('/')
	}
	
	
	def formatMatch(format, title, editions) {
		def formatMatch = true
		
		if (format.sets) {
			// editions must be included in set
			formatMatch = format.sets.count { editions.contains(it) } > 0
		}
		
		if (formatMatch && format.bannedCards) {
			// title must not be included in bannedCards
			formatMatch = format.bannedCards.count { title.equals(it) } == 0
		}
		
		formatMatch
	}
	
}
