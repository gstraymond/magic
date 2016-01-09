package fr.gstraymond.card.download

class Downloader {
	static pictureLocation = '/media/guillaume/Data/Dropbox/Public/mtg'
	static pictureHost = 'http://dl.dropboxusercontent.com/u/22449802/mtg'

	def url, fileFolderName, title
	
	def download() {
		// existence du répertoire
		def folderPath = "$pictureLocation/$fileFolderName"
		def fileFolder = new File(folderPath)
		if (!fileFolder.exists())  {
			fileFolder.mkdirs()
		}
		
		// existence du fichier
		def fileName = "$folderPath/$title"
		def file = new File(fileName)
		try {
            if (!file.exists()) {
                // téléchargement
                println "Downloading $url to $fileName"
                file.withOutputStream { out ->
                    out << new URL(url).openStream()
                }
            }
        } catch (FileNotFoundException e) {
            println "Unable to download $url $e"
        }
	}
}
