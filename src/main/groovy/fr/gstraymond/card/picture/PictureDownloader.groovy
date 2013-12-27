package fr.gstraymond.card.picture

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.Publication
import fr.gstraymond.card.tools.TitleNormalizer;

class PictureDownloader {
	
	def pictureLocation = '/media/guillaume/Data/Dropbox/Public/mtg/pics'

	def download(MagicCard card) {
		card.publications.each { Publication pub ->
			def fileFolderName = "$pictureLocation/${pub.editionCode}"
			def fileFolder = new File(fileFolderName)
			if (!fileFolder.exists())  {
				fileFolder.mkdirs()
			}
			
			def fileName = "${fileFolderName}/${formatTitle(card, pub)}"
			def file = new File(fileName)
			if (!file.exists())  {
				println "Downloading $pub.image to $fileName"
				file.withOutputStream { out ->
					out << new URL(pub.image).openStream()
				}
			}
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
