package nl.rollingsticks.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Compositie met maten
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-04-06
 */

public class Compositie {
	
	private String title;
	private int tempo;
	private String mode;
    private int beats;
    private int beatType;
    
    private List<Maat> maten = new ArrayList<Maat>();
	
    public List<Maat> getMaten() {
		return maten;
	}
	public void setMaten(List<Maat> maten) {
		this.maten = maten;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getBeats() {
		return beats;
	}
	public void setBeats(int beats) {
		this.beats = beats;
	}
	public int getBeatType() {
		return beatType;
	}
	public void setBeatType(int beatType) {
		this.beatType = beatType;
	}
    
    
}
