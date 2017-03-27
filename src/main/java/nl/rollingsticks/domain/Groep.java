package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
public class Groep {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="groepen")
	@Fetch(FetchMode.SELECT)
	private List<Leerling> leerlingen = new ArrayList<>();
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Huiswerkopdracht> huiswerkopdrachten = new ArrayList<>();

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
	public List<Huiswerkopdracht> getHuiswerkopdrachten() {
		return huiswerkopdrachten;
	}
	/**
	 * @param huiswerkopdrachten the huiswerkopdrachten to set
 	*/ 
	public void setHuiswerkopdrachten(List<Huiswerkopdracht> huiswerkopdrachten) {
		this.huiswerkopdrachten = huiswerkopdrachten;
	}
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
// ==================================================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Groep other = (Groep) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
	
