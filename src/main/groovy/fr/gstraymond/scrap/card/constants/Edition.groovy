package fr.gstraymond.scrap.card.constants

class Edition {
	
	private static final map = [:]
	static final editionsPropertiesPath = 'src/main/resources/scrap/editions.properties'
	
	static final Map getEditionsMap() {
		if (! map) {
			Properties props = new Properties()
			File propsFile = new File(editionsPropertiesPath)
			props.load(propsFile.newDataInputStream())
			
			props.each {
				map.put(it.key, it.value)
			}
		}
		
		map
	}
}
