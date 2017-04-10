package nl.rollingsticks.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import nl.rollingsticks.persistence.DocentService;
import nl.rollingsticks.persistence.LeerlingService;


@Path("docent")
@Component
public class DocentEndpoint {
	@Autowired
	private DocentService docentService;
	
	@Autowired
	private LeerlingService leerlingService;
	
	/**
	 * Aanmaken van nieuwe docent
	 * @param	leerling Cre&euml;ren van nieuwe Leerling.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Code 406 (Not acceptable) - gebruikersnaam bestaat al<br>
	 * 			Id van opgeslagen docent wordt als text_plain teruggegeven.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postDocent(Docent docent){
		List <Leerling> leerlingen = (ArrayList <Leerling>)leerlingService.findAll();
		List <Docent> docenten = (ArrayList<Docent>) docentService.findAll();
		List <Gebruiker> gebruikers = new ArrayList<>();
		for (Leerling ll : leerlingen) {
			gebruikers.add(ll);
		}
		for (Docent dcnt : docenten) {
			gebruikers.add(dcnt);
		}
		if(gebruikers.size() != 0){
			for(Gebruiker gebruiker: gebruikers){
				if(gebruiker.getGebruikersnaam().equalsIgnoreCase(docent.getGebruikersnaam())){
					System.out.println(docent.getGebruikersnaam() + " bestaat al!");
					return Response.status(406).build();
				}
			}
		}
		System.out.println("Docent - pre@POST: " + docent.getId() + " - " + docent.getVoornaam() + " " + docent.getAchternaam());
		Docent result = docentService.save(docent);		
		System.out.println("Docent - @POST: " + result.getId() + " - " + result.getVoornaam() + " " + result.getAchternaam());
		return Response.accepted(result.getId()).build();
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
	public Response deleteLeerlingById(@PathParam("id") Long id){
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
	
	//Moet nog worden aangepast
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putLeerling(Docent docent) {
		this.docentService.save(docent);
		Docent result = docentService.save(docent);
		return Response.accepted(result).build();
	}
	
	
}


