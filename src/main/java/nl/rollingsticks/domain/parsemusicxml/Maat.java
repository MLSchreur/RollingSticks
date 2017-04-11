package nl.rollingsticks.domain.parsemusicxml;

import java.util.ArrayList;
import java.util.List;

/**
 * Maat met noten
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */

public class Maat {

	private int nummer;
	private List<Noot> noten = new ArrayList<Noot>();

	// Geen setter voor nummer nodig. Dit geeft puur het nummer van de maat aan
	// voor de eventuele index van maten aan de voorkant.
	public Maat (int nummerMaat) {
		this.nummer = nummerMaat;
		System.out.println("*****Nieuwe maat...");
	}

	public int getNummer() {
		return nummer;
	}

	public List<Noot> getNoten() {
		return noten;
	}

	public void setNoten(List<Noot> noten) {
		this.noten = noten;
	}
	
	public void addNotenToMaat (Noot noot) {
		this.noten.add(noot);
	}

}
