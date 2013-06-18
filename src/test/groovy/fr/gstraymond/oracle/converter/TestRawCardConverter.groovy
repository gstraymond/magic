package fr.gstraymond.oracle.converter;

class TestRawCardConverter extends GroovyTestCase {
	
	RawCardConverter rawCardConverter
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		rawCardConverter = new RawCardConverter()
	}
	
	void testTransformCastingCost() {
		// null
		assertEquals(
			'',
			rawCardConverter.transformCastingCost(null)
		)
		
		// empty
		assertEquals(
			'',
			rawCardConverter.transformCastingCost('')
		)
		
		// Beckon Apparition
		assertEquals(
			'WB',
			rawCardConverter.transformCastingCost('(w/b)')
		)
		
		// Palladia-Mors
		assertEquals(
			'2 W W R R G G',
			rawCardConverter.transformCastingCost('2WWRRGG')
		)
		
		// Reaper King
		assertEquals(
			'2/W 2/U 2/B 2/R 2/G',
			rawCardConverter.transformCastingCost('(2/w)(2/u)(2/b)(2/r)(2/g)')
		)
		
		// Emrakul, the Aeons Torn
		assertEquals(
			'15',
			rawCardConverter.transformCastingCost('15')
		)
		
		// Energy Bolt
		assertEquals(
			'X R W',
			rawCardConverter.transformCastingCost('XRW')
		)
		
		// Mental Misstep
		assertEquals(
			'UP',
			rawCardConverter.transformCastingCost('(u/p)')
		)
	}
}
