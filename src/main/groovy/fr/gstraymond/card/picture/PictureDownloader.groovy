package fr.gstraymond.card.picture

import fr.gstraymond.card.MagicCard
import fr.gstraymond.card.Publication
import fr.gstraymond.card.tools.TitleNormalizer

class PictureDownloader {
	
	def pictureFolderLocation = 'mtg/pics'
	def pictureLocation = "/media/guillaume/Data/Dropbox/Public/$pictureFolderLocation"
	def pictureHost = "http://dl.dropboxusercontent.com/u/22449802/$pictureFolderLocation"

	def download(MagicCard card) {
		card.publications.each { Publication pub ->
			def fileFolderName = "$pictureLocation/$pub.editionCode"
			def fileFolder = new File(fileFolderName)
			if (!fileFolder.exists())  {
				fileFolder.mkdirs()
			}
			
			def cardTitle = formatTitle(card, pub)
			def fileName = "$fileFolderName/$cardTitle"
			def file = new File(fileName)
			if (!file.exists())  {
				println "Downloading $pub.image to $fileName"
				file.withOutputStream { out ->
					out << new URL(pub.image).openStream()
				}
			}
			
			// mis à jour du chemin
			pub.image = "$pictureHost/$pub.editionCode/$cardTitle"
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
