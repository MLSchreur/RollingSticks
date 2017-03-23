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
import nl.rollingsticks.persistence.MuziekstukService;

@Path("muziekstuk")
@Component
public class MuziekstukEndpoint {

	@Autowired
	private MuziekstukService muziekstukService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTekst(Muziekstuk muziekstuk){
		Muziekstuk result = muziekstukService.save(muziekstuk);
		return Response.accepted(result).build();
	}

	@POST
	@Consumes(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response postXMLtoBladmuziekById(@PathParam("id") Long id, String xml) {
		Muziekstuk muziekstuk = this.muziekstukService.findById(id);
		muziekstuk.setXml(xml);
		this.muziekstukService.save(muziekstuk);
		return Response.accepted().build();
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getBladmuziekById(@PathParam("id") Long id ) {
		Muziekstuk result = this.muziekstukService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listTekst(){
		System.out.println("@GET: Got the list!");
		Iterable <Muziekstuk> result = muziekstukService.findAll();
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response getXMLfromBladmuziekById(@PathParam("id") Long id) {
		Muziekstuk result = this.muziekstukService.findById(id);
		String xml = result.getXml();
		return Response.ok(result.getXml()).build();
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
}

