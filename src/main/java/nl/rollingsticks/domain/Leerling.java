 package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Leerlingen van de drumschool
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-03-23
 */

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
	
	public void addGroep(Groep groep){
		this.groepen.add(groep);
	
	}
}
