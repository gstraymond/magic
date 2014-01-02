package fr.gstraymond.card.download

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.Publication
import fr.gstraymond.card.tools.TitleNormalizer

class PictureDownloader {
	
	def pictureFolderLocation = 'pics'
	
	MagicCard card
	
	def download() {
		card.publications.each { Publication pub ->
			
			def cardTitle = formatTitle(card, pub)
			
			new Downloader(
				url: pub.image,
				fileFolderName: "$pictureFolderLocation/$pub.editionCode",
				title: cardTitle
			).download()
			
			// mis à jour du chemin
			pub.image = "$Downloader.pictureHost/$pictureFolderLocation/$pub.editionCode/$cardTitle"
		}
	}

	def formatTitle(MagicCard card, Publication pub) {
		def imageName = pub.image.tokenize('/')[-1]
		def id = imageName.split('\\.')[0]
		def ext = imageName.split('\\.')[1]
		def name = TitleNormalizer.normalize(card.title)
		"$id-$name.$ext"
	}
}
