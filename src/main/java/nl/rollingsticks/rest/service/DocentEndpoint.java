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

import nl.rollingsticks.domain.Docent;
import nl.rollingsticks.persistence.DocentService;


@Path("docent")
@Component
public class DocentEndpoint {
	@Autowired
	private DocentService docentService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postDocent(Docent docent){
		Docent result = docentService.save(docent);
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listDocent(){
		Iterable <Docent> result = docentService.findAll();
		return Response.ok(result).build();
	}
	
	////////CH
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/gebruikersnaam")
	public Response postGebruikersnaamById(@PathParam("id") Long id, String gebruikersnaam) {
		Docent docent = this.docentService.findById(id);
		docent.setGebruikersnaam(gebruikersnaam);
		this.docentService.save(docent);
		return Response.accepted().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/wachtwoord")
	public Response postWachtwoordById(@PathParam("id") Long id, String wachtwoord) {
		Docent docent = this.docentService.findById(id);
		docent.setWachtwoord(wachtwoord);
		this.docentService.save(docent);
		return Response.accepted().build();
	}
}


