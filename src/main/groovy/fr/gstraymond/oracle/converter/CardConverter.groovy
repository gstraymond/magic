package fr.gstraymond.oracle.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Patterns.*

import java.util.List;

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.constants.Abilities
import fr.gstraymond.card.constants.Rarity
import fr.gstraymond.converter.CommonCardConverter
import fr.gstraymond.oracle.card.constants.Edition
import fr.gstraymond.oracle.card.constants.Formats;

class CardConverter extends CommonCardConverter {

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
			setPublications()
			setAbilities()
			setFormats()
			
			// dedupe rarity
			card.rarities = card.rarities.unique()
		}

		card
	}
	
	void setDescription() {
		card.description = rawCard.description.join('\n')
	}
	
	void setEditions() {
		card.editions = editionsRarities.collect {
			parseEdition(it)
		}
	}
	
	List getEditionsRarities() {
		def editionsRarities = []
		def editionRarity = rawCard.editionRarity
		if (editionRarity.contains(',')) {
			editionsRarities = editionRarity.split(',').collect {
				it.trim()
			}
		} else {
			editionsRarities += editionRarity
		}
		editionsRarities
	}
	
	def parseEdition(editionRarity) {
		def edition = editionRarity.split('-')[0]
		if (! Edition.MAP[edition]) {
			throw new Exception("pb with $edition - ${card.title}")
		}
		Edition.MAP[edition]
	}
	
	def getRawEdition() {
		editionsRarities.collect { it.split('-')[0] }
	}
	
	void setRarity() {
		card.rarities = editionsRarities.collect {
			parseRarity(it)
		}
	}
	
	def parseRarity(editionRarity) {
		def rarity = editionRarity.split('-')[1]
		if (! Rarity.MAP[rarity]) {
			throw new Exception("pb with $rarity - ${card.title}")
		}
		Rarity.MAP[rarity]
	}

	void setPublications() {
		def publications = []
		card.editions.eachWithIndex { edition, index ->
			def rarity = card.rarities[index]
			publications += formatPublication(edition, rarity)
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
