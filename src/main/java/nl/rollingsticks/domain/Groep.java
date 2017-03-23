package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
/**
 * 
 */
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Groep {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ElementCollection(fetch=FetchType.EAGER)
	private List<Leerling> leerlingen = new ArrayList<Leerling>();
//	@ElementCollection(fetch=FetchType.EAGER)
//	private List<Huiswerkopdracht> huiswerkopdrachten = new ArrayList<Huiswerkopdracht>();
	private String naam;
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
	 * @return the leerlingen
	 */
	public List<Leerling> getLeerlingen() {
		return leerlingen;
	}
	/**
	 * @param leerlingen the leerlingen to set
	 */
	public void setLeerlingen(List<Leerling> leerlingen) {
		this.leerlingen = leerlingen;
	}
	/**
	 * @return the huiswerkopdrachten
	 */
/*	public List<Huiswerkopdracht> getHuiswerkopdrachten() {
		return huiswerkopdrachten;
	}
	/**
	 * @param huiswerkopdrachten the huiswerkopdrachten to set
	 
	public void setHuiswerkopdrachten(List<Huiswerkopdracht> huiswerkopdrachten) {
		this.huiswerkopdrachten = huiswerkopdrachten;
	}
*/
/**
	 * @return the naam
	 */
	public String getNaam() {
		return naam;
	}
	/**
	 * @param naam the naam to set
	 */
	public void setNaam(String naam) {
		this.naam = naam;
	}
	
}
	