import fr.gstraymond.forge.importer.Importer as ForgeImporter
import fr.gstraymond.oracle.importer.Importer as OracleImporter


def forgePath = 'src/main/resources/forge/'
def cardsFileName = 'cardsfolder.zip'
def priceFileName = 'all-prices.txt'

def oraclePath = 'src/main/resources/oracle/'
def fileName = 'All Sets-2013-05-03.txt'

println 'Importing forge cards'
def forgeCards = new ForgeImporter().parseCards(forgePath, cardsFileName, priceFileName)
Set forgeCardTitles = forgeCards*.title as Set

println 'Importing oracle cards'
new OracleImporter().parseCards(oraclePath, fileName).each {
	if (! forgeCardTitles.contains(it?.title)) {
		forgeCards += it
	}
}

println 'Done !'

new Importer(
	enableDebug: false,
	enableIndex: true,
	clearConfigure: false,
	cards: forgeCards
).launch()