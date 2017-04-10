package nl.rollingsticks.rest.service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import nl.rollingsticks.domain.Compositie;
import nl.rollingsticks.domain.Maat;
import nl.rollingsticks.domain.Noot;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	
	List<Instrument> instrumenten = new ArrayList<>();

	/**
	 * Parsen van XML bestand
	 * @param	id	Id van muziekstuk waarvan XML afgespeeld moet gaan worden
	 * @param	xml Tijdelijk kun je handmatig een xml meegeven die omgezet zal gaan worden.
	 * @return 	Is allemaal nog een verrassing<br>
	 */	
	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response parseXML(@PathParam("id") Long id, String xml) {
		System.out.println("XML is binnen");

		Compositie compositie = new Compositie();
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				// Dit kan niet buiten de DefaultHandler, aangezien er steeds een nieuwe
				// maat aangemaakt moet worden en dat mag niet wanneer deze een niveau hoger
				// gedefinieerd is.
				// De compositie moet wel een niveau hoger, omdat het resultaat daarvan
				// teruggegeven moet worden.
				Maat maat;
				Noot noot;
				
				// algemene informatie
				boolean isTitle =				false;
				boolean isCredit =				false;
				boolean isMode =				false;
				boolean isBeats =				false;
				boolean isBeatType = 			false;
				
				// overzicht druminstrumenten
				boolean isScoreInstrument =		false;
				boolean isInstrumentNaam = 		false;
				String	instrumentIndex =		null;
				
				// maat
				boolean isMeasure = 			false;
				
				// noot + informatie
				boolean isNote = 				false;
				boolean isLength = 				false;
				boolean isStem = 				false;
				boolean isBeam = 				false;
				boolean isChord = 				false;
				boolean isInstrument = 			false;
				//boolean isHeigth = 			false;

				public void startElement(String uri, String localName, String qName,
						Attributes attributes) throws SAXException {

					switch(qName.toLowerCase()) {
					case "movement-title":		isTitle = true; break;
					case "credit-words":		isCredit = true; break;
					case "mode":				isMode = true; break;
					case "beats":				isBeats = true; break;
					case "beat-type":			isBeatType = true; break;

					case "measure":				isMeasure = true; break;

					case "note":				isNote = true; break;
					case "type":				isLength = true; break;
					case "stem":				isStem = true; break;
					case "beam":				isBeam = true; break;
					case "chord":				isChord = true; break;
					case "instrument":			isInstrument = true; break;
					
					case "score-instrument":	isScoreInstrument = true; break;
					case "instrument-name":		isInstrumentNaam = true; break;
					//case "instrument geen nodig voor heigth":			isHeigth = true; break;
					}
					
					if (isScoreInstrument) {
						instrumentIndex = attributes.getValue("id");
						System.out.println("instrument index     : " + instrumentIndex);
						isScoreInstrument = false;
					}

					// CODE UITBREIDEN
					if (isInstrument) {
						String instrumentId = attributes.getValue("id");
						System.out.println("instrument ID        : " + instrumentId + " - " + omzettenInstrumentId(instrumentId));
						noot.setInstrument(omzettenInstrumentId(instrumentId));
						isInstrument = false;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// Measure - Maat
					if (qName.equalsIgnoreCase("measure")) {
						System.out.println("measure              : " + "einde van nieuwe maat");
						compositie.addMatenToCompositie(maat);
					}

					// Note - Noot
					if (qName.equalsIgnoreCase("note")) {
						System.out.println("note                 : " + "einde van nieuwe noot");
						maat.addNotenToMaat(noot);
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {

					// Titel
					if (isTitle) {
						System.out.println("title                : " + new String(ch, start, length));
						compositie.setTitle(new String(ch, start, length));
						isTitle = false;
					}

					// Credit-words
					if (isCredit) {
						if (length > 5) {
							String creditWords = new String(ch, start, length);
							if (creditWords.substring(0, 5).equals("Tempo")) {
								System.out.println("credit-words         : " + creditWords);
								int tempo = Integer.parseInt(creditWords.substring(6));
								System.out.println("Tempo                : " + tempo);
								compositie.setTempo(tempo);
							}
						}
						isCredit = false;
					}

					// InstrumentNaam
					if (isInstrumentNaam) {
						String instrumentNaam = new String(ch, start, length);
						System.out.println("instrument naam      : " + new String(ch, start, length));
						vastleggenInstrumentenIndex (instrumentIndex, instrumentNaam);
						instrumentIndex = null;
						isInstrumentNaam = false;
					}

					// Mode
					if (isMode) {
						System.out.println("mode                 : " + new String(ch, start, length));
						compositie.setMode(new String(ch, start, length));
						isMode = false;
					}

					// Beats
					if (isBeats) {
						int beats = Integer.parseInt(new String(ch, start, length));
						System.out.println("beats                : " + beats);
						compositie.setBeats(beats);
						isBeats = false;
					}

					// BeatType
					if (isBeatType) {
						int beatType = Integer.parseInt(new String(ch, start, length));
						System.out.println("beatsType            : " + beatType);
						compositie.setBeatType(beatType);
						isBeatType = false;
					}

					// Measure - Maat
					if (isMeasure) {
						System.out.println("measure              : " + "begin van nieuwe maat");
						maat = new Maat();
						isMeasure = false;
					}

					// Note - Noot
					if (isNote) {
						System.out.println("note                 : " + "begin van nieuwe noot");
						noot = new Noot();
						isNote = false;
					}

					// CODE UITBREIDEN
					// Length
					if (isLength) {
						String lengthNote = new String(ch, start, length);
						System.out.println("length/type          : " + lengthNote + " - " + omzettenLengteNoot(lengthNote));
						noot.setLength(omzettenLengteNoot(lengthNote));
						isLength = false;
					}

					// Stem
					if (isStem) {
						System.out.println("stem                 : " + new String(ch, start, length));
						noot.setStem(new String(ch, start, length));
						isStem = false;
					}

					// Beam
					if (isBeam) {
						System.out.println("beam                 : " + new String(ch, start, length));
						noot.setBeam(new String(ch, start, length));
						isBeam = false;
					}

					// Chord
					if (isChord) {
						System.out.println("chord                : " + "chord is true");
						noot.setChord(true);
						isChord = false;
					}

				}
			};

			initInstrumenten(instrumenten);
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
		
		return Response.ok(compositie).build();

	}

	// SAX parser gaat over zijn nek door onderstaande regel:
	//<!DOCTYPE score-partwise PUBLIC "-//Recordare//DTD MusicXML 3.0 Partwise//EN" "http://www.musicxml.org/dtds/partwise.dtd">
	
	// Foutmelding die je krijgt is:
	// Server returned HTTP response code: 403 for URL: http://www.musicxml.org/dtds/partwise.dtd
	
	// Voorkomen door:
	private String verwijderenDoctype (String xml) {
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
		return xmlStart + xml.substring(178);
	}
	
	private int omzettenLengteNoot (String lengthNote) {
		switch (lengthNote.toLowerCase()) {
		case "whole": 		return 1;
		case "half": 		return 2;
		case "quarter": 	return 4;
		case "eighth": 		return 8;
		case "16th": 		return 16;
		case "32nd": 		return 32;
		case "64th": 		return 64;
		}
		// Niets gevonden dan is er iets mis en geven we maar 0 terug. 
		// Dit moet nog wel even netjes in de documentatie van de API vastgelegd wordt,
		// zodat de mensen van de Frontend het ook snappen. ;-)
		return 0;
	}
	
	private String omzettenInstrumentId (String instrumentId) {
		for (Instrument instrument : instrumenten) {
//			if (instrument.instrumentId.equalsIgnoreCase(instrumentId)) {
			if (instrumentId.equalsIgnoreCase(instrument.instrumentId)) {
				return instrument.instrumentNaam;
			}
		}
		// Niets gevonden dan is er iets mis en geven we maar deze tekst terug. 
		// Even overleggen met Frontend wat we dan terug willen geven en vastleggen in de Javadocs.
		return "Instrument onbekend";
	}
	
	// methodes voor Instrumenten tabel (init & vastleggen vanuit XML)
	private void initInstrumenten(List<Instrument> instrumenten) {
		instrumenten.add(new Instrument("c6"));
		instrumenten.add(new Instrument("b5"));
		instrumenten.add(new Instrument("a5", "Crash Cymbal"));				// Crash Bekken
		instrumenten.add(new Instrument("g5", "Ride%g Bell"));				// Hihat Voet ??
		instrumenten.add(new Instrument("g5", "Hi-Hat%g Open"));			// Hihat Open
		instrumenten.add(new Instrument("g5", "Hi-Hat%g Closed"));			// Hihat
		instrumenten.add(new Instrument("f5", "Ride Cymbal"));				// Ride Bekken
		instrumenten.add(new Instrument("e5", ""));							// Kleine Tom ??
		instrumenten.add(new Instrument("d5", "Low Tom"));					// Grote Tom
		instrumenten.add(new Instrument("c5", "Snare Drum"));				// Snare Drum
		instrumenten.add(new Instrument("c5", "Snare%g Ghost Stroke"));		// Ghost Noot
		instrumenten.add(new Instrument("c5", "Stick Click"));				// Rim
		instrumenten.add(new Instrument("b4"));
		instrumenten.add(new Instrument("a4"));
		instrumenten.add(new Instrument("g4"));
		instrumenten.add(new Instrument("f4", "Flloor Tom1"));				// Staande Tom
		instrumenten.add(new Instrument("e4", "Bass Drum"));				// Base Drum
		instrumenten.add(new Instrument("d4", "Hi-Hat@g Foot"));			// Hihat Voet ??
																			// EZdrummer  ??
	}
	
	private void vastleggenInstrumentenIndex (String instrumentIndex, String instrumentNaam) {
		for (Instrument instrument : instrumenten) {
			if (instrument.instrumentNaam.equalsIgnoreCase(instrumentNaam)) {
				//System.out.println("** Match ***");
				instrument.instrumentId = instrumentIndex;
			}
		}
	}
}

class Instrument {
	Instrument (String nootNaam) {
		this(nootNaam, "");
	}
	Instrument (String nootNaam, String instrumentNaam) {
		this.nootNaam = nootNaam;
		this.instrumentNaam = instrumentNaam;
	}
	
	String nootNaam;
	String instrumentId;
	String instrumentNaam;
}