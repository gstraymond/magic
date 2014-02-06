package fr.gstraymond.scrap.card.constants

import fr.gstraymond.card.constants.Format;

class Formats {

	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrstandard
	static Format STANDARD = new Format(
	name: 'Standard',
	sets: [
		// Return to Ravnica block (Return to Ravnica, Gatecrash, Dragon's Maze)
		'rtr',
		'gtc',
		'dgm',
		'm14',
		
		// Theros block (Theros, Born of the Gods [effective February 7, 2014], Journey Into Nyx [effective May 2, 2014])
		'ths',
		'bng',
		'jin',
		]
	)

	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrextended
	static Format EXTENDED = new Format(
	name: 'Extended',
	sets: [
		// Scars of Mirrodin block (Scars of Mirrodin, Mirrodin Besieged, New Phyrexia)
		'som',
		'mbs',
		'nph',
		'm12',
		
		// Innistrad-Avacyn Restored block (Innistrad, Dark Ascension, Avacyn Restored)
		'isd',
		'dka',
		'avr',
		'm13',
		]
	+ STANDARD.sets,
	bannedCards: [
		'Ponder',
		'Mental Misstep',
		]
	)

	// https://www.wizards.com/magic/tcg/resources.aspx?x=judge/resources/sfrmodern
	static Format MODERN = new Format(
	name: 'Modern',
	sets: [
		// Mirrodin block (Mirrodin, Darksteel, Fifth Dawn)
		'8e',
		'mi',
		'ds',
		'5dn',
		
		// Kamigawa block (Champions of Kamigawa, Betrayers of Kamigawa, Saviors of Kamigawa)
		'chk',
		'bok',
		'sok',
		'9e',
		
		// Ravnica block (Ravnica: City of Guilds, Guildpact, Dissension)
		'rav',
		'gp',
		'di',
		'cs',
		
		// Time Spiral block (Time Spiral, Planar Chaos, Future Sight)
		'ts',
		'tsts',
		'pc',
		'fut',
		
		// Lorwyn-Shadowmoor block (Lorwyn, Morningtide, Shadowmoor, Eventide)
		'lw',
		'mt',
		'shm',
		'eve',
		'10e',
		
		// Shards of Alara block (Shards of Alara, Conflux, Alara Reborn)
		'ala',
		'cfx', // Conflux
		'arb',
		'm10',
		
		// Zendikar-Rise of the Eldrazi block (Zendikar, Worldwake, Rise of the Eldrazi)
		'zen',
		'wwk',
		'roe',
		'm11',
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
		'Green Sun\'s Zenith',
		'Hypergenesis',
		'Jace, the Mind Sculptor',
		'Mental Misstep',
		'Ponder',
		'Preordain',
		'Punishing Fire',
		'Rite of Flame',
		'Seat of the Synod',
		'Second Sunrise',
		'Seething Song',
		'Sensei\'s Divining Top',
		'Stoneforge Mystic',
		'Skullclamp',
		'Sword of the Meek',
		'Tree of Tales',
		'Umezawa\'s Jitte',
		'Vault of Whispers',
		]
	)

	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrlegacy
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
		'Library of Alexandria',
		'Mana Crypt',
		'Mana Drain',
		'Mana Vault',
		'Memory Jar',
		'Mental Misstep',
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
		]
	)

	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrvintage
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
