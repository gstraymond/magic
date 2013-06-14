package fr.gstraymond.card.constants

class PriceRange {
	String name
	int inf
	int sup
	
	final static range1 = new PriceRange(
		name: 'Under 1€',
		inf: 0,
		sup: 1
	)
	
	final static range2 = new PriceRange(
		name: 'Between 1€ and 4,99€',
		inf: 1,
		sup: 5
	)
	
	final static range3 = new PriceRange(
		name: 'Between 5€ and 14,99€',
		inf: 5,
		sup: 15
	)
	
	final static range4 = new PriceRange(
		name: 'Between 15€ and 99,99€',
		inf: 15,
		sup: 100
	)
	
	final static range5 = new PriceRange(
		name: 'More than 100€',
		inf: 100,
		sup: 99999
	)
	
	final static ALL = [
		range1,
		range2,
		range3,
		range4,
		range5
	]
}
