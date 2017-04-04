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
	public Response postMuziekstuk(Muziekstuk muziekstuk){
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
	public Response postXMLtoMuziekstukById(@PathParam("id") Long id, String xml) {
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

	/**
	 * Opslaan van pictogram bij meegegeven Muziekstuk (id)
	 * @param	id	id van het muziekstuk wordt uit het path gehaald.
	 * @param 	img Opslaan van pictogram bij meegegeven id van muziekstuk.
	 * @return 	Code 202 (Accepted)
	 * @return 	Code 204 (No Content)
	 */	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/img")
	public Response postImgtoMuziekstukById(@PathParam("id") Long id, byte[] img) {
		System.out.println("pre@POST-img: id provided: " + id);
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk == null) {
			return Response.noContent().build();
		} else {
			System.out.println("@POST-img: " + muziekstuk.getId() + " - " + muziekstuk.getOmschrijving());
			muziekstuk.setPictogram(img);
			this.muziekstukService.save(muziekstuk);
			return Response.accepted().build();
		}
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/mp3")
	public Response postMP3toMuziekstukById(@PathParam("id") Long id, byte[] mp3) {
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
	public Response getMuziekstukkById(@PathParam("id") Long id ) {
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		if (muziekstuk == null) {
			return Response.noContent().build();
		} else {
			MuziekstukModelBasic result = new MuziekstukModelBasic(muziekstuk);
			return Response.ok(result).build();
		}
	}
	
	/**
	 * Opvragen van alle muziekstukken.
	 * @return 	Code 200 (OK)
	 */	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listMuziekstuk(){
		System.out.println("@GET: Got the list!");
		Iterable <Muziekstuk> muziekstukken = muziekstukService.findAll();
		ArrayList <MuziekstukModelBasic> result = new ArrayList<>();
		for (Muziekstuk muziekstuk: muziekstukken) {
			result.add(new MuziekstukModelBasic(muziekstuk));
		}
		System.out.println("Size ArrayList met muziekstukken (Model): " + result.size());
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
	public Response getXMLFromMuziekstukById(@PathParam("id") Long id) {
		Muziekstuk result = this.muziekstukService.findById(id);
		if (result == null) {
			return Response.noContent().build();
		} else {
			return Response.ok(result.getXml()).build();
		}
	}	
	
	/**
	 * Opvragen van pictogram van Muziekstuk (id).
	 * @param 	id 	id van het muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)
	 * @return 	Code 204 (No Content)
	 */	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/img")
	public Response getImgFromMuziekstukById(@PathParam("id") Long id) {
		Muziekstuk result = this.muziekstukService.findById(id);
		if (result == null) {
			return Response.noContent().build();
		} else {
			return Response.ok(result.getPictogram()).build();
		}
	}	
	
	/**
	 * Opvragen van pictogram van Muziekstuk (id).
	 * @param 	id 	id van het muziekstuk wordt uit het path gehaald.
	 * @return 	Code 200 (OK)
	 * @return 	Code 204 (No Content)
	 */	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteMuziekstukById(@PathParam("id") Long id){
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
}

