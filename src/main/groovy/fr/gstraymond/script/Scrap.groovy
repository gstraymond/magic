package fr.gstraymond.script

import fr.gstraymond.scrap.Scraper
import groovy.json.JsonBuilder

def scrapedCards = new Scraper(
	languages: ['en', 'fr']
).scrap()

println "number of cards scraped : ${scrapedCards.size()}"

JsonBuilder builder = new JsonBuilder(scrapedCards)

new File('src/main/resources/scrap/scrapedCards.json').withWriter {
	 it << builder.toPrettyString()
}