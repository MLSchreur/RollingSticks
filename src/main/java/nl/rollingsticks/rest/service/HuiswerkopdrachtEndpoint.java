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

import nl.rollingsticks.domain.Huiswerkopdracht;
import nl.rollingsticks.persistence.HuiswerkopdrachtService;

@Path("huiswerkopdracht")
@Component
public class HuiswerkopdrachtEndpoint {
	@Autowired
	private HuiswerkopdrachtService huiswerkopdrachtService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postHuiswerkopdracht(Huiswerkopdracht huiswerkopdracht){
		Huiswerkopdracht result = huiswerkopdrachtService.save(huiswerkopdracht);
		return Response.accepted(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getHuiswerkopdrachtById(@PathParam("id") Long id ) {
		System.out.println("@GET: (" + id + ")");
		Huiswerkopdracht result = this.huiswerkopdrachtService.findById(id);
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listHuiswerkopdracht(){
		System.out.println("@GET: Got the list!");
		Iterable <Huiswerkopdracht> result = huiswerkopdrachtService.findAll();
		return Response.ok(result).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response deleteTekstById(@PathParam("id") Long id){
		this.huiswerkopdrachtService.deleteById(id);
		return Response.accepted().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTekst(Huiswerkopdracht huiswerkopdracht) {
		this.huiswerkopdrachtService.save(huiswerkopdracht);
		Huiswerkopdracht result = huiswerkopdrachtService.save(huiswerkopdracht);
		return Response.accepted(result).build();
	}
}
