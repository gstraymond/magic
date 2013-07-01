package fr.gstraymond.script
import fr.gstraymond.forge.importer.Importer as ForgeImporter
import fr.gstraymond.oracle.importer.Importer as OracleImporter


/** http://cardforge.org/releases/releases/forge/forge/ */
def forgePath = 'src/main/resources/forge/'
def cardsFileName = 'cardsfolder.zip'
def priceFileName = 'all-prices.txt'

/** from http://www.yawgatog.com/resources/oracle/ */
def oraclePath = 'src/main/resources/oracle/'
def fileName = 'All Sets-2013-05-03.txt'

new BaseImporter(
	enableIndex: true,
	cards: getCards(forgePath, cardsFileName, priceFileName, oraclePath, fileName)
).launch()

println 'Done indexing !'

def getCards(forgePath, cardsFileName, priceFileName, oraclePath, fileName) {
	println 'Importing forge cards'
	def cards = new ForgeImporter().parseCards(forgePath, cardsFileName, priceFileName)
	Set forgeCardTitles = cards*.title as Set
	
	println 'Importing oracle cards'
	new OracleImporter().parseCards(oraclePath, fileName).each {
		if (! forgeCardTitles.contains(it?.title)) {
			cards += it
		}
	}
	
	println 'Done importing... indexing'
	
	cards
}