package nl.rollingsticks.rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Docent;
import nl.rollingsticks.domain.Gebruiker;
import nl.rollingsticks.domain.Leerling;
import nl.rollingsticks.domain.Muziekstuk;
import nl.rollingsticks.domain.model.MuziekstukModelBasic;
import nl.rollingsticks.persistence.DocentService;
import nl.rollingsticks.persistence.LeerlingService;

/**
 * Gebruiker inlog
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-04
 */

@Path("login")
@Component
public class GebruikerEndpoint {
	
	@Autowired
	private LeerlingService leerlingService;
	
	@Autowired
	private DocentService docentService;
	
	/**
	 * Controleren of de gebruikersnaam en wachtwoord overeenkomen met database
	 * @param 	//
	 * @return 	//
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGebruikers() {
		List <Leerling> leerlingen = (ArrayList <Leerling>)leerlingService.findAll();
		List <Docent> docenten = (ArrayList <Docent>)docentService.findAll();
		ArrayList <Gebruiker> result = new ArrayList<>();
		result.addAll(leerlingen);
		result.addAll(docenten);
		for (Gebruiker gebruiker : result) {
			System.out.println(gebruiker.getVoornaam());
		}
		return Response.ok(result).build();
	}
}
