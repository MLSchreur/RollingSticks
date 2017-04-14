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

import nl.rollingsticks.domain.Groep;
import nl.rollingsticks.domain.Leerling;
import nl.rollingsticks.domain.model.LeerlingModelBasic;
import nl.rollingsticks.persistence.GebruikerService;
import nl.rollingsticks.persistence.GroepService;
import nl.rollingsticks.persistence.LeerlingService;

/**
 * Leerling http-methodes
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */

@Path("leerling")
@Component
public class LeerlingEndpoint {

	@Autowired
	private LeerlingService leerlingService;

	@Autowired
	private GebruikerService gebruikerService;

	@Autowired
	private GroepService groepService;

	/**
	 * Aanmaken van nieuwe leerling
	 * @param	leerling Cre&euml;ren van een nieuwe Leerling.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Code 406 (Not acceptable) - 1 = heeft al een id<br>
	 * 			Code 406 (Not acceptable) - 2 = gegevens niet goed ingevuld<br>
	 * 			Code 406 (Not acceptable) - 3 = gebruikersnaam bestaat al
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postLeerling(Leerling leerling){
		int gebruikerCheck = gebruikerService.postGebruiker(leerling);
		switch(gebruikerCheck){
		case 0: 
			leerlingService.save(leerling);
			System.out.println("leerling opgeslagen"); 
			return Response.accepted().build();
		case 1: System.out.println("Heeft al een id"); return Response.status(406).entity(1).build();
		case 2: System.out.println("Ontbrekende gegevens"); return Response.status(406).entity(2).build();
		case 3: System.out.println("Gebruikersnaam bestaat al"); return Response.status(406).entity(3).build();
		default: System.out.println("Onbekende return."); return Response.status(406).build();	
		}
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
		if (leerling != null){
			LeerlingModelBasic result = new LeerlingModelBasic(leerling);
			return Response.ok(result).build();
		} else {
			System.out.println("Leerling - Id " + id + " bestaat niet.");
			return Response.noContent().build();
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
	 * Verwijderen van de opgegeven Leerling (id) inclusief de koppelingen met de groepen.
	 * @param 	id 	Id van de te verwijderen Leerling wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@DELETE
	@Path("{id}")
	public Response deleteLeerlingById(@PathParam("id") Long id){
		Leerling leerling = leerlingService.findById(id);
		if(leerling != null){
			leerlingService.deleteById(id);
			return Response.accepted().build();
		} else {
			return Response.noContent().build();
		}
	}

	/**
	 * Verwijderen van koppeling tussen Groep(id) met Leerling (id).
	 * @param 	id 					Id van de Leerling waar een Groep van verwijderd moet worden.
	 * @param	huiswerkopdrachtId	Groep die verwijderd moet worden van Leerling (id).
	 * @return 	0 = Leerling en Groep zijn gekoppeld<br>
	 * 		 	1 = Leerling met opgegeven id bestaat niet.<br>
	 * 		 	2 = Groep met opgegeven id bestaat niet.<br>
	 * 		 	3 = Groep met opgegeven id is niet gekoppeld aan de Leerling.
	 */
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/groep/{groep_id}")
	public Response removeGroepFromLeerling(@PathParam("id") Long id, @PathParam("groep_id") Long groepId){
		int removeGroep = leerlingService.removeGroepFromLeerling(id, groepId);
		switch(removeGroep){
		case 0: return Response.accepted().build();
		case 1: return Response.status(406).entity(removeGroep).build();
		case 2: return Response.status(406).entity(removeGroep).build();
		case 3: return Response.status(406).entity(removeGroep).build();
		default: return Response.status(406).build();
		}
	}

	/**
	 * Aanmaken van koppeling tussen Groep(id) met Leerling (id).
	 * @param 	id 					Id van de Leerling waar een Groep aan toegevoegd moet worden.
	 * @param	huiswerkopdrachtId	Groep die toegevoegd moet worden aan Leerling (id).
	 * @return 	0 = Leerling en Groep zijn gekoppeld<br>
	 * 		 	1 = Leerling met opgegeven id bestaat niet.<br>
	 * 		 	2 = Groep met opgegeven id bestaat niet.<br>
	 * 		 	3 = Groep met opgegeven id is al gekoppeld aan de Leerling.
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/groep/{groep_id}")
	public Response addGroepToLeerling(@PathParam("id") Long id, @PathParam("groep_id") Long groepId) {
		Leerling leerling = leerlingService.findById(id);
		if(leerling != null){
			Groep groep = groepService.findById(groepId);
			if(groep != null){
				if(!leerling.isLinkedGroep(groep)){
					leerling.addGroep(groep);
					System.out.println(leerling.getId());
					leerlingService.save(leerling);
					return Response.accepted().build();
				} else {
					return Response.status(406).entity("3").build();
				}
			} else {
				return Response.status(406).entity("2").build();
			}
		} else {
			return Response.status(406).entity("1").build();
		}
	}



}