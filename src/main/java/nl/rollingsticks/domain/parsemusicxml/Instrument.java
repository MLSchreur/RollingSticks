package nl.rollingsticks.domain.parsemusicxml;

class Instrument {
	Instrument (String nootNaam) {
		this(nootNaam, "");
	}
	Instrument (String nootNaam, String instrumentNaam) {
		this(nootNaam, instrumentNaam, "");
	}
	Instrument (String nootNaam, String instrumentNaam, String instrumentId) {
		this.nootNaam = nootNaam;
		this.instrumentNaam = instrumentNaam;
		this.instrumentId = instrumentId;
	}
	
	String nootNaam;
	String instrumentNaam;
	String instrumentId;
}