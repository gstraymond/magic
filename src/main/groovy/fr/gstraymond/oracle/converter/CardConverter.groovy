package fr.gstraymond.oracle.converter

import static fr.gstraymond.card.constants.Color.*
import static fr.gstraymond.card.constants.Patterns.*

import java.util.List;

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.Publication;
import fr.gstraymond.card.constants.Abilities
import fr.gstraymond.card.constants.Rarity
import fr.gstraymond.converter.CommonCardConverter
import fr.gstraymond.scrap.card.ScrapedCard
import fr.gstraymond.scrap.card.constants.Edition
import fr.gstraymond.scrap.card.constants.Formats;

class CardConverter extends CommonCardConverter {
	
	List<ScrapedCard> scrapedCards

	MagicCard parse() {
		if (! scrapedCards) {
			System.err.println "No scraped card found for ${rawCard.title}"
		} else if (! rawCard) {
			card = null
		} else {
			setTitle()
			setFrenchTitle()
			setCastingCost()
			setConvertedManaCost()
			setDescription()
			setHiddenHints()
			setColors()
			setType()
			setPower()
			setToughness()
			setEditions()
			setRarity()
			setPublications()
			setAbilities()
			setFormats()
			setArtists()
			setDevotions()
			
			// dedupe rarity
			card.rarities = card.rarities.unique()
		}

		card
	}
	
	@Override
	void setTitle() {
		card.title = scrapedCards.get(0).title
	}
	
	void setHiddenHints() {
		if (card.description.contains('[') && card.description.concat(']')) {
			def start = card.description.indexOf('[') + 1
			def end = card.description.size() - 2
			card.hiddenHints = card.description[start..end].split('\\.')
		}
	}
	
	void setDescription() {
		card.description = rawCard.description.join('\n').replaceAll('\\)\\{\\(', ')}{(')
	}
	
	void setEditions() {
		card.editions = scrapedCards.collect {
			parseEdition(it.edition)
		}
	}
	
	def parseEdition(edition) {
		def fullEdition = Edition.editionsMap[edition]
		
		if (! fullEdition) {
			throw new Exception("pb with $edition - ${card.title}")
		}
		
		fullEdition
	}
	
	def getRawEditions() {
		scrapedCards*.edition
	}
	
	void setRarity() {
		card.rarities = scrapedCards*.rarity?.unique()
	}

	void setPublications() {
		def publications = scrapedCards.collect {
			def pictureUrl = "http://magiccards.info/scans/en/${it.edition}/${it.collectorNumber}.jpg"
			new Publication(
				edition:  parseEdition(it.edition),
				editionCode: it.edition,
				rarity: it.rarity,
				image: pictureUrl
			)
		}
		card.publications = sortPublications(publications)
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
			formatMatch(it, card.title, rawEditions)
		}.name
	}
	
	void setFrenchTitle() {
		card.frenchTitle = scrapedCards.find {
			it.frenchTitle
		}?.frenchTitle
	}
	
	void setArtists() {
		card.artists = scrapedCards*.artist.unique()
	}
}
