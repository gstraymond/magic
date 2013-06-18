package fr.gstraymond.forge.importer

import fr.gstraymond.forge.converter.CardConverter
import fr.gstraymond.forge.converter.FileConverter
import fr.gstraymond.forge.converter.RawCardConverter
import groovy.transform.CompileStatic

import java.util.zip.ZipFile


class Importer {
	def parseCards(String path, cardsFileName, priceFileName) {
		def now = System.currentTimeMillis()
		
		def fileConverter = new FileConverter(
			dumpZip: new ZipFile(path + cardsFileName),
			priceFile: new File(path + priceFileName)
		)
		
		def priceMap = fileConverter.parsePrice()
		def cards = fileConverter.parse().collect {
			def rawCardConverter = new RawCardConverter(cardAsString: it)
			def rawCard = rawCardConverter.parse()
			def cardConverter = new CardConverter(rawCard: rawCard, pricesAsMap: priceMap)
			cardConverter.parse()
		}
		
		println "$cards.size parsed in ${System.currentTimeMillis() - now}ms."
		
		cards
	}
}
