package nl.rollingsticks.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.parsemusicxml.Compositie;
import nl.rollingsticks.domain.parsemusicxml.ParseMusicXML;

/**
 * Parsen van XML
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-04
 */

@Path("xml")
@Component
public class ParseXMLEndpoint {

	//		@Autowired
	//		private LeerlingService leerlingService;
	//		
	//		@Autowired
	//		private DocentService docentService;
	

	/**
	 * Parsen van XML bestand
	 * @param	id	Id van muziekstuk waarvan XML afgespeeld moet gaan worden
	 * @param	xml Tijdelijk kun je handmatig een xml meegeven die omgezet zal gaan worden.
	 * @return 	Code 200 (Ok) + JSON bestand met omgezette XML van het Muziekstuk<br>
	 * 		 	Code 406 (Not Acceptable) - 1 = Fout opgetreden in de XML Parser. Waarschijnlijk ongeldige XML.
	 */	
	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response parseXML(@PathParam("id") Long id, String xml) {
		System.out.println("XML is binnen(Endpoint)");
		ParseMusicXML parseMusicXML = new ParseMusicXML();
		Compositie compositie = parseMusicXML.parseMusicXML(xml);
		// In geval van een exception in de parser wordt null terug gestuurd.
		if (compositie != null) {
			return Response.ok(compositie).build();
		} else {
			return Response.status(406).entity("1").build();
		}
	}
}
