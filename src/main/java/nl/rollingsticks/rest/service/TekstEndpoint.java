package nl.rollingsticks.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Tekst;
import nl.rollingsticks.persistence.TekstService;

@Path("tekst")
@Component
public class TekstEndpoint {

	@Autowired
	private TekstService tekstService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTekst(Tekst tekst){
		tekstService.save(tekst);
		System.out.println("@POST: " + tekst.getTekst() + " - " + tekst.getId());
		return Response.accepted(tekst).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listTekst(){
		System.out.println("@GET: Got the list!");
		Iterable <Tekst> result = tekstService.findAll();
		return Response.ok(result).build();
	}
}
