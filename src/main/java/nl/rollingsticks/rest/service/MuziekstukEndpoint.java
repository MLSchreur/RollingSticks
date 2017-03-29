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

import nl.rollingsticks.domain.Muziekstuk;
import nl.rollingsticks.domain.model.MuziekstukModelBasic;
import nl.rollingsticks.persistence.MuziekstukService;

@Path("muziekstuk")
@Component
public class MuziekstukEndpoint {

	@Autowired
	private MuziekstukService muziekstukService;
	
	/**
	 * Creëer een nieuw stuk Muziekstuk.
	 * @param 	muziekstuk Creëren van nieuw Muziekstuk.
	 * @return 	Code 202 (Accepted) - incl id van muziekstuk.
	 */	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postTekst(Muziekstuk muziekstuk){
		System.out.println("pre@POST: " + muziekstuk.getId() + " - " + muziekstuk.getOmschrijving());
		Muziekstuk result = muziekstukService.save(muziekstuk);
		System.out.println("@POST: " + result.getId() + " - " + result.getOmschrijving());
		return Response.accepted(result.getId()).build();
	}

	/**
	 * Opslaan van XML bij meegegeven Muziekstuk (id)
	 * @param	id	id van het muziekstuk wordt uit het path gehaald.
	 * @param 	xml Opslaan van XML bij meegegeven id van muziekstuk.
	 * @return 	Code 202 (Accepted)
	 * @return 	Code 204 (No Content)
	 */	
	@POST
	@Consumes(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response postXMLtoBladmuziekById(@PathParam("id") Long id, String xml) {
		System.out.println("pre@POST-XML: id provided: " + id);
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk == null) {
			return Response.noContent().build();
		} else {
			System.out.println("@POST-XML: " + muziekstuk.getId() + " - " + muziekstuk.getOmschrijving());
			muziekstuk.setXml(xml);
			this.muziekstukService.save(muziekstuk);
			return Response.accepted().build();
		}
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/img")
	public Response postIMGtoBladmuziekById(@PathParam("id") Long id, byte[] img) {
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		muziekstuk.setPictogram(img);
		this.muziekstukService.save(muziekstuk);
		return Response.accepted().build();
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/mp3")
	public Response postMP3toBladmuziekById(@PathParam("id") Long id, byte[] mp3) {
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		muziekstuk.setPictogram(mp3);
		this.muziekstukService.save(muziekstuk);
		return Response.accepted().build();
	}

	/**
	 * Opvragen van het muziekstuk.
	 * Op basis van id worden de gegevens gefilterd via een JSON object teruggegeven.
	 * @param 	id 	id van het muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)
	 * @return 	Code 204 (No Content)
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getBladmuziekById(@PathParam("id") Long id ) {
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk == null) {
			return Response.noContent().build();
		} else {
			MuziekstukModelBasic result = new MuziekstukModelBasic(muziekstuk);
			return Response.ok(result).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listTekst(){
		System.out.println("@GET: Got the list!");
		Iterable <Muziekstuk> result = muziekstukService.findAll();
		return Response.ok(result).build();
	}
	
	/**
	 * Opvragen van XML bestand van Muziekstuk (id).
	 * @param 	id 	id van het muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)
	 * @return 	Code 204 (No Content)
	 */	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response getXMLfromBladmuziekById(@PathParam("id") Long id) {
		Muziekstuk result = this.muziekstukService.findById(id);
		if (result == null) {
			return Response.noContent().build();
		} else {
			return Response.ok(result.getXml()).build();
		}
	}	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteBladmuziekById(@PathParam("id") Long id){
		this.muziekstukService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Muziekstuk muziekstuk) {
		this.muziekstukService.save(muziekstuk);
		Muziekstuk result = muziekstukService.save(muziekstuk);
		return Response.accepted(result).build();
	}
	
	
//
//	//////////////////////
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("{id}/artiest")
//	public Response getArtiestById(@PathParam("id") Long id ) {
//		Muziekstuk result = this.muziekstukService.findById(id);
//		return Response.ok(result).build();
//	}
//	@POST
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Path("{id}/artiest")
//	public Response postArtiestById(@PathParam("id") Long id, String artiest) {
//		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
//		muziekstuk.setArtiest(artiest);
//		this.muziekstukService.save(muziekstuk);
//		return Response.accepted().build();
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("{id}/omschrijving")
//	public Response getArtiestById(@PathParam("id") Long id ) {
//		Muziekstuk result = this.muziekstukService.findById(id);
//		return Response.ok(result).build();
//	}
//	@POST
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Path("{id}/omschrijving")
//	public Response postOmschrijvingById(@PathParam("id") Long id, String omschrijving) {
//		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
//		muziekstuk.setOmschrijving(omschrijving);
//		this.muziekstukService.save(muziekstuk);
//		return Response.accepted().build();
//	}
}

