import fr.gstraymond.oracle.converter.CardConverter
import fr.gstraymond.oracle.converter.FileConverter
import fr.gstraymond.oracle.converter.RawCardConverter
import groovy.transform.CompileStatic

def path = 'src/main/resources/oracle/'

/** from http://www.yawgatog.com/resources/oracle/ */
def fileName = 'All Sets-2013-05-03.txt' 
//def fileName = 'test.txt'

new Importer(
	enableDebug: false, 
	enableIndex: true, 
	clearConfigure: false, 
	cards: new Importer().parseCards(path, fileName)
).launch()
