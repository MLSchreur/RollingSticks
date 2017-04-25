package nl.rollingsticks.domain.parsemusicxml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseMusicXML {

	List<Instrument> instrumenten = new ArrayList<>();

	public Compositie parseMusicXML(String xml) {
		System.out.println("XML is ontvangen in de parseMusicXML methode. Parsen gaat beginnen.");

		Compositie compositie = new Compositie();
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				// De definitie van maat & noot kan niet buiten de DefaultHandler, 
				// aangezien er steeds een nieuwe maat / noot aangemaakt moet worden.
				// Dat mag niet wanneer deze een niveau hoger gedefinieerd is.
				// De compositie moet wel een niveau hoger, omdat het resultaat daarvan
				// teruggegeven moet worden.
				Maat maat;
				Noot noot;
				
				// algemene informatie
				boolean isTitle =					false;
				boolean isCredit =					false;
				boolean isMode =					false;
				boolean isBeats =					false;
				boolean isBeatType = 				false;
				
				// overzicht druminstrumenten voor instrumentenIndex
				boolean isInstrumentIndexId =		false;
				boolean isInstrumentIndexNaam = 	false;
				String	instrumentIndexId =			null;
				
				// maat
				boolean isMeasure = 				false;
				
				// noot + informatie
				boolean isNote = 					false;
				boolean isLength = 					false;
				boolean isStem = 					false;
				boolean isBeam = 					false;
				boolean isChord = 					false;
				boolean isInstrument = 				false;
				
				// rustnoot + informatie
				boolean isRest = 					false;
				boolean isDuration = 				false;

				@Override
				public void startElement(String uri, String localName, String qName,
						Attributes attributes) throws SAXException {

					// De meeste informatie wordt uit de content tussen de tags gehaald.
					// Voor de instrumentenIndex en voor de gebruikte instrumenten wordt 
					// de informatie uit de attribuut van de tag gehaald.
					switch(qName.toLowerCase()) {
					case "movement-title":		isTitle = true; break;
					case "credit-words":		isCredit = true; break;
					case "mode":				isMode = true; break;
					case "beats":				isBeats = true; break;
					case "beat-type":			isBeatType = true; break;

					case "measure":				isMeasure = true; break;

					case "note":				isNote = true; break;
					case "chord":				isChord = true; break;
					case "instrument":			isInstrument = true; break; // zowel voor instrument als nootNaam
					case "type":				isLength = true; break;
					case "stem":				isStem = true; break;
					case "beam":				isBeam = true; break;
					
					case "score-instrument":	isInstrumentIndexId = true; break;
					case "instrument-name":		isInstrumentIndexNaam = true; break;

					case "rest":				isRest = true; break;
					case "duration":			if(isRest) isDuration = true; break;		// alleen als er een <rest> tag is gevonden mag er naar de duration gekeken worden.
					}
					
					// score-instrument = index van de instrumten
					if (isInstrumentIndexId) {
						instrumentIndexId = attributes.getValue("id");
						isInstrumentIndexId = false;
					}

					// instrument zelf voor zowel de nootNaam als het instrument
					// De ID staat in de content en wordt via de eerder opgebouwde index
					//	omgezet naar zowel de nootNaam als het instrument zelf.
					if (isInstrument) {
						String instrumentId = attributes.getValue("id");
						Instrument instrument = opzoekenInstrument(instrumentId);
						noot.setInstrumentEnNootNaam(instrument);
						isInstrument = false;
					}

				}

				@Override
				public void characters(char ch[], int start, int length) throws SAXException {

					// Titel
					if (isTitle) {
						compositie.setTitle(new String(ch, start, length));
						isTitle = false;
					}

					// Credit-words
					if (isCredit) {
						if (length > 5) {
							String creditWords = new String(ch, start, length);
							if (creditWords.substring(0, 5).equals("Tempo")) {
								System.out.println("credit-words         : " + creditWords);
								try {
									int tempo = Integer.parseInt(creditWords.substring(6));
									compositie.setTempo(tempo);
								}
								catch (NumberFormatException e) {
									compositie.setTempo(0);
									// e.printStackTrace();
									System.out.println(e);;
								}
							}
						}
						isCredit = false;
					}

					// InstrumentIndexNaam
					// De id is al gevonden, nu de bijbehorende naam en dan vastleg in de index
					// zodat bij de noten het instrument teruggestuurd kan worden.
					if (isInstrumentIndexNaam) {
						String instrumentIndexNaam = new String(ch, start, length);
						vastleggenInstrumentenIndex (instrumentIndexId, instrumentIndexNaam);
						instrumentIndexId = null;
						isInstrumentIndexNaam = false;
					}

					// Mode
					if (isMode) {
						compositie.setMode(new String(ch, start, length));
						isMode = false;
					}

					// Beats
					if (isBeats) {
						try {
							int beats = Integer.parseInt(new String(ch, start, length));
							compositie.setBeats(beats);
						} catch (NumberFormatException e) {
							compositie.setBeats(0);
							// e.printStackTrace();
							System.out.println(e);;
						}
						isBeats = false;
					}

					// BeatType
					if (isBeatType) {
						try {
							int beatType = Integer.parseInt(new String(ch, start, length));
							compositie.setBeatType(beatType);
						} catch (NumberFormatException e) {
							compositie.setBeatType(0);
							// e.printStackTrace();
							System.out.println(e);;
						}
						isBeatType = false;
					}

					// Measure - Maat
					// Nieuwe maat -> nieuw nummer genereren en gebruiken voor de constructor van de maat
					if (isMeasure) {
						maat = new Maat(compositie.bepaalNummerMaat());
						isMeasure = false;
					}

					// Note - Noot
					// Nieuwe noot, aanmaken en vullen met de juiste gegevens
					if (isNote) {
						noot = new Noot();
						isNote = false;
					}

					// Length
					if (isLength) {
						String lengthNote = new String(ch, start, length);
						noot.setLength(omzettenLengteNoot(lengthNote));
						isLength = false;
					}

					// Stem
					if (isStem) {
						noot.setStem(new String(ch, start, length));
						isStem = false;
					}

					// Beam
					if (isBeam) {
						noot.setBeam(new String(ch, start, length));
						isBeam = false;
					}

					// Chord
					if (isChord) {
						noot.setChord(true);
						isChord = false;
					}

					// Rest en Duration (bij restnoot bepaalt de duration de lengte van de noot, in alle andere gevallen de tag <type>
					if (isRest && isDuration) {
						try {
							int lengthRest = Integer.parseInt(new String(ch, start, length));
							noot.setLength(lengthRest);
						} catch (NumberFormatException e) {
							noot.setLength(0);
							// e.printStackTrace();
							System.out.println(e);;
						}
						Instrument instrument = new Instrument("", "Rest");
						noot.setInstrumentEnNootNaam(instrument);
						isRest = false;
						isDuration = false;
					}
				}

				@Override
				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// Measure - Maat
					// Einde maat, dus maat aan compositie toevoegen
					if (qName.equalsIgnoreCase("measure")) {
						compositie.addMatenToCompositie(maat);
					}

					// Note - Noot
					// Einde noot, dus noot aan maat toevoegen
					if (qName.equalsIgnoreCase("note")) {
						maat.addNotenToMaat(noot);
					}
				}
			};

			// Eerst even de instrumentenIndex initialiseren, zodat deze later gevuld kan worden met de juiste instrumenten.
			initInstrumenten(instrumenten);

			// Documentatie van gekopieerd voorbeeld SAX Parser:
			// https://www.mkyong.com/java/how-to-read-xml-file-in-java-sax-parser/
			System.out.println("voor saxparser");
			
			// Verwijderen DOCTYPE attribuut, zodat de SAX Parser niet over zijn nek gaat. 
			String xmlToParse = verwijderenDoctype(xml);
			saxParser.parse(new InputSource(new StringReader(xmlToParse)), handler);
			// aanpassing 1 -> later uit database

			System.out.println("na saxparser");

		} catch (Exception e) {
			e.printStackTrace();
			// Weet niet of dit de netste manier is, maar is wel makkelijker om af te vangen.
			return null;
		}
		
		return compositie;

	}

	// SAX parser gaat over zijn nek door onderstaande regel:
	//<!DOCTYPE score-partwise PUBLIC "-//Recordare//DTD MusicXML 3.0 Partwise//EN" "http://www.musicxml.org/dtds/partwise.dtd">
	
	// Foutmelding die je krijgt is:
	// Server returned HTTP response code: 403 for URL: http://www.musicxml.org/dtds/partwise.dtd
	
	// Voorkomen door:
	private String verwijderenDoctype (String xml) {
		// Attribuut vanaf de tekst "PUBLIC" tot aan de sluit tag die daarna komt, mag verwijderd
		// worden. Anders gaat de SAX parser over zijn nek.
		int startVerwijderenAttribuut = xml.indexOf("PUBLIC");
		int eindeVerwijderenAttribuut = xml.indexOf(">", startVerwijderenAttribuut);
		String teVerwijderenAttribuut = xml.substring(startVerwijderenAttribuut, eindeVerwijderenAttribuut);
		System.out.println("Te verwijderen attribuut: " + teVerwijderenAttribuut);
		return xml.replaceFirst(teVerwijderenAttribuut, "");
	
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
	
	// methodes voor Instrumenten tabel (init & vastleggen vanuit XML)
	private void initInstrumenten(List<Instrument> instrumenten) {
		instrumenten.add(new Instrument("c6"));
		instrumenten.add(new Instrument("b5"));
		instrumenten.add(new Instrument("a5", "Crash Cymbal"));				// Crash Bekken
		instrumenten.add(new Instrument("a5", "Crash Cymbal 2"));			// Crash Bekken
		instrumenten.add(new Instrument("g5", "Hi-Hat%g Open"));			// Hihat Open
		instrumenten.add(new Instrument("g5", "Hi-Hat%g Closed"));			// Hihat
		instrumenten.add(new Instrument("f5", "Ride Cymbal"));				// Ride Bekken
		instrumenten.add(new Instrument("e5", "High Tom"));					// Kleine Tom
		instrumenten.add(new Instrument("d5", "Low Tom"));					// Grote Tom
		instrumenten.add(new Instrument("c5", "Snare Drum"));				// Snare Drum
		instrumenten.add(new Instrument("c5", "Snare%g Ghost Stroke"));		// Ghost Noot
		instrumenten.add(new Instrument("c5", "Snare%g Rim"));				// Rim
		instrumenten.add(new Instrument("b4"));
		instrumenten.add(new Instrument("a4"));
		instrumenten.add(new Instrument("g4"));
		instrumenten.add(new Instrument("f4", "Floor Tom 1"));				// Staande Tom
		instrumenten.add(new Instrument("e4", "Bass Drum"));				// Base Drum
		instrumenten.add(new Instrument("d4", "Hi-Hat%g Foot"));			// Hihat Voet
																			// EZdrummer, bestaat niet, waarschijnlijk verzamelnaam
	}
	
	private void vastleggenInstrumentenIndex (String instrumentIndexId, String instrumentIndexNaam) {
		for (Instrument instrument : instrumenten) {
			if (instrument.instrumentNaam.equalsIgnoreCase(instrumentIndexNaam)) {
				// System.out.println("** Match ***" + instrumentIndexId + " - [" + instrumentIndexNaam + "]");
				instrument.instrumentId = instrumentIndexId;
				return; // niet langer doorgaan dan nodig is.
			}
		}
		// Instrument onbekend, maar wordt wel toegevoegd. Alleen de koppeling naar de naam v/d noot kan niet worden gelegd.
		System.out.println("**** Probleem: onbekend muziekinstrument - " + instrumentIndexId + " - [" + instrumentIndexNaam + "]");
		System.out.println(" -> Combi toevoegen aan index instrumenten, naam v/d noot wordt onbekend.");
		instrumenten.add(new Instrument("onbekend", instrumentIndexNaam, instrumentIndexId));
	}
	
	private Instrument opzoekenInstrument (String instrumentId) {
		for (Instrument instrument : instrumenten) {
			if (instrumentId.equalsIgnoreCase(instrument.instrumentId)) {
				//System.out.println("** Match ***");
				return instrument;
			}
		}
		// Niets gevonden dan is er iets mis en geven we de Id als tekst terug. 
		// Even overleggen met Frontend wat ze eigenlijk terug willen geven en vastleggen in de Javadocs.
		String onbekend = instrumentId + " is onbekend";
		Instrument onbekendInstrument = new Instrument(onbekend, onbekend);
		return onbekendInstrument;
	}
}

