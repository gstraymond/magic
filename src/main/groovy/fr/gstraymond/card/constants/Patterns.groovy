package fr.gstraymond.card.constants

import java.util.regex.Pattern

class Patterns {

	final static Pattern UNCOLORED_CASTING_COST = ~/X?(\d*).*/
	
	/** XX - 15 - (u/p) - RW */
	final static Pattern VALID_CASTING_COST = ~/X*\d*[\(,\),\/,r,g,b,u,w,p,2]*[R,G,B,U,W]*/
}
