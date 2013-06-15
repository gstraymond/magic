package fr.gstraymond.card.constants

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
@CompileStatic
class Color {
	final static BLACK = 'B'
	final static BLUE  = 'U'
	final static GREEN = 'G'
	final static RED   = 'R'
	final static WHITE = 'W'
	final static LIFE  = 'P'
	final static X     = 'X'
	
	final static UNCOLORED = 'Uncolored'
	final static MONOCOLORED = 'Monocolored'
	final static MULTICOLORED = 'Multicolored'
	
	final static ALL_COLORS_SYMBOLS = [BLACK, BLUE, GREEN, RED, WHITE, LIFE, X]
	
	final static MAP_COLORS = [
		B: 'Black',
		G: 'Green',
		U: 'Blue',
		W: 'White',
		R: 'Red',
		P: 'Life',
		X: 'X'
	]
}
