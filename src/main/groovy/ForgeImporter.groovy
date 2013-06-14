import java.util.zip.ZipFile
import fr.gstraymond.elasticsearch.ESIndexer
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
println "$cards.size parsed in ${System.currentTimeMillis() - now}ms."

new Importer(
	enableDebug: false, 
	enableIndex: false, 
	clearConfigure: false, 
	cards: cards
).launch()