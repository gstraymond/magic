import java.util.zip.ZipFile

import fr.gstraymond.es.ESIndexer
import fr.gstraymond.forge.converter.CardConverter
import fr.gstraymond.forge.converter.FileConverter
import fr.gstraymond.forge.converter.RawCardConverter

def path = 'src/main/resources/forge/'
//def cardsFileName = 'cardsfolder.zip' // http://cardforge.org/releases/releases/forge/forge/1.3.9/forge-1.3.9.tar.bz2
//def priceFileName = 'all-prices.txt' // with forge, under forge-1.3.9/res/quest
def cardsFileName = 'test.zip'
def priceFileName = 'test-prices.txt'

def now = System.currentTimeMillis()
def fileConverter = new FileConverter(dumpZip: new ZipFile(path+cardsFileName), priceFile: new File(path+priceFileName))
def priceMap = fileConverter.parsePrice()
def cards = fileConverter.parse().collect {
	def rawCardConverter = new RawCardConverter(cardAsString: it)
	def rawCard = rawCardConverter.parse()
	def cardConverter = new CardConverter(rawCard: rawCard, pricesAsMap: priceMap) 
	cardConverter.parse()
}

// remove null elements and empty elements
cards = cards.findAll {	it && it.title }
println "$cards.size parsed in ${System.currentTimeMillis() - now}ms."

def enableDebug = false
def enableIndex = false
def clearConfigure = true

if (enableDebug) {
	cards.each {
		println '----'
		println it?.title
		println it?.editions
		println it?.priceRanges
		println it?.publications
	}
}

if (enableIndex || clearConfigure) {
	def indexer = new ESIndexer()
	indexer.clear()
	indexer.configure()
	if (enableIndex) {
		cards.eachWithIndex { card, counter ->
			println "Indexing ${counter+1}/${cards.size()} : $card.title"
			indexer.index card
		}
	}
}
