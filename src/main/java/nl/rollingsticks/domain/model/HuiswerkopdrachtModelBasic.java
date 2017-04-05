package nl.rollingsticks.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import nl.rollingsticks.domain.Huiswerkopdracht;
import nl.rollingsticks.domain.Muziekstuk;

/**
 * Basis Model voor Huiswerkopdrachten
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-04
 */

public class HuiswerkopdrachtModelBasic {
	private Huiswerkopdracht huiswerkopdracht;
	
	public HuiswerkopdrachtModelBasic(Huiswerkopdracht huiswerkopdracht) {
		this.huiswerkopdracht = huiswerkopdracht;
	}
	
	public long getId() {
		return this.huiswerkopdracht.getId();
	}

	public Date getLesDatum() {
		return this.huiswerkopdracht.getLesDatum();
	}

	public String getNotitie() {
		return this.huiswerkopdracht.getNotitie() + " Jase was here!";
	}
	
	/**
	 * We geven alleen de id's van de Muziekstukken terug.<br>
	 * De Muziekstukken zelf kunnen dan via de API van Muziekstuk worden opgevraagd.  
	 * @return Id's van muziekstukken behorend bij het huiswerk.
	 */
	public List<Long> getMuziekstukken() {
		List<Long> muziekIds = new ArrayList<>();
		for (Muziekstuk muziekstuk : this.huiswerkopdracht.getMuziekstukken()) {
			muziekIds.add(muziekstuk.getId());
		}
		return muziekIds;
	}

}
