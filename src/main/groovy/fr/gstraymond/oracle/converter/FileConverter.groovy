package fr.gstraymond.oracle.converter

class FileConverter {
	File dumpFile
	
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
