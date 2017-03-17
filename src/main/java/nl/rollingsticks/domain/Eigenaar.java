package nl.rollingsticks.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author WCHorrel
 *
 */

@Entity
public class Eigenaar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String eigenaar;
	
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
	 * @return the eigenaar
	 */
	public String getEigenaar() {
		return eigenaar;
	}
	/**
	 * @param eigenaar the eigenaar to set
	 */
	public void setEigenaar(String eigenaar) {
		this.eigenaar = eigenaar;
	}
	
}
