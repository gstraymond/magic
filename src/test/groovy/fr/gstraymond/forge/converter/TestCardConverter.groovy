package fr.gstraymond.forge.converter

import static fr.gstraymond.card.constants.Color.*
import fr.gstraymond.card.MagicCard;
import groovy.transform.CompileStatic;


@CompileStatic
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
		assertEquals 0, cardConverter.calculateCMC('')
		
		// Beckon Apparition
		assertEquals 1, cardConverter.calculateCMC('WB')
		
		// Palladia-Mors
		assertEquals 8, cardConverter.calculateCMC('2 W W R R G G')
		
		// Reaper King
		assertEquals 10, cardConverter.calculateCMC('2/W 2/U 2/B 2/R 2/G')
		
		// Emrakul, the Aeons Torn
		assertEquals 15, cardConverter.calculateCMC('15')
		
		// Energy Bolt
		assertEquals 2, cardConverter.calculateCMC('X R W')
		
		// Mental Misstep
		assertEquals 1, cardConverter.calculateCMC('PU')
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
			cardConverter.calculateColors('')
		)
		
		// Beckon Apparition
		assertEquals(
			['White', 'Black', '2 colors', 'Guild'],
			cardConverter.calculateColors('WB')
		)
		
		// Palladia-Mors
		assertEquals(
			['White', 'Red', 'Green', '3 colors', 'Gold'],
			cardConverter.calculateColors('2 W W R R G G')
		)
		
		// Reaper King
		assertEquals(
			['White', 'Blue', 'Black', 'Red', 'Green', '5 colors', 'Gold'],
			cardConverter.calculateColors('2/W 2/U 2/B 2/R 2/G')
		)
		
		// Emrakul, the Aeons Torn
		assertEquals(
			['Uncolored'],
			cardConverter.calculateColors('15')
		)
		
		// Energy Bolt
		assertEquals(
			['X', 'Red', 'White', '2 colors', 'Gold'],
			cardConverter.calculateColors('X R W')
		)	
		
		// Mental Misstep
		assertEquals(
			['Life', 'Blue', '1 color'],
			cardConverter.calculateColors('PU')
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
	
	void testFormatPublication() {
		assertEquals(
			'<li><a href=\'http://google.com\'>ed - rar</a></li>',
			cardConverter.formatPublication('ed', 'rar', null, 'http://google.com')
		)
		
		assertEquals(
			'<li><a href=\'pic\'>ed - rar: pr€</a></li>',
			cardConverter.formatPublication('ed', 'rar', 'pr', 'pic')
		)
	}
	
	void testFormatPublications() {
		assertEquals(
			'<ul class=\'unstyled\'><li>1</li><li>2</li></ul>',
			cardConverter.formatPublications(['<li>2</li>', '<li>1</li>'])
		)
	}
	
	void testGetFormat() {
		assertEquals(
			[],
			cardConverter.getFormats(new MagicCard(title: 'Chaos Orb', editions: ['LEA', 'LEB', '2ED']))
		)
		assertEquals(
			['Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Black Lotus', editions: ['LEA', 'LEB', '2ED']))
		)
		
		assertEquals(
			['Legacy', 'Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Animate Dead', editions: ['LEA', 'LEB', '2ED', '3ED', '4ED', '5ED']))
		)
		
		assertEquals(
			['Legacy', 'Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Chrome Mox', editions: ['MRD']))
		)
		
		assertEquals(
			['Modern', 'Legacy', 'Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Blazing Archon', editions: ['RAV']))
		)
		
		assertEquals(
			['Modern', 'Legacy', 'Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Ponder', editions: ['M10', 'M12', 'LRW']))
		)
		
		assertEquals(
			['Extended', 'Modern', 'Legacy', 'Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Blightsteel Colossus', editions: ['MBS']))
		)
		
		assertEquals(
			['Standard', 'Extended', 'Modern', 'Legacy', 'Vintage'],
			cardConverter.getFormats(new MagicCard(title: 'Jarad, Golgari Lich Lord', editions: ['RTR']))
		)
	}
}
