package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
public class Leerling extends Gebruiker {
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Groep> groepen = new ArrayList<Groep>();

	public List<Groep> getGroepen() {
		return groepen;
	}

	public void setGroepen(List<Groep> groepen) {
		this.groepen = groepen;
	}
}
