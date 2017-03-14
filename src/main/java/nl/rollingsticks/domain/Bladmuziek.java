package nl.rollingsticks.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Cor Faber
 *
 */

@Entity
public class Bladmuziek {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String titel;
	private String artiest;
	private String omschrijving;
//	private String pictogram; // later via jpeg
	private String lesonderdeel;
	private String mp3;
	private String xml;
	
	public long getId() {
		return id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getArtiest() {
		return artiest;
	}

	public void setArtiest(String artiest) {
		this.artiest = artiest;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getLesonderdeel() {
		return lesonderdeel;
	}

	public void setLesonderdeel(String lesonderdeel) {
		this.lesonderdeel = lesonderdeel;
	}

	public String getMp3() {
		return mp3;
	}

	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}


}
