package fr.gstraymond.card.download

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.Publication

class EditionDownloader {
	
	def baseUrl = 'http://gatherer.wizards.com/Handlers/Image.ashx' 
	def pictureFolderLocation = 'sets'
	
	MagicCard card

	def download() {
		card.publications.findAll {
			it.stdEditionCode
		}.each { Publication pub ->
			def picSize = format[pub.stdEditionCode] ?: 'large'
			def title = "${pub.rarityCode}.gif"
			
			new Downloader(
				url: "$baseUrl?type=symbol&set=$pub.stdEditionCode&size=$picSize&rarity=$pub.rarityCode",
				fileFolderName: "$pictureFolderLocation/$pub.stdEditionCode",
				title: title
			).download()
			
			// mis à jour du chemin
			pub.editionImage = "$Downloader.pictureHost/$pictureFolderLocation/$pub.stdEditionCode/$title"
		}
		
	}
	
	def format = [
		ARC : 'small',
		M11 : 'small',
		ROE : 'small',
		US : 'small',
		ZEN : 'small',
		ME3 : 'small',
		DDF : 'small',
		DDD : 'small',
		DDE : 'small',
		H09 : 'small',
		ME4 : 'small'
	]
}
