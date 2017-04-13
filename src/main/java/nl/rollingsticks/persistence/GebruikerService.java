package nl.rollingsticks.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

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
	
	public boolean checkGebruikersnaam(Gebruiker checkGebruiker){
		List <Gebruiker> gebruikers = new ArrayList<>();
		gebruikers.addAll((ArrayList<Leerling>)leerlingService.findAll());
		gebruikers.addAll((ArrayList<Docent>) docentService.findAll());
		if(gebruikers.size() != 0){
			for(Gebruiker gebruiker: gebruikers){
				if(gebruiker.getGebruikersnaam().equalsIgnoreCase(checkGebruiker.getGebruikersnaam())){
					System.out.println(checkGebruiker.getGebruikersnaam() + " bestaat al!");
					return true;
				}
			}
		}
		return false;		
	}
}
