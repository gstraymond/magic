package fr.gstraymond.forge.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Patterns.*

import java.math.MathContext
import java.text.DecimalFormat

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.constants.Abilities
import fr.gstraymond.card.constants.PriceRange
import fr.gstraymond.converter.CommonCardConverter
import fr.gstraymond.forge.card.constants.Edition
import fr.gstraymond.forge.card.constants.Formats;

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
			setFormats()
		}

		card
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

	String parseEdition(String editionRarityUrl) {
		def edition = parseEditionCode(editionRarityUrl)
		if (! Edition.MAP[edition]) {
			throw new Exception("pb with $edition - ${card.title}")
		}
		Edition.MAP[edition]
	}

	def parseEditionCode(editionRarityUrl) {
		editionRarityUrl.split('\\|')[0]
	}
	
	def getRawEdition() {
		rawCard.setInfos.collect { parseEditionCode(it) }
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
			formatPublication(
				parseEdition(it),
				parseRarity(it),
				parsePrice(it),
				parseUrl(it)
			)
		}
		card.publications = formatPublications(publications)
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

	void setFormats() {
		card.formats = getFormats(card)
	}

	def getFormats(card) {
		Formats.ALL.findAll {
			formatMatch(it, card.title, rawEdition)
		}.name
	}
}
