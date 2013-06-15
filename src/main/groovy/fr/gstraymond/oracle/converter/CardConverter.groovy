package fr.gstraymond.oracle.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Pattern.*
import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.constants.Abilities
import fr.gstraymond.card.constants.Rarity
import fr.gstraymond.converter.CommonCardConverter
import fr.gstraymond.oracle.card.constants.Edition

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
			
			// dedupe rarity
			card.rarities = card.rarities.unique()
		}

		card
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
			setColor(BLACK)
			setColor(GREEN)
			setColor(BLUE)
			setColor(WHITE)
			setColor(RED)
		}
		setColorType()
	}
	
	void setColor(color) {
		if (card.castingCost.toLowerCase().contains(color.toLowerCase())) {
			card.colors += MAP_COLORS[color]
		}
	}
	
	void setColorType() {
		def colorSize = card.colors?.size()
		if (! colorSize) {
			card.colors += [UNCOLORED]
		} else if (colorSize == 1) {
			card.colors += [MONOCOLORED]
		} else {
			card.colors += [MULTICOLORED]
		}
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
		Edition.MAP[edition]
	}
	
	void setRarity() {
		card.rarities = editionsRarities.collect {
			parseRarity(it)
		}
	}
	
	def parseRarity(editionRarity) {
		def rarity = editionRarity.split('-')[1]
		Rarity.MAP[rarity]
	}

	// TODO : Auratog 1/2 TE - Rare, TSP - null	
	// TODO : disenchant Alpha - Common, Beta - Common, Unlimited - Common, Revised - Common, Fourth Edition - Common, Ice Age - Common, Mirage - Common, Fifth Edition - Common, Tempest - Common, Urza's Saga - Common, Six Edition - Common, Mercadian Masques - Common, Starter 2000 - Common, Seventh Edition - Common, Time Spiral - null
	void setPublications() {
		def publications = []
		card.editions.eachWithIndex { edition, index ->
			def rarity = card.rarities[index]
			publications += "$edition - $rarity"
		}
		card.publications = publications.join(', ')
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
