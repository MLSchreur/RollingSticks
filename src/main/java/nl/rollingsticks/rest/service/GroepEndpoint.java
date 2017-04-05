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

import nl.rollingsticks.domain.Groep;
import nl.rollingsticks.persistence.GroepService;

@Path("groep")
@Component
public class GroepEndpoint {
	@Autowired
	private GroepService groepService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postGroep(Groep groep){
		Groep result = groepService.save(groep);
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getGroepById(@PathParam("id") Long id ) {
		Groep result = this.groepService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGroep(){
		Iterable <Groep> result = groepService.findAll();
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteTekstById(@PathParam("id") Long id){
		this.groepService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Groep groep) {
		this.groepService.save(groep);
		Groep result = groepService.save(groep);
		return Response.accepted(result).build();
	}
}
