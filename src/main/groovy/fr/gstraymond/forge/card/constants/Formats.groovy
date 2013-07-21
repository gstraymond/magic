package fr.gstraymond.forge.card.constants

import fr.gstraymond.card.constants.Format;

class Formats {

	static Format STANDARD = new Format(
	name: 'Standard',
	sets: [
		'ISD',
		'DKA',
		'AVR',
		'M13',
		'RTR',
		'GTC',
		'DGM',
		'M14']
	)

	static Format EXTENDED = new Format(
	name: 'Extended',
	sets: [
		'ZEN',
		'WWK',
		'ROE',
		'M11',
		'SOM',
		'MBS',
		'NPH',
		'M12'
	]
	+ STANDARD.sets,
	bannedCards: [
		'Stoneforge Mystic',
		'Jace, the Mind Sculptor',
		'Ponder',
		'Preordain',
		'Mental Misstep']
	)

	static Format MODERN = new Format(
	name: 'Modern',
	sets: [
		'8ED',
		'MRD',
		'DST',
		'5DN',
		'CHK',
		'BOK',
		'SOK',
		'9ED',
		'RAV',
		'GPT',
		'DIS',
		'CSP',
		'TSP',
		'TSB',
		'PLC',
		'FUT',
		'10E',
		'LRW',
		'EVE',
		'SHM',
		'MOR',
		'ALA',
		'CFX',
		'ARB',
		'M10'
	]
	+ EXTENDED.sets,
	bannedCards: [
		'Ancestral Vision',
		'Ancient Den',
		'Bitterblossom',
		'Blazing Shoal',
		'Bloodbraid Elf',
		'Chrome Mox',
		'Cloudpost',
		'Dark Depths',
		'Dread Return',
		'Glimpse of Nature',
		'Golgari Grave-Troll',
		'Great Furnace',
		'Green Sun\'s Zenith']
	)

	static Format LEGACY = new Format(
	name: 'Legacy',
	bannedCards: [
		'Amulet of Quoz',
		'Ancestral Recall',
		'Balance',
		'Bazaar of Baghdad',
		'Black Lotus',
		'Black Vise',
		'Bronze Tablet',
		'Channel',
		'Chaos Orb',
		'Contract from Below',
		'Darkpact',
		'Demonic Attorney',
		'Demonic Consultation',
		'Demonic Tutor',
		'Earthcraft',
		'Falling Star',
		'Fastbond',
		'Flash',
		'Frantic Search',
		'Goblin Recruiter',
		'Gush',
		'Hermit Druid',
		'Imperial Seal',
		'Jeweled Bird',
		'Land Tax',
		'Library of Alexandria',
		'Mana Crypt',
		'Mana Drain',
		'Mana Vault',
		'Memory Jar',
		'Mind Twist',
		'Mind\'s Desire',
		'Mishra\'s Workshop',
		'Mox Emerald',
		'Mox Jet',
		'Mox Pearl',
		'Mox Ruby',
		'Mox Sapphire',
		'Mystical Tutor',
		'Necropotence',
		'Oath of Druids',
		'Rebirth',
		'Shahrazad',
		'Skullclamp',
		'Sol Ring',
		'Strip Mine',
		'Survival of the Fittest',
		'Tempest Efreet',
		'Time Vault',
		'Time Walk',
		'Timetwister',
		'Timmerian Fiends',
		'Tinker',
		'Tolarian Academy',
		'Vampiric Tutor',
		'Wheel of Fortune',
		'Windfall',
		'Worldgorger Dragon',
		'Yawgmoth\'s Bargain',
		'Yawgmoth\'s Will',
		'Mental Misstep']
	)

	static Format VINTAGE = new Format(
	name: 'Vintage',
	bannedCards: [
		'Amulet of Quoz',
		'Bronze Tablet',
		'Chaos Orb',
		'Contract from Below',
		'Darkpact',
		'Demonic Attorney',
		'Falling Star',
		'Jeweled Bird',
		'Rebirth',
		'Shahrazad',
		'Tempest Efreet',
		'Timmerian Fiends'
	])

	static ALL = [
		STANDARD,
		EXTENDED,
		MODERN,
		LEGACY,
		VINTAGE
	]
}
