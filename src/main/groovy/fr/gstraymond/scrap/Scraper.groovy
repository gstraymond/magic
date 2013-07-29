package fr.gstraymond.scrap

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import fr.gstraymond.scrap.card.ScrapedCard
import fr.gstraymond.scrap.card.constants.Edition;

class Scraper {

	String host = 'magiccards.info'
	List languages = []
	
	final cardExpression = 'table[cellpadding=3] tr'
	final editionExpression = 'ul li ul li'

	Map<String, List<ScrapedCard>> scrap() {
		Map<String, List<ScrapedCard>> scrapedCards = [:]

		scrapEditions()
		
		Edition.editionsMap.keySet().each { edition ->
			def duplicatedCardByLang = languages.collect { language ->
				
				def url = "http://${host}/${edition}/${language}.html"
				getElements(url).collect {
					buildScrapedCard(it, edition, language)
				}
				
			}
			
			def editionCards = mergeCards(duplicatedCardByLang.flatten())
			
			editionCards.each { it
				if (scrapedCards[it.title]) {
					scrapedCards.get(it.title).add(it)
				} else {
					scrapedCards.put(it.title, [it])
				}
			}
		}

		scrapedCards
	}
	
	void scrapEditions() {
		def url = "http://${host}/sitemap.html"
		
		println "scraping $url ..."
		
		Document doc = Jsoup.connect(url).get();
		
		Elements tables = doc.select("table")
		
		if (tables.isEmpty()) {
			throw new Exception("No results found for : url '$url' and expression 'table' ")
		}
		
		Elements elements = tables[1].select(editionExpression)
		
		if (elements.isEmpty()) {
			throw new Exception("No results found for : url '$url' and expression '$editionExpression' ")
		}

		Properties props = new Properties()
		File propsFile = new File(Edition.editionsPropertiesPath)
		elements.each {
			def code = it.getElementsByTag('small')[0].text()
			def name = it.getElementsByTag('a')[0].text()
			props.setProperty(code, name)
		}
		props.store(propsFile.newWriter(), null)
	}

	def getElements(url) {
		println "scraping $url ..."
		
		Document doc = Jsoup.connect(url).get();

		Elements elements = doc.select(cardExpression)

		if (elements.isEmpty()) {
			println "No results found for : url '$url' and expression '$cardExpression' "
			return []
		}

		elements.tail()
	}

	def buildScrapedCard(element, edition, language) {
		def tds = element.getElementsByTag('td')

		def card = new ScrapedCard(
			collectorNumber: tds[0].text(),
			rarity: tds[4].text(),
			artist: tds[5].text(),
			edition: edition
		)
		
		if (language == 'en') {
			card.title = tds[1].text()
		}
		
		if (language == 'fr') {
			card.frenchTitle = tds[1].text()
		}
		
		card
	}
	
	def mergeCards(duplicatedCardByLang) {
		def mergedCards = []
		def uniqueIds = [] as Set
		
		duplicatedCardByLang.each { dupCard ->
			if (uniqueIds.add(dupCard.uniqueId)) {
				mergedCards += dupCard
			} else {
				def card = mergedCards.find { mergeCard ->
					mergeCard.uniqueId == dupCard.uniqueId
				}
				
				if (! card.title) {
					card.title = dupCard.title
					card.edition = dupCard.edition
				} else {
					card.frenchTitle = dupCard.frenchTitle
				}
			}
		}
		mergedCards
	}
}