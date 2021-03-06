package nl.rollingsticks.rest.service;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Docent;
import nl.rollingsticks.persistence.DocentService;
import nl.rollingsticks.persistence.GebruikerService;

/**
 * Docent http-methodes
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */
@Path("docent")
@Component
public class DocentEndpoint {
	@Autowired
	private DocentService docentService;
	
	@Autowired
	private GebruikerService gebruikerService;
	
	/**
	 * Aanmaken van nieuwe docent
	 * @param	docent Cre&euml;ren van een nieuwe Docent.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Code 406 (Not acceptable) - 1 = heeft al een id<br>
	 * 			Code 406 (Not acceptable) - 2 = gegevens niet goed ingevuld<br>
	 * 			Code 406 (Not acceptable) - 3 = gebruikersnaam bestaat al
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postDocent(Docent docent){
		int gebruikerCheck = gebruikerService.postGebruiker(docent);
		switch(gebruikerCheck){
		case 0: 
			docentService.save(docent);
			System.out.println("Docent opgeslagen"); 
			return Response.accepted().build();
		case 1: System.out.println("Heeft al een id"); return Response.status(406).entity(1).build();
		case 2: System.out.println("Ontbrekende gegevens"); return Response.status(406).entity(2).build();
		case 3: System.out.println("Gebruikersnaam bestaat al"); return Response.status(406).entity(3).build();
		default: System.out.println("Onbekende return."); return Response.status(406).build();	
		}
	}
	
	/**
	 * Opvragen van de Docent.
	 * @param 	id 	Id van de Docent wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 		 	Code 204 (No Content)<br>
	 * 			Opgevraagde docent wordt (zonder wachtwoord) als JSON object teruggegeven.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getDocentById(@PathParam("id") Long id){
		System.out.println("Docent - pre@GET: (" + id + ")");
		Docent docent = docentService.findById(id);
		if (docent == null){
			System.out.println("Docent - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			return Response.ok(docent).build();
		}
	}
	
	/**
	 * Opvragen van alle docenten.
	 * @return 	Code 200 (OK)<br>
	 * 			Alle docenten (zonder wactwoord) worden als JSON objecten teruggegeven.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listDocent(){
		System.out.println("Docenten - @GET: Got the list!");
		List <Docent> result = (List<Docent>) docentService.findAll();
		System.out.println("Leerling - @GET: Size ArrayList met leerlingen (Model): " + result.size());
		return Response.ok(result).build();
	}
	
	/**
	 * Verwijderen van de opgegeven Docent (id).
	 * @param 	id 	Id van de te verwijderen docent wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteDocentById(@PathParam("id") Long id){
		System.out.println("Docent - pre@DELETE: id provided: " + id);
		Docent result = this.docentService.findById(id);
		if (result == null) {
			System.out.println("Docent - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			this.docentService.deleteById(id);
			return Response.accepted().build();
		}
	}
	
//	Moet nog worden aangepast
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response putLeerling(Docent docent) {
//		this.docentService.save(docent);
//		Docent result = docentService.save(docent);
//		return Response.accepted(result).build();
//	}
	
}


