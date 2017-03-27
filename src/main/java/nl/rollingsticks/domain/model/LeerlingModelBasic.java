package nl.rollingsticks.domain.model;

import java.util.ArrayList;
import java.util.List;

import nl.rollingsticks.domain.Leerling;

public class LeerlingModelBasic {
	private Leerling leerling;
	
	public LeerlingModelBasic(Leerling leerling) {
		this.leerling = leerling;
	}
	
	public long getId() {
		return this.leerling.getId();
	}
	public String getAchternaam() {
		return this.leerling.getAchternaam();
	}
	public String getVoornaam() {
		return this.leerling.getVoornaam();
	}
	public String getTussenvoegsel() {
		return this.leerling.getTussenvoegsel();
	}
	public String getGebruikersnaam() {
		return this.leerling.getGebruikersnaam();
	}
	/**
	 * To prevent going in circles, only Groep id's are returned
	 * @return the Groep id's
	 */
	public List<Long> getGroepenById() {
		List<Long> groepIds = new ArrayList<>();
		for (int i=0 ; i<this.leerling.getGroepen().size() ; i++) {
			groepIds.add(this.leerling.getGroepen().get(i).getId());
		}
		return groepIds;
	}
}
