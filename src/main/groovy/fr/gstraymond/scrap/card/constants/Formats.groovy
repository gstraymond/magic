package fr.gstraymond.scrap.card.constants

import fr.gstraymond.card.constants.Format;

class Formats {

    // http://mtgsalvation.gamepedia.com/Standard
	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrstandard
	static Format STANDARD = new Format(
	name: 'Standard',
	sets: [
	    'bfz',

        'm15',

		'dtk',
		'frf',
		'ktk',
		]
	)

    // http://mtgsalvation.gamepedia.com/Extended
	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrextended
	static Format EXTENDED = new Format(
	name: 'Extended',
	sets: [
		// Return to Ravnica block (Return to Ravnica, Gatecrash, Dragon's Maze)
		'rtr',
		'gtc',
		'dgm',
		'm14',

		// Theros block (Theros, Born of the Gods [effective February 7, 2014], Journey Into Nyx [effective May 2, 2014])
		'jou',
		'bng',
		'ths',
    ]
    + STANDARD.sets,
	bannedCards: [
		'Mental Misstep',
		'Ponder',
    ]
	)

    // http://mtgsalvation.gamepedia.com/Modern
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
	+ EXTENDED.sets,
	bannedCards: [
		'Ancestral Vision',
		'Ancient Den',
		'Birthing Pod',
		'Blazing Shoal',
		'Bloodbraid Elf',
		'Chrome Mox',
		'Cloudpost',
		'Dark Depths',
		'Deathrite Shaman',
		'Dig Through Time',
		'Dread Return',
		'Glimpse of Nature',
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
		'Treasure Cruise',
		'Tree of Tales',
		'Umezawa\'s Jitte',
		'Vault of Whispers',
		]
	)

    // http://mtgsalvation.gamepedia.com/Vintage
	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrvintage
	static Format VINTAGE = new Format(
	name: 'Vintage',
	bannedCards: [
		'Advantageous Proclamation',
		'Amulet of Quoz',
		'Backup Plan',
		'Brago\'s Favor',
		'Bronze Tablet',
		'Chaos Orb',
		'Contract from Below',
		'Darkpact',
		'Demonic Attorney',
		'Double Stroke',
		'Falling Star',
		'Immediate Action',
		'Iterative Analysis',
		'Jeweled Bird',
		'Muzzio\'s Preparations',
		'Power Play',
		'Rebirth',
		'Secret Summoning',
		'Secrets of Paradise',
		'Sentinel Dispatch',
		'Shahrazad',
		'Tempest Efreet',
		'Timmerian Fiends',
		'Unexpected Potential',
		'Worldknit',
	])

    // http://mtgsalvation.gamepedia.com/Legacy
    // http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrlegacy
    static Format LEGACY = new Format(
            name: 'Legacy',
            bannedCards: [
                    'Ancestral Recall',
                    'Balance',
                    'Bazaar of Baghdad',
                    'Black Lotus',
                    'Channel',
                    'Demonic Consultation',
                    'Dig Through Time',
                    'Demonic Tutor',
                    'Earthcraft',
                    'Fastbond',
                    'Flash',
                    'Frantic Search',
                    'Goblin Recruiter',
                    'Gush',
                    'Hermit Druid',
                    'Imperial Seal',
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
                    'Skullclamp',
                    'Sol Ring',
                    'Strip Mine',
                    'Survival of the Fittest',
                    'Time Vault',
                    'Time Walk',
                    'Timetwister',
                    'Tinker',
                    'Tolarian Academy',
                    'Treasure Cruise',
                    'Vampiric Tutor',
                    'Wheel of Fortune',
                    'Windfall',
                    'Yawgmoth\'s Bargain',
                    'Yawgmoth\'s Will',
            ] + VINTAGE.bannedCards
    )

	// http://www.wizards.com/Magic/TCG/Resources.aspx?x=judge/resources/sfrvintage
	static Format RESTRICTED = new Format(
	name: 'Restricted',
	include: false,
	bannedCards: [
		'Ancestral Recall',
		'Balance',
		'Black Lotus',
		'Brainstorm',
        'Chalice of the Void',
		'Channel',
		'Demonic Consultation',
		'Demonic Tutor',
        'Dig Through Time',
		'Fastbond',
		'Flash',
		'Imperial Seal',
		'Library of Alexandria',
		'Lion\'s Eye Diamond',
		'Lotus Petal',
		'Mana Crypt',
		'Mana Vault',
		'Memory Jar',
		'Merchant Scroll',
		'Mind\'s Desire',
		'Mox Emerald',
		'Mox Jet',
		'Mox Pearl',
		'Mox Ruby',
		'Mox Sapphire',
		'Mystical Tutor',
		'Necropotence',
		'Ponder',
		'Sol Ring',
		'Strip Mine',
		'Time Vault',
		'Time Walk',
		'Timetwister',
		'Tinker',
		'Tolarian Academy',
		'Treasure Cruise',
		'Trinisphere',
		'Vampiric Tutor',
		'Wheel of Fortune',
		'Windfall',
		'Yawgmoth\'s Bargain',
		'Yawgmoth\'s Will'
	])
	static ALL = [
		STANDARD,
		EXTENDED,
		MODERN,
		LEGACY,
		VINTAGE,
		RESTRICTED
	]
}
