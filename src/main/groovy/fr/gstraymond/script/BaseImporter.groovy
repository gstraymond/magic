package fr.gstraymond.script
import java.util.concurrent.ConcurrentSkipListMap.Index;

import fr.gstraymond.elasticsearch.ESIndexer

class BaseImporter {
	
	def enableDebug = false
	def enableIndex = false
	def clearConfigure = false
	def host = 'localhost'
	def cards = []
	
	def launch() {
		clean()
		debug()
		index()
	}
	
	def clean() {		
		// remove null elements and empty elements
		cards = cards.findAll {	it && it.title }
	}
	
	def debug() {
		if (enableDebug) {
			cards.each {
				println '----'
				println "title        ${it?.title}"
				println "frenchTitle  ${it?.frenchTitle}"
				println "castingCost  ${it?.castingCost}"
				println "colors       ${it?.colors}"
				println "CMC          ${it?.convertedManaCost}"
				println "editions     ${it?.editions}"
				println "rarities     ${it?.rarities}"
				println "prices       ${it?.priceRanges}"
				println "publications ${it?.publications}"
				println "formats      ${it?.formats}"
				println "artists      ${it?.artists}"
				println "hiddenHints  ${it?.hiddenHints}"
				println "devotions    ${it?.devotions}"
			}
		}
	}
		
	def index() {
		if (enableIndex || clearConfigure) {
			def indexer = new ESIndexer(host: host)
			indexer.clear()
			indexer.configure()
			if (enableIndex) {
				cards.eachWithIndex { card, counter ->
					println "Indexing ${counter+1}/${cards.size()} : $card.title"
					indexer.index card
				}
			}
		}
	}
}
