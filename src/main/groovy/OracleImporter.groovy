import fr.gstraymond.oracle.importer.Importer

def path = 'src/main/resources/oracle/'

/** from http://www.yawgatog.com/resources/oracle/ */
def fileName = 'All Sets-2013-05-03.txt' 
//def fileName = 'test.txt'

new BaseImporter(
	enableDebug: false, 
	enableIndex: true, 
	clearConfigure: false, 
	cards: new Importer().parseCards(path, fileName)
).launch()
