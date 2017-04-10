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

import nl.rollingsticks.domain.Muziekstuk;
import nl.rollingsticks.domain.model.MuziekstukModelBasic;
import nl.rollingsticks.persistence.MuziekstukService;

/**
 * Muziekstuk http-methodes
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */ 

@Path("muziekstuk")
@Component
public class MuziekstukEndpoint {

	@Autowired
	private MuziekstukService muziekstukService;
	
	/**
	 * Cre&euml;er een nieuw Muziekstuk.
	 * @param 	muziekstuk Cre&euml;ren van nieuw Muziekstuk.
	 * @return 	Code 202 (Accepted)<br>
	 * 			Id van opgeslagen muziekstuk wordt als text_plain teruggegeven.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postMuziekstuk(Muziekstuk muziekstuk){
		System.out.println("Muziekstuk - pre@POST: " + muziekstuk.getId() + " - " + muziekstuk.getOmschrijving());
		Muziekstuk result = muziekstukService.save(muziekstuk);
		System.out.println("Muziekstuk - @POST: " + result.getId() + " - " + result.getOmschrijving());
		return Response.accepted(result.getId()).build();
	}

	/**
	 * Opslaan van XML bij meegegeven Muziekstuk (id)
	 * @param	id	Id van het Muziekstuk wordt uit het path gehaald.
	 * @param 	xml Opslaan van XML bij meegegeven id van muziekstuk.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Muziekstuk met opgegeven id bestaat niet.
	 */	
	@PUT
	@Consumes(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response addXMLtoMuziekstukById(@PathParam("id") Long id, String xml) {
		System.out.println("Muziekstuk - pre@POST-XML: id provided: " + id);
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk != null) {
			System.out.println("Muziekstuk - @POST-XML: " + muziekstuk.getId() + " - " + muziekstuk.getOmschrijving());
			muziekstuk.setXml(xml);
			this.muziekstukService.save(muziekstuk);
			return Response.accepted().build();
		} else {
			return Response.status(406).entity("1").build();
		}
	}

	/**
	 * Opslaan van pictogram bij meegegeven Muziekstuk (id)
	 * @param	id	Id van het Muziekstuk wordt uit het path gehaald.
	 * @param 	img Opslaan van pictogram bij meegegeven id van muziekstuk.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Muziekstuk met opgegeven id bestaat niet.
	 */	
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/img")
	public Response postImgtoMuziekstukById(@PathParam("id") Long id, byte[] img) {
		System.out.println("Muziekstuk - pre@POST-img: id provided: " + id);
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk != null) {
			System.out.println("Muziekstuk - @POST-img: " + muziekstuk.getId() + " - " + muziekstuk.getOmschrijving());
			muziekstuk.setPictogram(img);
			this.muziekstukService.save(muziekstuk);
			return Response.accepted().build();
		} else {
			return Response.status(406).entity("1").build();
		}
	}

	// Uploaden van mp3 bestand wordt later pas uitgerold.

//	@PUT
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Path("{id}/mp3")
//	public Response postMP3toMuziekstukById(@PathParam("id") Long id, byte[] mp3) {
//		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
//		muziekstuk.setPictogram(mp3);
//		this.muziekstukService.save(muziekstuk);
//		return Response.accepted().build();
//	}

	/**
	 * Opvragen van het muziekstuk.
	 * Op basis van id worden de gegevens gefilterd via een JSON object teruggegeven.
	 * @param 	id 	Id van het Muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 			Opgevraagd Muziekstuk wordt (zonder XML, Pictogram 	&amp; MP3) als JSON object teruggegeven.<br>
	 * 			<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Muziekstuk met opgegeven id bestaat niet.
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getMuziekstukkById(@PathParam("id") Long id ) {
		System.out.println("Muziekstuk - pre@GET: (" + id + ")");
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk != null) {
			MuziekstukModelBasic result = new MuziekstukModelBasic(muziekstuk);
			return Response.ok(result).build();
		} else {
			System.out.println("Muziekstuk - Id " + id + " bestaat niet.");
			return Response.status(406).entity("1").build();
		}
	}
	
	/**
	 * Opvragen van alle Muziekstukken.
	 * @return 	Code 200 (OK)<br>
	 * 			Alle Muziekstukken (zonder XML, Pictogram 	&amp; MP3) worden als JSON objecten teruggegeven.
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listMuziekstuk(){
		System.out.println("Muziekstuk - @GET: Got the list!");
		Iterable <Muziekstuk> muziekstukken = muziekstukService.findAll();
		ArrayList <MuziekstukModelBasic> result = new ArrayList<>();
		for (Muziekstuk muziekstuk: muziekstukken) {
			result.add(new MuziekstukModelBasic(muziekstuk));
		}
		System.out.println("Muziekstuk - @GET: Size ArrayList met muziekstukken (Model): " + result.size());
		return Response.ok(result).build();
	}
	
	/**
	 * Opvragen van XML bestand van Muziekstuk (id).
	 * @param 	id 	Id van het Muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 			XML bestand van Muziekstuk wordt teruggegeven in text_xml vorm.
	 * 		 	Code 406 (Not Acceptable) - 1 = Muziekstuk met opgegeven id bestaat niet.
	 */	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response getXMLFromMuziekstukById(@PathParam("id") Long id) {
		Muziekstuk result = this.muziekstukService.findById(id);
		if (result != null) {
			return Response.ok(result.getXml()).build();
		} else {
			return Response.status(406).entity("1").build();
		}
	}	
	
	/**
	 * Opvragen van pictogram van Muziekstuk (id).
	 * @param 	id 	Id van het Muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)<br>
	 * 			Pictogram van Muziekstuk wordt teruggegeven in base64 codering.
	 * 		 	Code 406 (Not Acceptable) - 1 = Muziekstuk met opgegeven id bestaat niet.
	 */	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/img")
	public Response getImgFromMuziekstukById(@PathParam("id") Long id) {
		Muziekstuk result = this.muziekstukService.findById(id);
		if (result != null) {
			return Response.ok(result.getPictogram()).build();
		} else {
			return Response.status(406).entity("1").build();
		}
	}	
	
	// !!!! Zodra de mp3 ook geïmplementeerd wordt en deze op de fileserver komt, moet de Delete hierop worden aangepast.
	// Wordt de mp3 als Base64 in de database gezet, dan is er geen aanpassing nodig.
	/**
	 * Verwijderen van het opgegeven Muziekstuk (id).
	 * @param 	id 	Id van het te verwijderen Muziekstuk wordt uit het path gehaald.
	 * @return 	Code 202 (Accepted)<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Muziekstuk met opgegeven id bestaat niet.
	 */	
	@DELETE
	@Path("{id}")
	public Response deleteMuziekstukById(@PathParam("id") Long id){
		System.out.println("Muziekstuk - pre@DELETE: id provided: " + id);
		Muziekstuk result = this.muziekstukService.findById(id);
		if (result != null) {
			this.muziekstukService.deleteById(id);
			return Response.accepted().build();
		} else {
			System.out.println("Muziekstuk - Id " + id + " bestaat niet.");
			return Response.status(406).entity("1").build();
		}
	}

	// Voorlopig nog via DELETE/POST muziekstuk bewerken.
	
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response putMuziekstuk(Muziekstuk muziekstuk) {
//		this.muziekstukService.save(muziekstuk);
//		Muziekstuk result = muziekstukService.save(muziekstuk);
//		return Response.accepted(result).build();
//	}
}

