package fr.gstraymond.oracle.importer

import fr.gstraymond.oracle.converter.CardConverter
import fr.gstraymond.oracle.converter.FileConverter
import fr.gstraymond.oracle.converter.RawCardConverter
import groovy.transform.CompileStatic

@CompileStatic
class Importer {
	
	def parseCards(String path, String fileName) {
		def now = System.currentTimeMillis()
		
		def fileConverter = new FileConverter(dumpFile: new File(path + fileName))
		def cards = fileConverter.parse().collect { List cardAsString ->
			def rawCardConverter = new RawCardConverter(cardAsString: cardAsString)
			def rawCard = rawCardConverter.parse()
			def cardConverter = new CardConverter(rawCard: rawCard)
			cardConverter.parse()
		}
		
		println "$cards.size parsed in ${System.currentTimeMillis() - now}ms."
		
		cards
	}
}
