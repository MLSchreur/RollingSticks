package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Entity
public class Leerling extends Gebruiker {
	@ElementCollection(fetch=FetchType.EAGER)
	private List<Groep> groepen = new ArrayList<Groep>();

	public List<Groep> getGroepen() {
		return groepen;
	}

	public void setGroepen(List<Groep> groepen) {
		this.groepen = groepen;
	}
}
