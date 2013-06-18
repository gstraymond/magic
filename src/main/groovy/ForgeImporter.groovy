import java.util.zip.ZipFile
import fr.gstraymond.elasticsearch.ESIndexer
import fr.gstraymond.forge.converter.CardConverter
import fr.gstraymond.forge.converter.FileConverter
import fr.gstraymond.forge.converter.RawCardConverter

def path = 'src/main/resources/forge/'

/** Forge version 1.3.9 */
/** http://cardforge.org/releases/releases/forge/forge/ */
/** prices are under ~/.cache/forge/db/all-prices.txt */
//def cardsFileName = 'cardsfolder.zip'
//def priceFileName = 'all-prices.txt' 
def cardsFileName = 'test.zip'
def priceFileName = 'test-prices.txt'

new Importer(
	enableDebug: false, 
	enableIndex: false, 
	clearConfigure: true, 
	cards: new Importer().parseCards(path, cardsFileName, priceFileName)
).launch()

