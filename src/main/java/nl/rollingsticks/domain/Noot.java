package nl.rollingsticks.domain;

/**
 * Noten
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */

public class Noot {
	
	private String length;
    private String stem;
    private String beam;
    private String heigth;
    private boolean chord;
    private String instrument;
	
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public String getBeam() {
		return beam;
	}
	public void setBeam(String beam) {
		this.beam = beam;
	}
	public String getHeigth() {
		return heigth;
	}
	public void setHeigth(String heigth) {
		this.heigth = heigth;
	}
	public boolean isChord() {
		return chord;
	}
	public void setChord(boolean chord) {
		this.chord = chord;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	
}
