package nl.rollingsticks.domain.parsemusicxml;

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