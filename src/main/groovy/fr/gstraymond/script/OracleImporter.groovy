package fr.gstraymond.script
import fr.gstraymond.oracle.importer.Importer

def path = 'src/main/resources/oracle/'

/** from http://www.yawgatog.com/resources/oracle/ */
//def fileName = 'All Sets-2013-07-12.txt' 
def fileName = 'test.txt'

new BaseImporter( 
	enableDebug: true, 
	enableIndex: false, 
	clearConfigure: false, 
	cards: new Importer().parseCards(path, fileName)
).launch()
