package fr.gstraymond.forge.converter

import static fr.gstraymond.card.constants.Color.*

class TestCardConverter extends GroovyTestCase {

	CardConverter cardConverter
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		cardConverter = new CardConverter()
	}

	void testCMC() {
		// null
		assertEquals 0, cardConverter.calculateCMC(null)
		
		// emtpy
		assertEquals 0, cardConverter.calculateCMC("")
		
		// Beckon Apparition
		assertEquals 1, cardConverter.calculateCMC("WB")
		
		// Palladia-Mors
		assertEquals 8, cardConverter.calculateCMC("2 W W R R G G")
		
		// Reaper King
		assertEquals 10, cardConverter.calculateCMC("2/W 2/U 2/B 2/R 2/G")
		
		// Emrakul, the Aeons Torn
		assertEquals 15, cardConverter.calculateCMC("15")
		
		// Energy Bolt
		assertEquals 2, cardConverter.calculateCMC("X R W")
		
		// Mental Misstep
		assertEquals 1, cardConverter.calculateCMC("PU")
	}
	
	void testColors() {
		// null
		assertEquals(
			['Uncolored'],
			cardConverter.calculateColors(null)
		)
		
		// empty
		assertEquals(
			['Uncolored'],
			cardConverter.calculateColors("")
		)
		
		// Beckon Apparition
		assertEquals(
			['White', 'Black', 'Multicolored'],
			cardConverter.calculateColors("WB")
		)
		
		// Palladia-Mors
		assertEquals(
			['White', 'Red', 'Green', 'Multicolored'],
			cardConverter.calculateColors("2 W W R R G G")
		)
		
		// Reaper King
		assertEquals(
			['White', 'Blue', 'Black', 'Red', 'Green', 'Multicolored'],
			cardConverter.calculateColors("2/W 2/U 2/B 2/R 2/G")
		)
		
		// Emrakul, the Aeons Torn
		assertEquals(
			['Uncolored'],
			cardConverter.calculateColors("15")
		)
		
		// Energy Bolt
		assertEquals(
			['X', 'Red', 'White', 'Multicolored'],
			cardConverter.calculateColors("X R W")
		)	
		
		// Mental Misstep
		assertEquals(
			['Life', 'Blue', 'Monocolored'],
			cardConverter.calculateColors("PU")
		)
	}
	
	void testPrices() {
		// null
		assertEquals(
			['No price'],
			cardConverter.calculatePriceRanges(null)
		)
		
		// empty
		assertEquals(
			['No price'],
			cardConverter.calculatePriceRanges([])
		)
		
		// 0
		assertEquals(
			['No price'],
			cardConverter.calculatePriceRanges([0])
		)
		
		// 
		assertEquals(
			['Under 1€', 'Between 5€ and 14,99€'],
			cardConverter.calculatePriceRanges([0.6, 7, 0.1, 13])
		)
		
		// 
		assertEquals(
			['Between 1€ and 4,99€'],
			cardConverter.calculatePriceRanges([1, 3, 2])
		)
		
		// 
		assertEquals(
			['Between 15€ and 99,99€', 'More than 100€'],
			cardConverter.calculatePriceRanges([99.99, 100, 0])
		)
	}
}
