package fr.gstraymond.script
import fr.gstraymond.forge.importer.Importer

def path = 'src/main/resources/forge/'

/** Forge version 1.3.9 */
/** http://cardforge.org/releases/releases/forge/forge/ */
/** prices are under ~/.cache/forge/db/all-prices.txt */
//def cardsFileName = 'cardsfolder.zip'
//def priceFileName = 'all-prices.txt' 
def cardsFileName = 'test.zip'
def priceFileName = 'test-prices.txt'

new BaseImporter(
	enableDebug: true, 
	enableIndex: false, 
	clearConfigure: false, 
	cards: new Importer().parseCards(path, cardsFileName, priceFileName)
).launch()

