package fr.gstraymond.forge.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Pattern.*

import java.math.MathContext
import java.text.DecimalFormat

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.constants.Abilities
import fr.gstraymond.card.constants.PriceRange
import fr.gstraymond.converter.CommonCardConverter
import fr.gstraymond.forge.card.constants.Edition

class CardConverter extends CommonCardConverter {
	Map pricesAsMap

	def mathcontext = new MathContext(1)
	def decimalFormat = new DecimalFormat('0.#')

	MagicCard parse() {
		if (! rawCard) {
			card = null
		} else {
			setTitle()
			setCastingCost()
			setConvertedManaCost()
			setColors()
			setType()
			setDescription()
			setPower()
			setToughness()
			setEditions()
			setRarity()
			setPriceRanges()
			setPublications()
			setAbilities()
		}

		card
	}

	void setConvertedManaCost() {
		card.convertedManaCost = calculateCMC(card.castingCost)
	}
	
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
	
	def calculateCMC(castingCost) {
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
	
	void setColors() {
		card.colors = calculateColors(card.castingCost)
	}
	
	def calculateColors(castingCost) {
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

	void setDescription() {
		card.description = rawCard.description

		if (! card.description) {
			card.description = ''
		}
	}

	void setEditions() {
		card.editions = rawCard.setInfos.collect { parseEdition(it) }
	}

	def parseEdition(editionRarityUrl) {
		def edition = parseEditionCode(editionRarityUrl)
		if (! Edition.MAP[edition]) {
			println "pb with $edition - ${card.title}"
		}
		Edition.MAP[edition]
	}

	def parseEditionCode(editionRarityUrl) {
		editionRarityUrl.split('\\|')[0]
	}

	void setRarity() {
		card.rarities = rawCard.setInfos.collect { parseRarity(it) }.unique()
	}

	void setPriceRanges() {
		def prices = rawCard.setInfos.collect { getPrice(it) }
		card.priceRanges = calculatePriceRanges(prices)
	}
	
	def calculatePriceRanges(prices) {
		if (! prices || prices.sum() == 0) {
			return ['No price']
		}
		
		PriceRange.ALL.findAll { priceRange ->
			prices.findAll { price ->
				priceRange.inf <= price && price < priceRange.sup
			}
		}.name
	}

	def parseRarity(editionRarityUrl) {
		editionRarityUrl.split('\\|')[1]
	}

	def parseUrl(editionRarityUrl) {
		editionRarityUrl.split('\\|')[2]
	}

	def getPrice(editionRarityUrl) {
		def edition = parseEditionCode(editionRarityUrl)
		def price = pricesAsMap["$edition - $card.title"]
		if (price) {
			new BigDecimal(price, mathcontext) / 100
		} else {
			0
		}
	}

	def parsePrice(editionRarityUrl) {
		def price = getPrice(editionRarityUrl)
		if (! price) {
			println "No price found for $editionRarityUrl - $card.title"
			''
		} else {
			decimalFormat.format(price)
		}
	}

	void setPublications() {
		def publications = rawCard.setInfos.collect {
			def price = parsePrice(it)
			if (price) {
				price = ": ${price}€"
			}

			"<li>${parseEdition(it)} - ${parseRarity(it)}${price}<br/>${parseUrl(it)}</li>"
		}
		card.publications = "<ul>${publications.join('')}</ul>"
	}

	void setAbilities() {
		Abilities.LIST.each {
			if (abilityMatch(card.description, it)) {
				card.abilities += it
			}
		}
	}

	boolean abilityMatch (text, ability) {
		text.toLowerCase().contains(ability.toLowerCase())
	}
}
