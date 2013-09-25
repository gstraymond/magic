import fr.gstraymond.scrap.card.constants.Edition
import groovy.json.JsonBuilder

def scrapedCards = new fr.gstraymond.scrap.Scraper(
	languages: ['en', 'fr']
).scrap()

println "number of cards scraped : ${scrapedCards.size()}"

JsonBuilder builder = new JsonBuilder(scrapedCards)

new File('src/main/resources/scrap/scrapedCards.json').withWriter {
	 it << builder.toPrettyString()
}