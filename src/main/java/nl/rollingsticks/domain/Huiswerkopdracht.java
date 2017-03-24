package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Carina Horrel
 */

@Entity
public class Huiswerkopdracht {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Muziekstuk> muziekstukken=new ArrayList<Muziekstuk>();

	private Date lesDatum;
	private String notitie;
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
	 * @return the muziekstukken
	 */
	public List<Muziekstuk> getMuziekstukken() {
		return muziekstukken;
	}
	/**
	 * @param muziekstukken the muziekstukken to set
	 */
	public void setMuziekstukken(List<Muziekstuk> muziekstukken) {
		this.muziekstukken = muziekstukken;
	}
	/**
	 * @return the lesDatum
	 */
	public Date getLesDatum() {
		return lesDatum;
	}
	/**
	 * @param lesDatum the lesDatum to set
	 */
	public void setLesDatum(Date lesDatum) {
		this.lesDatum = lesDatum;
	}
	/**
	 * @return the notitie
	 */
	public String getNotitie() {
		return notitie;
	}
	/**
	 * @param notitie the notitie to set
	 */
	public void setNotitie(String notitie) {
		this.notitie = notitie;
	}
}
