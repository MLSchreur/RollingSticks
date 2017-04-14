package nl.rollingsticks.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Docent;
import nl.rollingsticks.domain.Gebruiker;
import nl.rollingsticks.domain.Leerling;

@Service
@Transactional
public class GebruikerService {

	@Autowired
	private LeerlingService leerlingService;

	@Autowired
	private DocentService docentService;

	/**
	 * Aanmaken van nieuwe gebruiker
	 * @param	gebruiker Cre&euml;ren van een nieuwe gebruiker.
	 * @return 	0 = gebruiker kan worden opgeslagen
	 * 			1 = heeft al een id<br>
	 * 			2 = gegevens niet goed ingevuld<br>
	 * 			3 = gebruikersnaam bestaat al
	 */
	public int postGebruiker(Gebruiker gebruiker){
		if(gebruiker.getId() != 0){
			System.out.println("Heeft al een id");
			return 1;
		} else if (gebruiker.getAchternaam() == null || gebruiker.getVoornaam() == null || gebruiker.getGebruikersnaam() == null ){

			System.out.println("Niet alles ingevuld");
			return 2;
		}
		List <Gebruiker> gebruikers = new ArrayList<>();
		gebruikers.addAll((ArrayList<Leerling>)leerlingService.findAll());
		gebruikers.addAll((ArrayList<Docent>) docentService.findAll());
		if(gebruikers.size() != 0){
			for(Gebruiker gbrkr: gebruikers){
				if(gbrkr.getGebruikersnaam().equalsIgnoreCase(gebruiker.getGebruikersnaam())){
					System.out.println(gebruiker.getGebruikersnaam() + " bestaat al!");
					return 3;
				}
			}
		}
		System.out.println("gebruiker kan worden opgeslagen");
		return 0;
	}

	/**
	 * Controle of gebruikersnaam bestaat
	 * @param validateGebruiker
	 * @return 	0   = gebruikersnaam bestaat niet
	 * 			1 	= gebruiker is docent
	 * 			2	= gebruiker is leerling
	 */
	public Gebruiker checkGebruikersnaam(Gebruiker validateGebruiker){
		List <Gebruiker> gebruikers = new ArrayList<>();
		gebruikers.addAll((ArrayList<Leerling>)leerlingService.findAll());
		gebruikers.addAll((ArrayList<Docent>) docentService.findAll());
		for(Gebruiker gebruiker:gebruikers){
			if(validateGebruiker.getGebruikersnaam().equalsIgnoreCase(gebruiker.getGebruikersnaam())){
				return gebruiker;
			}
		}
		return null;
	}

}
