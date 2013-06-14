import fr.gstraymond.elasticsearch.ESIndexer
import fr.gstraymond.oracle.converter.CardConverter
import fr.gstraymond.oracle.converter.FileConverter
import fr.gstraymond.oracle.converter.RawCardConverter

def path = 'src/main/resources/oracle/'
//def fileName = 'All Sets-2013-02-10.txt' // from http://www.yawgatog.com/resources/oracle/
def fileName = 'test.txt'

def now = System.currentTimeMillis()
def fileConverter = new FileConverter(dumpFile: new File(path+fileName))
def cards = fileConverter.parse().collect {
	def rawCardConverter = new RawCardConverter(cardAsString: it)
	def rawCard = rawCardConverter.parse()
	def cardConverter = new CardConverter(rawCard: rawCard) 
	cardConverter.parse()
}

println "$cards.size parsed in ${System.currentTimeMillis() - now}ms."

new Importer(
	enableDebug: false, 
	enableIndex: false, 
	clearConfigure: false, 
	cards: cards
).launch()