package nl.rollingsticks.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass voor leerlingen en docent
 * @author ProgramIT
 * @version 0.1.0
 * @since 2017-03-23
 */

@MappedSuperclass
public class Gebruiker {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String voornaam;
	private String tussenvoegsel;
	@Column(nullable=false)
	private String achternaam;
	
	@Column(unique=true, nullable=false)
	private String gebruikersnaam;
	private String wachtwoord;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	
	public String getTussenvoegsel() {
		return tussenvoegsel;
	}
	
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}
	
	public String getAchternaam() {
		return achternaam;
	}
	
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	
	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}
	
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
	
	public boolean checkWachtwoord(String gebruikersnaam, String wachtwoord){
		if(this.gebruikersnaam.equals(gebruikersnaam) && this.wachtwoord.equals(wachtwoord)){		
			return true;
		} else {
			return false;
		}
		
	}

}
