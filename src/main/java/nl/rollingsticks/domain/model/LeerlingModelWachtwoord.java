package nl.rollingsticks.domain.model;

import nl.rollingsticks.domain.Leerling;

public class LeerlingModelWachtwoord {
	
	private Leerling leerling;
	
	public LeerlingModelWachtwoord(Leerling leerling){
		this.leerling = leerling;
	}
	
	public String getGebruikersnaam(){
		return this.leerling.getGebruikersnaam();
	}
	
	public String getWachtwoord(){
		return this.leerling.getWachtwoord();
	}
}
