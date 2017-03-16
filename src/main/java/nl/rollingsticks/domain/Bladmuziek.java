package nl.rollingsticks.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
	
	// oplossing 1: bestanden opslaan op file-server en padverwijzing in velden opslaan
	private String padPictogram;
	private String padMp3;
	private String padXml;
	
	// oplossing 2: bestanden opslaan in database
	@Lob
	@Column(name = "blob_img", length = 17777215)
	private byte[] pictogram;

	@Lob
	@Column(name = "blob_mp3", length = 17777215)
	private byte[] mp3;

	@Lob
	@Column(name = "blob_xml", length = 17777215)
	private String xml;
	
	public byte[] getPictogram() {
		return pictogram;
	}

	public void setPictogram(byte[] pictogram) {
		this.pictogram = pictogram;
	}

	public byte[] getMp3() {
		return mp3;
	}

	public void setMp3(byte[] mp3) {
		this.mp3 = mp3;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

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
