package nl.rollingsticks.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Eigenaar;
import nl.rollingsticks.persistence.EigenaarService;

@Path("eigenaar")
@Component

public class EigenaarEndpoint {
	@Autowired
	private EigenaarService eigenaarService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postEigenaar(Eigenaar eigenaar){
		System.out.println("pre@POST: (" + eigenaar.getId() + ") " + eigenaar.getEigenaar());
		Eigenaar result = eigenaarService.save(eigenaar);
		System.out.println("@POST: (" + result.getId() + ") " + result.getEigenaar());
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getEigenaarById(@PathParam("id") Long id ) {
		System.out.println("@GET: (" + id + ")");
		Eigenaar result = this.eigenaarService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listEigenaar(){
		System.out.println("@GET: Got the list!");
		Iterable <Eigenaar> result = eigenaarService.findAll();
		return Response.ok(result).build();
	}
	
}
