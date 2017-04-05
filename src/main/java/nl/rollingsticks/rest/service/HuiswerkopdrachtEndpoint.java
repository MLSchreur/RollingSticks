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

import nl.rollingsticks.domain.Huiswerkopdracht;
import nl.rollingsticks.domain.Muziekstuk;
import nl.rollingsticks.domain.model.HuiswerkopdrachtModelBasic;
import nl.rollingsticks.persistence.HuiswerkopdrachtService;
import nl.rollingsticks.persistence.MuziekstukService;

@Path("huiswerkopdracht")
@Component
public class HuiswerkopdrachtEndpoint {
	
	@Autowired
	private HuiswerkopdrachtService huiswerkopdrachtService;
	
	@Autowired
	private MuziekstukService muziekstukService;
	
	/**
	 * Cre&euml;er een nieuwe Huiswerkopdracht.
	 * @param 	huiswerkopdracht Cre&euml;ren van nieuw Huiswerkopdracht.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Id van opgeslagen muziekstuk wordt als text_plain teruggegeven.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postHuiswerkopdracht(Huiswerkopdracht huiswerkopdracht){
		System.out.println("Huiswerk - pre@POST: " + huiswerkopdracht.getId() + " - " + huiswerkopdracht.getNotitie());
		Huiswerkopdracht result = huiswerkopdrachtService.save(huiswerkopdracht);
		System.out.println("Huiswerk - @POST: " + huiswerkopdracht.getId() + " - " + huiswerkopdracht.getNotitie());
		return Response.accepted(result.getId()).build();
	}
	
	/**
	 * Opvragen van de huiswerkopdracht.
	 * Op basis van id worden de gegevens gefilterd via een JSON object teruggegeven.
	 * @param 	id 	Id van de Huiswerkopdracht wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 		 	Code 204 (No Content)<br>
	 * 			Opgevraagde Huiswerkopdracht wordt als JSON object teruggegeven.<br>
	 * 			Muziekstukken kunnen via api's van muziekstuk verder opgevraagd worden.<br>
	 * 			Voorbeeld: { "id": 1, "muziekstukken": [ 1, 2, 3, ], "lesDatum": 1491813000000, "notitie": "Huiswerk met 3 muziekstukken." }
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
			HuiswerkopdrachtModelBasic result = new HuiswerkopdrachtModelBasic(huiswerkopdracht);
			System.out.println("Huiswerk - @GET: (" + id + ") " + huiswerkopdracht.getNotitie());
			return Response.ok(result).build();
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
		Iterable <Huiswerkopdracht> huiswerkopdrachten = huiswerkopdrachtService.findAll();
		ArrayList<HuiswerkopdrachtModelBasic> result = new ArrayList<>();
		for (Huiswerkopdracht huiswerkopdracht : huiswerkopdrachten) {
			result.add(new HuiswerkopdrachtModelBasic(huiswerkopdracht));
		}
		System.out.println("Muziekstuk - @GET: Size ArrayList met huiswerkopdrachten (Model): " + result.size());
		return Response.ok(result).build();
	}
	
	/**
	 * NOG NIET GEBRUIKEN - NOG ONDUIDELIJK WAT ER GEBEURT MET DE GEKOPPELDE MUZIEKSTUKKEN! <br>
	 * Verwijderen van de opgegeven Huiswerkopdracht (id).
	 * @param 	id 	Id van de te verwijderen Huiswerkopdracht wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@DELETE
	@Path("{id}")
	public Response deleteMuziekstukById(@PathParam("id") Long id){
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

	/**
	 * Verwijderen van een Muziekstuk uit de opgegeven Huiswerkopdracht (id).
	 * @param 	id 				Id van de Huiswerkopdracht wordt uit het path gehaald.
	 * @param	muziekstukId	Id van het te verwijderen Muziekstuk wordt uit het path gehaald.
	 * @param	muziekstukDel	Middels een boolean wordt bepaald of het muziekstuk vervolgens ook uit de database verwijderd mag worden.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@DELETE
	@Path("{id}/{muziekstuk_id}/{muziekstuk_delete}")
	public Response deleteMuziekstukFromHuiswerkopdrachtById(
			@PathParam("id") 				Long id, 
			@PathParam("muziekstuk_id") 	Long muziekstukId, 
			@PathParam("muziekstuk_delete") boolean muziekstukDel){
		System.out.println("Huiswerk(muziekstuk) - pre@DELETE: id's provided: " + id + "/" + muziekstukId + "/" + muziekstukDel);
		Huiswerkopdracht huiswerkopdracht = this.huiswerkopdrachtService.findById(id);
		if (huiswerkopdracht == null) {
			System.out.println("Huiswerk - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			Muziekstuk muziekstuk = this.muziekstukService.findById(muziekstukId);
			if (muziekstuk == null) {
				System.out.println("Huiswerk/muziekstuk - Id " + muziekstukId + " bestaat niet.");
				return Response.noContent().build();
			} else {
				huiswerkopdracht.removerMuziekstukFromMuziekstukken(muziekstuk);
				this.huiswerkopdrachtService.save(huiswerkopdracht);
				if (muziekstukDel) {
					this.muziekstukService.deleteById(muziekstukId);
				}
				return Response.accepted().build();
			}
		}
	}

	/**
	 * Toevoegen van Muziekstuk aan opgegeven Huiswerkopdracht (id).
	 * @param 	id 			Id van de Huiswerkopdracht waar een Muziekstuk aan toegevoegd moet worden.
	 * @param	muziekstuk	Muziekstuk dat opgeslagen en gekoppeld moet worden aan de Huiswerkopdracht (id).
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/muziekstuk")
	public Response addMuziekstukToHuiswerkopdracht(@PathParam("id") Long id, Muziekstuk muziekstuk) {
		System.out.println("Huiswerk - pre@PUT (Muziekstuk): id provided: " + id);
		Huiswerkopdracht huiswerkopdracht = this.huiswerkopdrachtService.findById(id);
		if (huiswerkopdracht == null) {
			System.out.println("Huiswerk - Id " + id + " bestaat niet.");
			return Response.noContent().build();
		} else {
			Muziekstuk newMuziekstuk = this.muziekstukService.save(muziekstuk);
			huiswerkopdracht.addMuziekstukToMuziekstukken(newMuziekstuk);
			huiswerkopdrachtService.save(huiswerkopdracht);
			return Response.accepted(newMuziekstuk.getId()).build();
		}
	}

	/**
	 * Toevoegen van een <b>bestaand</b> Muziekstuk aan opgegeven Huiswerkopdracht (id).
	 * @param 	id 			Id van de Huiswerkopdracht waar een Muziekstuk aan toegevoegd moet worden.
	 * @param	muziekstuk	Muziekstuk dat opgeslagen en gekoppeld moet worden aan de Huiswerkopdracht (id).
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 204 (No Content)
	 */	
	@PUT
	@Path("{id}/{muziekstukId}")
	public Response addBestaandMuziekstukToHuiswerkopdracht(@PathParam("id") Long id, @PathParam("muziekstukId") Long muziekstukId) {
		System.out.println("Huiswerk - pre@PUT (Muziekstuk): id provided: " + id + " (" + muziekstukId + ")");
		Huiswerkopdracht huiswerkopdracht = this.huiswerkopdrachtService.findById(id);
		if (huiswerkopdracht != null) {
			Muziekstuk muziekstuk = this.muziekstukService.findById(muziekstukId);
			if (muziekstuk != null) {
				// nog een check of hij al niet toevallig eerder is toegevoegd!! 
				huiswerkopdracht.addMuziekstukToMuziekstukken(muziekstuk);
				huiswerkopdrachtService.save(huiswerkopdracht);
				return Response.accepted().build();
			} else {
				System.out.println("Muziekstuk - Id " + muziekstukId + " bestaat niet.");
				return Response.noContent().build();
			}
		} else {
			System.out.println("Huiswerk - Id " + id + " bestaat niet.");
			return Response.noContent().build();
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
