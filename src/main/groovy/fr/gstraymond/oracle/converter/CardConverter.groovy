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
	
	def parseEditionRarity(editionRarity) {
		editionRarity.split(',').collect { 
			def edRar = it.trim().split('-')
			def editionCode = edRar[0]
			editionCode = translatedSets[editionCode] ?: editionCode
			def edition = fr.gstraymond.oracle.card.constants.Edition.MAP[editionCode]
			if (! edition) {
				throw new Exception("No edition found for $editionCode ($rawCard.title)")
			}
			
			[
				editionCode: editionCode,
				edition: edition,
				rarityCode: edRar[1],
			]
		}
	}
	
	def translatedSets = [
		HL: 'HM',
		AI: 'AL',
		P1: 'PO',
		S00 : 'P4',
		S99 : 'P3',
		UD : 'CG',
		UL : 'GU',
		US : 'UZ',
		P3K : 'PK',
		A : '1E',
		B : '2E',
		U : '2U',
		RV : '3E'
	]

	void setPublications() {
		def editionRarities = parseEditionRarity(rawCard.editionRarity)
		def publications = scrapedCards.collect {
			def pictureUrl = "http://magiccards.info/scans/en/${it.edition}/${it.collectorNumber}.jpg"
			def edition = parseEdition(it.edition)
			
			if (specialSets[it.edition]) {
				new Publication(
					edition:  edition,
					editionCode: it.edition,
					stdEditionCode: specialSets[it.edition],
					rarity: it.rarity,
					rarityCode: it.rarity[0].toUpperCase(),
					image: pictureUrl
				)
			} else {
				def editionRarity = editionRarities.find { it.edition == edition }
				if (!editionRarity) {
					println "No edition rarity found for card $rawCard.title with edition $edition and $editionRarities"
				}
				
				new Publication(
					edition:  edition,
					editionCode: it.edition,
					stdEditionCode: editionRarity?.editionCode,
					rarity: it.rarity,
					rarityCode: editionRarity?.rarityCode,
					image: pictureUrl
				)
			}
		}
		card.publications = sortPublications(publications)
	}
	
	def specialSets = [
		med : 'MED',
		me2 : 'ME2',
		me3 : 'ME3',
		me4 : 'ME4',
		tsts : 'TSB',
		mma: 'MMA',
		br: 'BR',
		bd: 'BD',
		cma: 'CM1',
		ddh: 'DDH',
		dvd: 'DDC',
		evg: 'EVG',
		gvl: 'DDD',
		ddl: 'DDL',
		ddj: 'DDJ',
		jvc: 'DD2',
		ddf: 'DDF',
		ddg: 'DDG',
		pvc: 'DDE',
		ddk: 'DDK',
		ddi: 'DDI',
		fvd: 'DRB',
		fve: 'V09',
		fvl: 'V11',
		v12: 'V12',
		fvr: 'V10',
		v13: 'V13',
		pc2: 'PC2',
		pd2: 'PD2',
		pd3: 'PD3',
		pds: 'H09',
		ug: 'UG',
		uh: 'UNH',
		st2k: 'P4'
	]
	
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
