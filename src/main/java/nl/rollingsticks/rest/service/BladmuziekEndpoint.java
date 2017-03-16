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

import nl.rollingsticks.domain.Bladmuziek;
import nl.rollingsticks.domain.Tekst;
import nl.rollingsticks.persistence.BladmuziekService;

@Path("bladmuziek")
@Component
public class BladmuziekEndpoint {

	@Autowired
	private BladmuziekService bladmuziekService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTekst(Bladmuziek bladmuziek){
		System.out.println("@POST: " + bladmuziek.getId() + " - " + bladmuziek.getOmschrijving());
		Bladmuziek result = bladmuziekService.save(bladmuziek);
		System.out.println("@POST: " + result.getId() + " - " + result.getOmschrijving());
		return Response.accepted(result).build();
	}

	@POST
	@Consumes(MediaType.TEXT_XML)
	@Path("{id}/xml")
	public Response postXMLtoBladmuziekById(@PathParam("id") Long id, String xml) {
		Bladmuziek bladmuziek = this.bladmuziekService.findById(id);
		bladmuziek.setXml(xml);
		this.bladmuziekService.save(bladmuziek);
		return Response.accepted().build();
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/img")
	public Response postIMGtoBladmuziekById(@PathParam("id") Long id, byte[] img) {
		Bladmuziek bladmuziek = this.bladmuziekService.findById(id);
		bladmuziek.setPictogram(img);
		this.bladmuziekService.save(bladmuziek);
		return Response.accepted().build();
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{id}/mp3")
	public Response postMP3toBladmuziekById(@PathParam("id") Long id, byte[] mp3) {
		Bladmuziek bladmuziek = this.bladmuziekService.findById(id);
		bladmuziek.setPictogram(mp3);
		this.bladmuziekService.save(bladmuziek);
		return Response.accepted().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getBladmuziekById(@PathParam("id") Long id ) {
		System.out.println("@GET: (" + id + ")");
		Bladmuziek result = this.bladmuziekService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listTekst(){
		System.out.println("@GET: Got the list!");
		Iterable <Bladmuziek> result = bladmuziekService.findAll();
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteBladmuziekById(@PathParam("id") Long id){
		System.out.println("@DELETE: (" + id+ ")");
		this.bladmuziekService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Bladmuziek bladmuziek) {
		System.out.println("pre@PUT: (" + bladmuziek.getId() + ") " + bladmuziek.getOmschrijving());
		this.bladmuziekService.save(bladmuziek);
		Bladmuziek result = bladmuziekService.save(bladmuziek);
		System.out.println("@PUT: (" + result.getId() + ") " + result.getOmschrijving());
		return Response.accepted(result).build();
	}
}
