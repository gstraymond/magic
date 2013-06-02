package fr.gstraymond.forge.converter

import java.util.zip.ZipFile


class FileConverter {
	ZipFile dumpZip
	File priceFile
	def cards = []

	List parse() {
		/*
		 * forge cards database is a zip cpntaining :
		 * - a directory for each letter (a, b, c...)
		 * - for each directory, a txt file for each card
		 */
		cards = entriesFromZip.collect { entry ->
			getTextFromEntry(entry).split('\\n')
		}
		
		// remove alternate card and add alternate cards splitted
		cards - alternateCardList + getSplittedAlternateCards(alternateCardList)
	}
	
	def getEntriesFromZip() {
		dumpZip.entries().findAll {
			! it.directory
		}
	}
	
	def getTextFromEntry(entry) {
		dumpZip.getInputStream(entry).text
	}
	
	def getAlternateCardList() {
		cards.findAll {
			it.findAll { it.contains("ALTERNATE") }
		}
	}
	
	def getSplittedAlternateCards(alternateCards) {
		def splittedCards = []
		alternateCards.each { card ->
			def standCard = []
			def alternateCard = []
			card.each {
				if(alternateCard || it.contains("ALTERNATE")) {
					alternateCard += it
				} else {
					standCard += it
				}
			}
			splittedCards.add standCard
			splittedCards.add alternateCard
		}
		splittedCards
	}
	
	Map parsePrice() {
		def priceMap = [:]
		priceFile.eachLine {
			// ex: Cryptic Annelid|FUT=22
			def firstSplit = it.split('\\|')
			def secondSplit = firstSplit[1].split('=')
			def title = firstSplit[0]
			def edition = secondSplit[0]
			def price = secondSplit[1]
			priceMap["$edition - $title"] = price
		}
		priceMap
	}
}
