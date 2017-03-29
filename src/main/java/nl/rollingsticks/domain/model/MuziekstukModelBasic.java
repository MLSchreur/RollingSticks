package nl.rollingsticks.domain.model;

import nl.rollingsticks.domain.Muziekstuk;

/**
 * Basis Model voor muziekstukken
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-03-29
 */

public class MuziekstukModelBasic {

	public MuziekstukModelBasic(Muziekstuk muziekstuk) {
		this.muziekstuk = muziekstuk;
	}
	
	private Muziekstuk muziekstuk;
	
	public long getId() {
		return this.muziekstuk.getId();
	}

	public String getArtiest() {
		return this.muziekstuk.getArtiest();
	}

	public String getTitel() {
		return this.muziekstuk.getTitel();
	}

	public String getOmschrijving() {
		return this.muziekstuk.getOmschrijving();
	}
}
