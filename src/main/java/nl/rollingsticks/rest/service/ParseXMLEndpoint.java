package nl.rollingsticks.rest.service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

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
	 * @return 	Is allemaal nog een verrassing<br>
	 */	
	@POST
	@Consumes(MediaType.TEXT_XML)
	@Path("{id}")
	public Response parseXML(@PathParam("id") Long id, String xml) {
		System.out.println("XML is binnen");

		try {
			System.out.println("Plek 1");

			SAXParserFactory factory = SAXParserFactory.newInstance();
			System.out.println("Plek 2");
			SAXParser saxParser = factory.newSAXParser();
			System.out.println("Plek 3");

			DefaultHandler handler = new DefaultHandler() {

				boolean bfname = false;
				boolean blname = false;
				boolean bnname = false;
				boolean bsalary = false;
				
				boolean bTitle =	false;
				boolean bCredit =	false;

				public void startElement(String uri, String localName,String qName,
						Attributes attributes) throws SAXException {

					
					// sysout voor begin tag
					//System.out.println("Start Element :" + qName);

					// Titel
					if (qName.equalsIgnoreCase("movement-title")) {
						System.out.println("Start Element 	    : " + qName);
						bTitle = true;
					}
					
					// Credit words (voor Tempo)
					if (qName.equalsIgnoreCase("credit-words")) {
//						System.out.println("Start Element 	: " + qName);
						bCredit = true;
					}
					
					
					if (qName.equalsIgnoreCase("FIRSTNAME")) {
						bfname = true;
					}

					if (qName.equalsIgnoreCase("LASTNAME")) {
						blname = true;
					}

					if (qName.equalsIgnoreCase("NICKNAME")) {
						bnname = true;
					}

					if (qName.equalsIgnoreCase("SALARY")) {
						bsalary = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// sysout voor endtag
					//System.out.println("End Element :" + qName);

					// Titel
					if (qName.equalsIgnoreCase("movement-title")) {
						System.out.println("End Element         : " + qName);
					}

					// Credit-words
//					if (qName.equalsIgnoreCase("credit-words")) {
//						System.out.println("End Element         : " + qName);
//					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {

					// Titel
					if (bTitle) {
						System.out.println("Title               : " + new String(ch, start, length));
						bTitle = false;
					}

					// Credit-words
					if (bCredit) {
						if (length > 5) {
							if (new String(ch, start, length).substring(0, 5).equals("Tempo")) {
								System.out.println("Start Element 	    : " + "credit-words");
								System.out.println("Credit-words        : " + new String(ch, start, length));
								System.out.println("End Element 	    : " + "credit-words");
							}
						}
						bCredit = false;
					}

					if (bfname) {
						System.out.println("First Name : " + new String(ch, start, length));
						bfname = false;
					}
				}
			};

			System.out.println("voor saxparser");
			saxParser.parse("c:\\Java\\Blof.xml", handler);
			// aanpassing 1 -> later uit database

//			saxParser.parse(xml, handler);
			System.out.println("na saxparser");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.ok().build();

	}
}
