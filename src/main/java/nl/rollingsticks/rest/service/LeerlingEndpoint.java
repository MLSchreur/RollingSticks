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

import nl.rollingsticks.domain.Leerling;
import nl.rollingsticks.persistence.LeerlingService;

@Path("leerling")
@Component
public class LeerlingEndpoint {
	@Autowired
	private LeerlingService leerlingService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postLeerling(Leerling leerling){
		System.out.println("pre@POST: (" + leerling.getId() + ") " + leerling.getLeerling());
		Leerling result = leerlingService.save(leerling);
		System.out.println("@POST: (" + result.getId() + ") " + result.getLeerling());
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getEigenaarById(@PathParam("id") Long id ) {
		System.out.println("@GET: (" + id + ")");
		Leerling result = this.leerlingService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listLeerling(){
		System.out.println("@GET: Got the list!");
		Iterable <Leerling> result = leerlingService.findAll();
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteTekstById(@PathParam("id") Long id){
		System.out.println("@DELETE: (" + id+ ")");
		this.leerlingService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Leerling leerling) {
		System.out.println("pre@PUT: (" + leerling.getId() + ") " + leerling.getLeerling());
		this.leerlingService.save(leerling);
		Leerling result = leerlingService.save(leerling);
		System.out.println("@PUT: (" + result.getId() + ") " + result.getLeerling());
		return Response.accepted(result).build();
	}
}
