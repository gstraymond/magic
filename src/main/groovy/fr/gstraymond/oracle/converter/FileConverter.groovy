package fr.gstraymond.oracle.converter

import java.io.File;

class FileConverter {
	File dumpFile
	File priceFile
	
	List parse() {
		List cards = []
		List cardAsString = []
		dumpFile.eachLine {
			if (it.isEmpty()) {
				cards.add cardAsString
				cardAsString = []
			} else {
				cardAsString += it
			}
		}
		
		if (cardAsString) {
			cards.add cardAsString
		}
		
		cards
	}
}
