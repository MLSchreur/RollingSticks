package nl.rollingsticks.rest.service;

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

import nl.rollingsticks.domain.Huiswerkopdracht;
import nl.rollingsticks.domain.model.MuziekstukModelBasic;
import nl.rollingsticks.persistence.HuiswerkopdrachtService;

@Path("huiswerkopdracht")
@Component
public class HuiswerkopdrachtEndpoint {
	
	@Autowired
	private HuiswerkopdrachtService huiswerkopdrachtService;
	
	/**
	 * Cre&euml;er een nieuwe Huiswerkopdracht.
	 * @param 	huiswerkopdracht Cre&euml;ren van nieuw Huiswerkopdracht.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Id van opgeslagen muziekstuk wordt als text_plain teruggegeven.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postHuiswerkopdracht(Huiswerkopdracht huiswerkopdracht){
		System.out.println("Huiswerk - pre@POST: " + huiswerkopdracht.getId() + " - " + huiswerkopdracht.getNotitie());
		Huiswerkopdracht result = huiswerkopdrachtService.save(huiswerkopdracht);
		System.out.println("Huiswerk - @POST: " + huiswerkopdracht.getId() + " - " + huiswerkopdracht.getNotitie());
		return Response.accepted(result).build();
	}
	
	/**
	 * Opvragen van de huiswerkopdracht.
	 * Op basis van id worden de gegevens gefilterd via een JSON object teruggegeven.
	 * @param 	id 	Id van de Huiswerkopdracht wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 		 	Code 204 (No Content)<br>
	 * 			Opgevraagde Huiswerkopdracht wordt als JSON object teruggegeven.
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getHuiswerkopdrachtById(@PathParam("id") Long id ) {
		System.out.println("Huiswerk - pre@GET: (" + id + ")");
		Huiswerkopdracht huiswerkopdracht = this.huiswerkopdrachtService.findById(id);
		if (huiswerkopdracht == null) {
			System.out.println("Huiswerk - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			System.out.println("Huiswerk - @GET: (" + id + ") " + huiswerkopdracht.getLesDatum());
			return Response.ok(huiswerkopdracht).build();
		}
	}
	
	/**
	 * Opvragen van alle Huiswerkopdrachten.
	 * @return 	Code 200 (OK)<br>
	 * 			Alle Huiswerkopdrachten worden als JSON objecten teruggegeven.
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listHuiswerkopdracht(){
		System.out.println("@GET: Got the list!");
		Iterable <Huiswerkopdracht> result = huiswerkopdrachtService.findAll();
		return Response.ok(result).build();
	}
	
	/**
	 * Verwijderen van de opgegeven Huiswerkopdracht (id).
	 * @param 	id 	Id van de te verwijderen Huiswerkopdracht wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@DELETE
	@Path("{id}")
	public Response deleteTekstById(@PathParam("id") Long id){
		System.out.println("Huiswerk - pre@DELETE: id provided: " + id);
		Huiswerkopdracht huiswerkopdracht = this.huiswerkopdrachtService.findById(id);
		if (huiswerkopdracht == null) {
			System.out.println("Huiswerk - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			this.huiswerkopdrachtService.deleteById(id);
			return Response.accepted().build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Huiswerkopdracht huiswerkopdracht) {
		this.huiswerkopdrachtService.save(huiswerkopdracht);
		Huiswerkopdracht result = huiswerkopdrachtService.save(huiswerkopdracht);
		return Response.accepted(result).build();
	}
}
