package nl.rollingsticks.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Lesgroep binnen de drumschool
 * @author WCHorrel
 * @version 0.1.0
 * @since 2017-03-23
 */

@Entity
public class Muziekstuk {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String artiest;
	private String titel;
	private String omschrijving;
	private String mp3;
	
	@Lob
	@Column(name = "blob_xml", length = 17777215)
	private String xml;
	
	@Lob
	@Column(name = "blob_pictogram", length = 17777215)
	byte[] pictogram;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArtiest() {
		return artiest;
	}

	public void setArtiest(String artiest) {
		this.artiest = artiest;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
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

	public byte[] getPictogram() {
		return pictogram;
	}

	public void setPictogram(byte[] pictogram) {
		this.pictogram = pictogram;
	}
}
