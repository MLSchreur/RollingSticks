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

import nl.rollingsticks.domain.Bladmuziek;
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
		System.out.println("@POST: " + bladmuziek.getOmschrijving());
		bladmuziekService.save(bladmuziek);
		return Response.accepted(bladmuziek).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listTekst(){
		System.out.println("@GET: Got the list!");
		Iterable <Bladmuziek> result = bladmuziekService.findAll();
		return Response.ok(result).build();
	}
}
