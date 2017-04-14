package nl.rollingsticks.domain.model;

import java.util.ArrayList;
import java.util.List;

import nl.rollingsticks.domain.Groep;

public class GroepModelBasic {
		
	private Groep groep;
	
	public GroepModelBasic(Groep groep){
		this.groep = groep;
	}
	
	public String getNaam(){
		return this.groep.getNaam();
	}
	
	/**
	 * We geven alleen de id's van de Leerlingen terug.<br>
	 * De Leerlingen zelf kunnen dan via de API van Leerlingen worden opgevraagd.  
	 * @return Id's van groepen behorend bij de groep.
	 */
	public List<Long> getLeerlingenById(){
		List<Long> leerlingIds = new ArrayList<>();	
		for (int i = 0; i < this.groep.getLeerlingen().size(); i++) {
			leerlingIds.add(this.groep.getLeerlingen().get(i).getId());
		}
		return leerlingIds;
	}
	
	/**
	 * We geven alleen de id's van de Leerlingen terug.<br>
	 * De Leerlingen zelf kunnen dan via de API van Leerlingen worden opgevraagd.  
	 * @return Id's van groepen behorend bij de groep.
	 */
	public List<Long> getHuiswerkopdrachtenById(){
		List<Long> huiswerkopdrachtenIds = new ArrayList<>();	
		for (int i = 0; i < this.groep.getHuiswerkopdrachten().size(); i++) {
			huiswerkopdrachtenIds.add(this.groep.getHuiswerkopdrachten().get(i).getId());
		}
		return huiswerkopdrachtenIds;
	}
	
	
}
