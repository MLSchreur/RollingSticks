package nl.rollingsticks.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Leerling;

@Service
@Transactional
public class LeerlingService {
	@Autowired
	
	private LeerlingRepository leerlingRepository;

	public Leerling save(Leerling leerling) {
		return leerlingRepository.save(leerling);
	}

	public Leerling findById(Long id) {
		return leerlingRepository.findOne(id);
	}
	
	public Iterable <Leerling> findAll(){
		Iterable <Leerling> result = leerlingRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		leerlingRepository.delete(id);
	}
	
	/**
	 * Maak een nieuwe leerling aan in de database. Er worden wat checks uitgevoerd om database foutmeldingen te voorkomen.
	 * @param leerling De id van de nieuwe leerling
	 * @return de nieuwe id of anders <ul>
	 * <li>-1 als de leerling een id heeft
	 * <li>-2 als een voornaam, achternaam of gebruikersnaam gelijk is aan null
	 * <li>-3 als de gebruikersnaam al bestaat
	 * </ul>
	 */
	public long newLeerling(Leerling leerling) {
		if (leerling.getId() != 0) {
			return -1;
		} else if (leerling.getAchternaam() == null || leerling.getVoornaam() == null || leerling.getGebruikersnaam() == null) {
			return -2;
		} else {
			List<Leerling> leerlingen = new ArrayList<>();
			leerlingen = (List<Leerling>) this.leerlingRepository.findAll();
			for (int i=0 ; i<leerlingen.size() ; i++) {
				if (leerlingen.get(i).getGebruikersnaam().equals(leerling.getGebruikersnaam())) {
					return -3;
				}
			}
		}
		Leerling result = this.leerlingRepository.save(leerling);
		return result.getId();
	}
}