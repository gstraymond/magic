package fr.gstraymond.forge.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Pattern.*

import java.math.MathContext
import java.text.DecimalFormat

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.constants.Abilities
import fr.gstraymond.card.constants.PriceRange;
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
	
	void setDescription() {
		card.description = rawCard.description
		
		if (! card.description) {
			card.description = ''
		}
	}
	
	void setEditions() {
		card.editions = rawCard.setInfos.collect {
			parseEdition(it)
		}
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
		card.rarities = rawCard.setInfos.collect {
			parseRarity(it)
		}.unique()
	}
	
	void setPriceRanges() {
		def prices = rawCard.setInfos.collect {
			 getPrice(it)
		}
		card.priceRanges = PriceRange.ALL.findAll { priceRange ->
			prices.findAll { price ->
				priceRange.inf <= price && priceRange.sup > price 
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
