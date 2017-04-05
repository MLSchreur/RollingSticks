package nl.rollingsticks.domain.model;

import java.util.ArrayList;
import java.util.List;

import nl.rollingsticks.domain.Leerling;

/**
 * Basis Model voor leerlingen
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-05
 */
public class LeerlingModelBasic {
	
	private Leerling leerling;
	
	public LeerlingModelBasic(Leerling leerling){
		this.leerling = leerling;
	}
	
	public String getAchternaam(){
		return this.leerling.getAchternaam();
	}
	
	public String getVoornaam(){
		return this.leerling.getVoornaam();
	}
	
	public String getTussenvoegsel(){
		return this.leerling.getTussenvoegsel();
	}
	
	public String getGebruikersnaam(){
		return this.leerling.getGebruikersnaam();
	}
	public List<Long> getGroepenById(){
		List<Long> groepIds = new ArrayList<>();
		for (int i = 0; i < this.leerling.getGroepen().size(); i++) {
			groepIds.add(this.leerling.getGroepen().get(i).getId());
		}
		return groepIds;
	}
}
