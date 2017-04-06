package nl.rollingsticks.rest.service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;

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
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean isTitle =		false;
				boolean isCredit =		false;
				boolean isMode =		false;
				boolean isBeats =		false;
				boolean isBeatType = 	false;
				
				boolean isMeasure = 	false;
				
				boolean isNote = 	false;
				boolean isLength = 	false;
				boolean isStem = 	false;
				boolean isBeam = 	false;
				boolean isChord = 	false;
				boolean isInstrument = 	false;
				//boolean isHeigth = 	false;

				public void startElement(String uri, String localName, String qName,
						Attributes attributes) throws SAXException {

					switch(qName.toLowerCase()) {
					case "movement-title":	isTitle = true; break;
					case "credit-words":	isCredit = true; break;
					case "mode":			isMode = true; break;
					case "beats":			isBeats = true; break;
					case "beat-type":		isBeatType = true; break;

					case "measure":			isMeasure = true; break;

					case "note":			isNote = true; break;
					case "type":			isLength = true; break;
					case "stem":			isStem = true; break;
					case "beam":			isBeam = true; break;
					case "chord":			isChord = true; break;
					case "instrument":		isInstrument = true; break;
					//case "instrument geen nodig":			isHeigth = true; break;
					}
					
					if (isInstrument) {
						System.out.println("instrument           : " + attributes.getValue("id"));
						isInstrument = false;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// Maat
					if (qName.equalsIgnoreCase("measure")) {
						System.out.println("measure              : " + "einde van nieuwe maat");
					}

					// Noot
					if (qName.equalsIgnoreCase("note")) {
						System.out.println("note                 : " + "einde van nieuwe noot");
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {

					// Titel
					if (isTitle) {
						System.out.println("title                : " + new String(ch, start, length));
						isTitle = false;
					}

					// Credit-words
					if (isCredit) {
						if (length > 5) {
							String creditWords = new String(ch, start, length);
							if (creditWords.substring(0, 5).equals("Tempo")) {
								System.out.println("Element              : " + "credit-words");
								System.out.println("credit-words         : " + creditWords);
								int tempo = Integer.parseInt(creditWords.substring(6));
								System.out.println("Tempo                : " + tempo);
							}
						}
						isCredit = false;
					}

					// Mode
					if (isMode) {
						System.out.println("mode                 : " + new String(ch, start, length));
						isMode = false;
					}

					// Beats
					if (isBeats) {
						int beats = Integer.parseInt(new String(ch, start, length));
						System.out.println("beats                : " + beats);
						isBeats = false;
					}

					// BeatType
					if (isBeatType) {
						int beatType = Integer.parseInt(new String(ch, start, length));
						System.out.println("beatsType            : " + beatType);
						isBeatType = false;
					}

					// Measure
					if (isMeasure) {
						System.out.println("measure              : " + "begin van nieuwe maat");
						isMeasure = false;
					}

					// Note
					if (isNote) {
						System.out.println("note                 : " + "begin van nieuwe noot");
						isNote = false;
					}

					// Length
					if (isLength) {
						System.out.println("length/type          : " + new String(ch, start, length));
						isLength = false;
					}

					// Stem
					if (isStem) {
						System.out.println("stem                 : " + new String(ch, start, length));
						isStem = false;
					}

					// Beam
					if (isBeam) {
						System.out.println("beam                 : " + new String(ch, start, length));
						isBeam = false;
					}

					// Chord
					if (isChord) {
						System.out.println("chord                : " + "chord is true");
						isChord = false;
					}

				}
			};

			// Documentatie van gekopieerd voorbeeld SAX Parser:
			// https://www.mkyong.com/java/how-to-read-xml-file-in-java-sax-parser/
			System.out.println("voor saxparser");
			String xmlToParse = verwijderenDoctype(xml);
			saxParser.parse(new InputSource(new StringReader(xmlToParse)), handler);
//			saxParser.parse("c:\\Java\\Blof.xml", handler);
			// aanpassing 1 -> later uit database

//			saxParser.parse(xml, handler);
			System.out.println("na saxparser");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.ok().build();

	}

	// SAX parser gaat over zijn nek door onderstaande regel:
	//<!DOCTYPE score-partwise PUBLIC "-//Recordare//DTD MusicXML 3.0 Partwise//EN" "http://www.musicxml.org/dtds/partwise.dtd">
	
	// Foutmelding die je krijgt is:
	// Server returned HTTP response code: 403 for URL: http://www.musicxml.org/dtds/partwise.dtd
	
	// Voorkomen door 
	private String verwijderenDoctype (String xml) {
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
		return xmlStart + xml.substring(178);
	}
}
