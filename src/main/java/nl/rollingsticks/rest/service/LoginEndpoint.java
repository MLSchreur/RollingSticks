package nl.rollingsticks.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Gebruiker;
import nl.rollingsticks.persistence.GebruikerService;


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
	private GebruikerService gebruikerService;

	/**
	 * Controleren van gebruikersnaam en wachtwoord bij inloggen
	 * @param	gebruiker	Een JSON-object van type Gebruiker(Leerling of Docent) waarin gebruikersnaam en wachtwoord moeten worden meegegeven
	 * @return 	Code 200 (OK) - id = D staat voor Docent en L voor Leerling gevolgd door id van de gebruiker<br>
	 * 			Code 406 (Not Acceptable) - 1 = Object is null
	 * 			Code 406 (Not Acceptable) - 2 = gebruiker bestaat niet
	 * 			Code 406 (Not Acceptable) - 3 = wachtwoord kont niet overeen
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response listGebruikers(Gebruiker gebruiker) {
		if(gebruiker != null){
			Gebruiker checkGebruikersnaam = gebruikerService.checkGebruikersnaam(gebruiker);
			if(checkGebruikersnaam != null){
				if(checkGebruikersnaam.checkWachtwoord(gebruiker)){
					if(checkGebruikersnaam.getClass().getSimpleName().equalsIgnoreCase("docent")){
						return Response.ok("D"+checkGebruikersnaam.getId()).build();
					} else {
						return Response.ok("L"+checkGebruikersnaam.getId()).build();
					}
				} else {
					return Response.status(406).entity(3).build();
				}
			} else {
				return Response.status(406).entity(2).build();
			}

		} else {
			return Response.status(406).entity(1).build();
		}
	}
}
