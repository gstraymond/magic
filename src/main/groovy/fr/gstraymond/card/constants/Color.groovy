package fr.gstraymond.card.constants

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
@CompileStatic
class Color {
	static BLACK = 'B'
	static BLUE  = 'U'
	static GREEN = 'G'
	static RED   = 'R'
	static WHITE = 'W'
	static LIFE  = 'P'
	static X     = 'X'
	
	static UNCOLORED = 'Uncolored'
	static MONOCOLORED = '1 color'
	static MULTICOLORED = '{X} colors'
	static GUILD = 'Guild'
	static GOLD = 'Gold'
	
	static ALL_COLORS_SYMBOLS = [BLACK, BLUE, GREEN, RED, WHITE, LIFE, X]
	
	static MAP_COLORS = [
		B: 'Black',
		G: 'Green',
		U: 'Blue',
		W: 'White',
		R: 'Red',
		P: 'Life',
		X: 'X'
	]
	
	static WU = 'WU' // Azorius Senate
	static WB = 'WB' // Orzhov Syndicate
	static BU = 'BU' // House Dimir
	static UR = 'UR' // Izzet League
	static BR = 'BR' // Cult of Rakdos
	static BG = 'BG' // Golgari Swarm
	static RG = 'RG' // Gruul Clans
	static RW = 'RW' // Boros Legion
	static GW = 'GW' // Selesnya Conclave
	static GU = 'GU' // Simic Combine
	
	static GUILDS = [
		WU,
		WB,
		BU,
		UR,
		BR,
		BG,
		RG,
		RW,
		GW,
		GU
	]
}
