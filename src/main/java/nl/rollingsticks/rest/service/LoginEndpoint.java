package nl.rollingsticks.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Docent;
import nl.rollingsticks.domain.Gebruiker;
import nl.rollingsticks.domain.Leerling;
import nl.rollingsticks.persistence.DocentService;
import nl.rollingsticks.persistence.LeerlingService;

/**
 * Check op Inloggegevens
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-04
 */

@Path("login")
@Component
public class LoginEndpoint {
	
	@Autowired
	private LeerlingService leerlingService;
	
	@Autowired
	private DocentService docentService;
	
	/**
	 * Controleren van gebruikersnaam en wachtwoord bij inloggen
	 * @param	gebruikerValidatie	gebruikersnaam en wachtwoord worden uit een JSON-object van type Gebruiker gehaald
	 * @return 	Code 200 (OK) - 1 = Docent<br>
	 * 			Code 200 (OK) - 2 = Leerling<br>
	 * 		 	Code 204 (No Content)<br>
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response listGebruikers(Gebruiker gebruikerValidatie) {
		List <Leerling> leerlingen = (ArrayList <Leerling>)leerlingService.findAll();
		List <Gebruiker> gebruikers = new ArrayList<>();
		List <Docent> docenten = (ArrayList<Docent>) docentService.findAll();
		for (Leerling leerling : leerlingen) {
			gebruikers.add(leerling);
		}
		for (Docent docent : docenten) {
			gebruikers.add(docent);
		}
		for (Gebruiker gebruiker : gebruikers) {
			if(gebruiker.getGebruikersnaam().equals(gebruikerValidatie.getGebruikersnaam())){
				if(gebruiker.checkWachtwoord(gebruikerValidatie)){
					System.out.println("Gebruiker:" + gebruikerValidatie.getGebruikersnaam() +" is ok!");
					if(gebruiker.getClass().getSimpleName().equals("Docent")){
						return Response.ok(1).build();
					} else if(gebruiker.getClass().getSimpleName().equals("Leerling")){
						return Response.ok(2).build();
					}
				}else{
					System.out.println("Gebruiker:" + gebruikerValidatie.getGebruikersnaam() + " is niet ok!");
					return Response.noContent().build();
				}
			}
		}
		System.out.println("Gebruiker:" + gebruikerValidatie.getGebruikersnaam() + " is niet gevonden.");
		return Response.noContent().build();
	}
}
