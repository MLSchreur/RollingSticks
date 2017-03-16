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
	private String lesonderdeel;
	private String padPictogram;
	private String padMp3;
	private String padXml;
	
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

	public String getPadPictogram() {
		return padPictogram;
	}

	public void setPadPictogram(String padPictogram) {
		this.padPictogram = padPictogram;
	}

	public String getPadMp3() {
		return padMp3;
	}

	public void setPadMp3(String padMP3) {
		this.padMp3 = padMP3;
	}

	public String getPadXml() {
		return padXml;
	}

	public void setPadXml(String padXML) {
		this.padXml = padXML;
	}

}
