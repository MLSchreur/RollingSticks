package nl.rollingsticks.domain;

import javax.persistence.Entity;
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

/**
 * 
 * @author WCHorrel
 *
 */
@Entity
public class Leerling {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String leerling;
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
	 * @return the gebruikersnaam
	 */
	public String getLeerling() {
		return leerling;
	}
	/**
	 * @param leerling the leerling to set
	 */
	public void setLeerling(String leerling) {
		this.leerling = leerling;
	}
	
	
}
