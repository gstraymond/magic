package fr.gstraymond.forge.converter

import fr.gstraymond.forge.card.RawCard;

class RawCardConverter {
	List cardAsString
	Map cardAsMap = [:]
	RawCard rawCard = new RawCard()
	
	RawCard parse() {
		transformToMap()
		setTitle()
		setCastingCost()
		setType()
		setSetInfos()
		setPowerToughness()
		setDescription()
		rawCard
	}
	
	void transformToMap() {
		cardAsString.each {
			def cardLine = cleanLine it
			def delimPos = cardLine.indexOf(':')
			if (delimPos > -1) {
				def key = cardLine.substring(0, delimPos).toLowerCase()
				def value = cardLine.substring(delimPos+1)
				
				if (cardAsMap.containsKey(key)) {
					cardAsMap[key] = [cardAsMap[key], value].flatten()
				} else {
					cardAsMap[key] = value
				}
			}
		}
	}
	
	def cleanLine(line) {
		(line - 'SVar:').trim()
	}
	
	void setTitle() {
		rawCard.title = cardAsMap.name
	}

	void setCastingCost() {
		if (! 'no cost'.equals(cardAsMap.manacost)) {
			rawCard.castingCost = cardAsMap.manacost
		}
	}
	
	void setType() {
		rawCard.type = cardAsMap.types
	}

	void setSetInfos() {
		if (cardAsMap.setinfo) {
			rawCard.setInfos.addAll cardAsMap.setinfo
		}
	}

	void setPowerToughness() {
		rawCard.powerToughness = cardAsMap.pt
	}

	void setDescription() {
		rawCard.description = cardAsMap.oracle?.replaceAll(/\\n/, '\n')
	}
}
