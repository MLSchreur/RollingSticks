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
		Leerling result = leerlingService.save(leerling);
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getLeerlingById(@PathParam("id") Long id ) {
		Leerling result = this.leerlingService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listLeerling(){
		Iterable <Leerling> result = leerlingService.findAll();
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteLeerlingById(@PathParam("id") Long id){
		this.leerlingService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putLeerling(Leerling leerling) {
		this.leerlingService.save(leerling);
		Leerling result = leerlingService.save(leerling);
		return Response.accepted(result).build();
	}
	
	////////CH
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/gebruikersnaam")
	public Response postGebruikersnaamById(@PathParam("id") Long id, String gebruikersnaam) {
		Leerling leerling = this.leerlingService.findById(id);
		leerling.setGebruikersnaam(gebruikersnaam);
		this.leerlingService.save(leerling);
		return Response.accepted().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/wachtwoord")
	public Response postWachtwoordById(@PathParam("id") Long id, String wachtwoord) {
		Leerling leerling = this.leerlingService.findById(id);
		leerling.setWachtwoord(wachtwoord);
		this.leerlingService.save(leerling);
		return Response.accepted().build();
	}
}