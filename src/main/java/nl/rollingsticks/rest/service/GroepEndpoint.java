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
import nl.rollingsticks.domain.Huiswerkopdracht;
import nl.rollingsticks.domain.Leerling;
import nl.rollingsticks.domain.model.GroepModelBasic;
import nl.rollingsticks.persistence.GroepService;
import nl.rollingsticks.persistence.HuiswerkopdrachtService;
import nl.rollingsticks.persistence.LeerlingService;

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
	
	@Autowired
	private LeerlingService leerlingService;
	
	@Autowired
	private HuiswerkopdrachtService huiswerkopdrachtService;
	
	/**
	 * Cre&euml;er een nieuwe Groep.
	 * @param 	groep Cre&euml;ren van nieuwe Groep.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Code 406 () - 1 Groepsnaam bestaat al <br>
	 * 			Id van opgeslagen groep wordt als text_plain teruggegeven.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postGroep(Groep groep){
		ArrayList <Groep> groepen = (ArrayList<Groep>) groepService.findAll();
		if (groepen != null) {
			for (Groep grp : groepen) {
				if(grp.getNaam().equalsIgnoreCase(groep.getNaam())){
					System.out.println(groep.getNaam() + " bestaat al.");
					return Response.status(406).entity(1).build();
				}
			}
		} 
		System.out.println("Groep - pre@POST: " + groep.getId() + " - " + groep.getNaam());
		Groep result = groepService.save(groep);
		System.out.println("Groep - @POST: " + groep.getId() + " - " + groep.getNaam());
		return Response.accepted(result.getId()).build();	
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
	
	/**
	 * Verwijderen van de opgegeven Groep (id) inclusief de koppelingen met de leerlingen en huiswerkopdrachten.
	 * @param 	id 	Id van de te verwijderen Leerling wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */
	@DELETE
	@Path("{id}")
	public Response deleteGroep(@PathParam("id") Long id){
			Groep groep = groepService.findById(id);
			if(groep != null){
				System.out.println("groep met id " + id + " bestaat.");
				List<Leerling> leerlingen = groep.getLeerlingen();
				if (leerlingen.size() != 0){
					for (Leerling leerling : leerlingen) {
						leerlingService.removeGroepFromLeerling(leerling.getId(), id);
						System.out.println("Leerling met id "+ leerling.getId() + " verwijderd.");
						leerlingService.save(leerling);
					}
				}
				groepService.deleteById(id);
				System.out.println("Groep verwijderd");
				return Response.accepted().build();
			} else {
				return Response.noContent().build();
			}
	}
	
	/**
	 * Verwijderen van koppeling tussen Huiswerkopdracht(id) met Groep (id).
	 * @param 	id 					Id van de Groep waar een Huiswerkopdracht van verwijderd moet worden.
	 * @param	huiswerkopdrachtId	Huiswerkopdracht die verwijderd moet worden van groep (id).
	 * @return 	0 = Groep en Huiswerkopdracht zijn gekoppeld<br>
	 * 		 	1 = Groep met opgegeven id bestaat niet.<br>
	 * 		 	2 = Huiswerkopdracht met opgegeven id bestaat niet.<br>
	 * 		 	3 = Huiswerkopdracht met opgegeven id is niet gekoppeld aan de Groep.
	 */
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/huiswerkopdracht/{huiswerkopdracht_id}")
	public Response removeHuiswerkopdrachtFromGroep(
			@PathParam("id") Long id, 
			@PathParam("huiswerkopdracht_id") Long huiswerkopdrachtId){
		int removeHuiswerkopdracht = groepService.removeHuiswerkopdrachtFromGroep(id, huiswerkopdrachtId);
		switch(removeHuiswerkopdracht){
		case 0: return Response.accepted().build();
		case 1: return Response.status(406).entity(removeHuiswerkopdracht).build();
		case 2: return Response.status(406).entity(removeHuiswerkopdracht).build();
		case 3: return Response.status(406).entity(removeHuiswerkopdracht).build();
		default: return Response.status(406).entity("onbekende fout").build();
		}
	}
	
	/**
	 * Toevoegen van een <b>bestaande</b> Huiswerkopdracht aan opgegeven Groep (id).
	 * @param 	id 					Id van de Groep waar een Huiswerkopdracht aan toegevoegd moet worden.
	 * @param	huiswerkopdrachtId	Huiswerkopdracht dat opgeslagen en gekoppeld moet worden aan de Groep (id).
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Groep met opgegeven id bestaat niet.<br>
	 * 		 	Code 406 (Not Acceptable) - 2 = Huiswerkopdracht met opgegeven id bestaat niet.<br>
	 * 		 	Code 406 (Not Acceptable) - 3 = Huiswerkopdracht met opgegeven id is al gekoppeld aan de Groep.
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/huiswerkopdracht/{huiswerkopdracht_id}")
	public Response addHuiswerkopdrachtToGroep(@PathParam("id") Long id, @PathParam("huiswerkopdracht_id") Long huiswerkopdrachtId) {
		Groep groep = groepService.findById(id);
		if(groep != null){
			System.out.println("Groep met id " + id + " bestaat.");
			Huiswerkopdracht huiswerkopdracht = huiswerkopdrachtService.findById(huiswerkopdrachtId);
			if (huiswerkopdracht != null){
				System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " bestaat.");
				if(!groep.isLinkedHuiswerkopdracht(huiswerkopdracht)){
					System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " is nog niet gelinkt aan groep met id " + id);
					groep.addHuiswerkopdrachtToHuiswerkopdrachten(huiswerkopdracht);
					groepService.save(groep);
					return Response.accepted().build();
				} else {
					System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " is al gelinkt aan groep met id " + id);
					return Response.status(406).entity(3).build();
				}
			} else {
				System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " bestaat niet.");
				return Response.status(406).entity(2).build();
			}
		} else {
			System.out.println("Groep met id " + id + " bestaat niet.");
			return Response.status(406).entity(1).build();
		}
	}
}
