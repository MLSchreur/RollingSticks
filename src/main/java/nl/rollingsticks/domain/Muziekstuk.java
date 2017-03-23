package nl.rollingsticks.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 
 * @author WCHorrel
 *
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
	
	byte[] pictogram;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the artiest
	 */
	public String getArtiest() {
		return artiest;
	}

	/**
	 * @param artiest the artiest to set
	 */
	public void setArtiest(String artiest) {
		this.artiest = artiest;
	}

	/**
	 * @return the titel
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * @param titel the titel to set
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}

	/**
	 * @return the omschrijving
	 */
	public String getOmschrijving() {
		return omschrijving;
	}

	/**
	 * @param omschrijving the omschrijving to set
	 */
	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	/**
	 * @return the mp3
	 */
	public String getMp3() {
		return mp3;
	}

	/**
	 * @param mp3 the mp3 to set
	 */
	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * @return the pictogram
	 */
	public byte[] getPictogram() {
		return pictogram;
	}

	/**
	 * @param pictogram the pictogram to set
	 */
	public void setPictogram(byte[] pictogram) {
		this.pictogram = pictogram;
	}
	

}
