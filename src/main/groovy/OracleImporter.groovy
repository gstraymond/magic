import fr.gstraymond.es.ESIndexer
import fr.gstraymond.oracle.converter.CardConverter;
import fr.gstraymond.oracle.converter.FileConverter;
import fr.gstraymond.oracle.converter.RawCardConverter;

def path = 'src/main/resources/oracle/'
//def fileName = 'All Sets-2013-02-10.txt' // from http://www.yawgatog.com/resources/oracle/
def fileName = 'test.txt'

def fileConverter = new FileConverter(dumpFile: new File(path+fileName))
def cards = fileConverter.parse().collect {
	def rawCardConverter = new RawCardConverter(cardAsString: it)
	def rawCard = rawCardConverter.parse()
	def cardConverter = new CardConverter(rawCard: rawCard) 
	cardConverter.parse()
}

// remove null elements
cards = cards.findAll()

def enableDebug = true
def enableIndex = false
def clearConfigure = false

if (enableDebug) {
	cards.each {
		println '----'
		println it?.title
		println it?.abilities
		println it?.rarity
	}
}

if (clearConfigure) {
	def indexer = new ESIndexer()
	indexer.clear()
	indexer.configure()
}

if (enableIndex) {
	def indexer = new ESIndexer()
	indexer.clear()
	indexer.configure()
	cards.eachWithIndex { card, counter ->
		println "Indexing ${counter}/${cards.size()} : $card.title"
		indexer.index card
	}
}
