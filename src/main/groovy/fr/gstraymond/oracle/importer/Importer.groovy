package fr.gstraymond.oracle.importer

import fr.gstraymond.oracle.converter.CardConverter
import fr.gstraymond.oracle.converter.FileConverter
import fr.gstraymond.oracle.converter.RawCardConverter
import fr.gstraymond.scrap.card.constants.Title;
import groovy.json.JsonSlurper

class Importer {
	
	def parseCards(path, fileName) {
		def now = System.currentTimeMillis()
		
		println "loading scraped translation / index / artist ..."
		def fullScrapedCards = new JsonSlurper().parseText(new File('src/main/resources/scrap/scrapedCards.json').text) 
		
		println "... done, parsing oracle ..."
		
		
		def fileConverter = new FileConverter(dumpFile: new File(path + fileName))
		def cards = fileConverter.parse().collect { List cardAsString ->
			def rawCardConverter = new RawCardConverter(cardAsString: cardAsString)
			def rawCard = rawCardConverter.parse()
			if (rawCard) {
				def scrapedCards = fullScrapedCards[rawCard.title]
				
				// title with special characters
				if (! scrapedCards) {
					def key = Title.MAP[rawCard.title]
					scrapedCards = fullScrapedCards[key]
				}
				
				// doubles cards
				if (! scrapedCards) {
					def key = fullScrapedCards.keySet().find {
						it.startsWith(rawCard.title + ' (')
					}
					scrapedCards = fullScrapedCards[key]
				}
				
				def cardConverter = new CardConverter(
					rawCard: rawCard,
					scrapedCards: scrapedCards
				)
				cardConverter.parse()
			}
		}
		
		println "... $cards.size parsed in ${System.currentTimeMillis() - now}ms."
		
		cards
	}
}
