package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Maat met noten
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */

public class Maat {

	private List<Noot> noten = new ArrayList<Noot>();

	public List<Noot> getNoten() {
		return noten;
	}

	public void setNoten(List<Noot> noten) {
		this.noten = noten;
	}
}
