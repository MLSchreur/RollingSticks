package nl.rollingsticks.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Bestemd voor documentatie tijdens ontwikkeling
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-03-23
 */

@Entity
public class Tekst {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String tekst;
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public long getId() {
		return id;
	}
	
	
}
