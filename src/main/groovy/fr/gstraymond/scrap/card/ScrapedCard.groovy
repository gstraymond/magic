package fr.gstraymond.scrap.card

class ScrapedCard {

	String collectorNumber
	String title
	String frenchTitle
	String rarity
	String artist
	String edition

	@Override
	public String toString() {
		"$collectorNumber - $edition - $title - $frenchTitle"
	}	

	def getUniqueId() {
		"$collectorNumber - $edition"
	}	
}
