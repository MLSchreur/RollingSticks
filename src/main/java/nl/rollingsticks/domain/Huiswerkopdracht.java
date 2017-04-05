package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.Date;
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

/**
 * Huiswerkopdrachten met muziekstukken
 * @author WCHorrel
 * @version 0.1.0
 * @since 2017-03-23
 */

@Entity
public class Huiswerkopdracht {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Muziekstuk> muziekstukken=new ArrayList<Muziekstuk>();

	private Date lesDatum;
	private String notitie;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Muziekstuk> getMuziekstukken() {
		return muziekstukken;
	}

	public void setMuziekstukken(List<Muziekstuk> muziekstukken) {
		this.muziekstukken = muziekstukken;
	}

	public Date getLesDatum() {
		return lesDatum;
	}

	public void setLesDatum(Date lesDatum) {
		this.lesDatum = lesDatum;
	}

	public String getNotitie() {
		return notitie;
	}

	public void setNotitie(String notitie) {
		this.notitie = notitie;
	}
	
	public void addMuziekstukToMuziekstukken (Muziekstuk muziekstuk) {
		this.muziekstukken.add(muziekstuk);
	}
	
	public void removerMuziekstukFromMuziekstukken (Muziekstuk muziekstuk) {
		this.muziekstukken.remove(muziekstuk);
	}
}
