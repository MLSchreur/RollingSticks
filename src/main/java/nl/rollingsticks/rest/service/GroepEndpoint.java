package nl.rollingsticks.rest.service;

import java.util.ArrayList;

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

import nl.rollingsticks.domain.Groep;
import nl.rollingsticks.domain.model.GroepModelBasic;
import nl.rollingsticks.persistence.GroepService;

/**
 * Groepen http-methodes
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */
@Path("groep")
@Component
public class GroepEndpoint {
	
	@Autowired
	private GroepService groepService;
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postGroep(Groep groep){
		Groep result = groepService.save(groep);
		return Response.accepted(result).build();
	}
	
	/**
	 * Opvragen van de groepen.
	 * Op basis van id worden de gegevens gefilterd via een JSON object teruggegeven.
	 * @param 	id 	Id van de Groepen wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Groep met opgegeven id bestaat niet.<br>
	 * 			Opgevraagde Groep wordt als JSON object teruggegeven.<br>
	 * 			Huiswerkopdrachten en Leerlingen kunnen via api's van huiswerkopdracht en leerling verder opgevraagd worden.<br>
	 * 			Voorbeeld: { "id": 1, "naam":"maandag 12:00", "huiswerkopdrachten": [ 1, 2, 3 ], "leerlingen": [ 1, 2, 3 ] }
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getGroepById(@PathParam("id") Long id ) {
		System.out.println("Groep - pre@GET: (" + id + ")");
		Groep groep = this.groepService.findById(id);
		if(groep != null){
			GroepModelBasic result = new GroepModelBasic(groep);
			System.out.println("Groep - @GET: (" + id + ") " + groep.getNaam());
			return Response.ok(result).build();	
		} else {
			return Response.status(406).entity(1).build();
		}
	}
	
	/**
	 * Opvragen van alle Groepen.
	 * @return 	Code 200 (OK)<br>
	 * 			Alle Groepen worden als JSON objecten teruggegeven.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGroep(){
		System.out.println("@GET: Got the list!");
		Iterable <Groep> groepen = groepService.findAll();
		ArrayList<GroepModelBasic> result = new ArrayList<>();
		for (Groep groep : groepen) {
			result.add(new GroepModelBasic(groep));
		}
		System.out.println("Groep - @GET: Size ArrayList met groepen (Model): " + result.size());
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteTekstById(@PathParam("id") Long id){
		this.groepService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Groep groep) {
		this.groepService.save(groep);
		Groep result = groepService.save(groep);
		return Response.accepted(result).build();
	}
}
