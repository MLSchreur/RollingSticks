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

import nl.rollingsticks.domain.Leerling;
import nl.rollingsticks.domain.model.LeerlingModelBasic;
import nl.rollingsticks.persistence.LeerlingService;

@Path("leerling")
@Component
public class LeerlingEndpoint {
	
	@Autowired
	private LeerlingService leerlingService;
	
	/**
	 * Aanmaken van nieuwe leerling
	 * @param	leerling Cre&euml;ren van nieuwe Leerling.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Id van opgeslagen leerling wordt als text_plain teruggegeven.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postLeerling(Leerling leerling){	
		System.out.println("Leerling - pre@POST: " + leerling.getId() + " - " + leerling.getVoornaam() + " " + leerling.getAchternaam());
		Leerling result = leerlingService.save(leerling);		
		System.out.println("Leerling - @POST: " + result.getId() + " - " + result.getVoornaam() + " " + result.getAchternaam());
		return Response.accepted(result.getId()).build();
	}
	
	/**
	 * Opvragen van de leerling.
	 * Op basis van id worden de gegevens gefilterd via een JSON object teruggegeven.
	 * @param 	id 	Id van het Docent wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 		 	Code 204 (No Content)<br>
	 * 			Opgevraagde leerling wordt (zonder wachtwoord) als JSON object teruggegeven.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getLeerlingById(@PathParam("id") Long id ) {
		System.out.println("Leerling - pre@GET: (" + id + ")");
		Leerling leerling = this.leerlingService.findById(id);
		if (leerling == null){
			System.out.println("Leerling - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			LeerlingModelBasic result = new LeerlingModelBasic(leerling);
			return Response.ok(result).build();
		}
	}
	
	/**
	 * Opvragen van alle leerlingen.
	 * @return 	Code 200 (OK)<br>
	 * 			Alle leerlingen (zonder wactwoord) worden als JSON objecten teruggegeven.
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listLeerling(){
		System.out.println("Leerling - @GET: Got the list!");
		List <LeerlingModelBasic> result = new ArrayList <>();
		List <Leerling> leerlingen = (List<Leerling>) leerlingService.findAll();
		for (Leerling leerling : leerlingen) {
			result.add(new LeerlingModelBasic(leerling));
		}
		System.out.println("Leerling - @GET: Size ArrayList met leerlingen (Model): " + result.size());
		return Response.ok(result).build();
	}
	
	/**
	 * Verwijderen van de opgegeven Leerling (id).
	 * @param 	id 	Id van de te verwijderen Leerling wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteLeerlingById(@PathParam("id") Long id){
		System.out.println("Leerling - pre@DELETE: id provided: " + id);
		Leerling result = this.leerlingService.findById(id);
		if (result == null) {
			System.out.println("Leerling - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			this.leerlingService.deleteById(id);
			return Response.accepted().build();
		}
	}

	
	//Moet nog worden aangepast
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putLeerling(Leerling leerling) {
		this.leerlingService.save(leerling);
		Leerling result = leerlingService.save(leerling);
		return Response.accepted(result).build();
	}
	
}