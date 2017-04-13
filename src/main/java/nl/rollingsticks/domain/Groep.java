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

/**
 * Lesgroep binnen de drumschool
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-03-23
 */

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Leerling> getLeerlingen() {
		return leerlingen;
	}

	public void setLeerlingen(List<Leerling> leerlingen) {
		this.leerlingen = leerlingen;
	}
	
	public void removeLeerlingFromLeerlingen(Leerling leerling) {
		this.leerlingen.remove(leerling);
	}
	
	public boolean isLinkedLeerling(Leerling linkedLeerling) {
		for (Leerling leerling : leerlingen) {
			if (leerling.getId() == linkedLeerling.getId()) {
				return true;
			}
		}
		return false;
	}

	public List<Huiswerkopdracht> getHuiswerkopdrachten() {
		return huiswerkopdrachten;
	}

	public void setHuiswerkopdrachten(List<Huiswerkopdracht> huiswerkopdrachten) {
		this.huiswerkopdrachten = huiswerkopdrachten;
	}
	
	public boolean isLinkedHuiswerkopdracht(Huiswerkopdracht linkedHuiswerkopdracht) {
		for (Huiswerkopdracht huiswerkopdracht : huiswerkopdrachten) {
			if (huiswerkopdracht.getId() == linkedHuiswerkopdracht.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public void addHuiswerkopdrachtToHuiswerkopdrachten(Huiswerkopdracht huiswerkopdracht) {
		this.huiswerkopdrachten.add(huiswerkopdracht);
	}
	
	public void removeHuiswerkopdrachtFromHuiswerkopdrachten(Huiswerkopdracht huiswerkopdracht) {
		this.huiswerkopdrachten.remove(huiswerkopdracht);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
}
	
