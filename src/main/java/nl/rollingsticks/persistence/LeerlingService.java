package nl.rollingsticks.persistence;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Groep;
import nl.rollingsticks.domain.Leerling;

@Service
@Transactional
public class LeerlingService {
	
	@Autowired
	private LeerlingRepository leerlingRepository;
	
	@Autowired
	private GroepService groepService;

	public Leerling save(Leerling leerling){
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
	 * 
	 * @param leerling Leerling van de Drumschool
	 * @return de nieuwe id of ander <ul>
	 * <li>-1 als de leerling een id heeft</li>
	 * <li>-2 als leerling achternaam/voornaam of gebruikersnaam is null</li>
	 * <li>-3 als gebruikersnaam al bestaat</li>
	 * </ul>
	 */
//	public long newLeerling(Leerling leerling){
//		if (leerling.getId() != 0){
//			return -1;
//		} else if (leerling.getAchternaam() == null || leerling.getVoornaam() == null || leerling.getGebruikersnaam()== null ){
//			return -2;
//		} else {
//			List<Leerling> leerlingen = new ArrayList<>();
//			leerlingen = (List<Leerling>) this.leerlingRepository.findAll();
//			for (int i = 0; i < leerlingen.size(); i++) {
//				if(leerlingen.get(i).getGebruikersnaam().equals(leerling.getGebruikersnaam())){
//					return -3;
//				}
//			}
//		}
//		Leerling result = this.leerlingRepository.save(leerling);	
//		return result.getId();
//	}
	
	//Toevoegen van groep aan leerling
	public boolean addGroepToLeerling(long id, long groep_id){
		Leerling leerling = this.findById(id);
		if(leerling != null){
			System.out.println("leerling bestaat");
			Groep groep = groepService.findById(groep_id);
			if(groep != null){
				System.out.println("groep bestaat");
				leerling.addGroep(groep);
				System.out.println(leerling.getId() + " " + leerling.getGebruikersnaam());
				leerlingRepository.save(leerling);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Verwijderen van koppeling tussen Groep(id) met Leerling (id).
	 * @param 	id 					Id van de Leerling waar een Groep van verwijderd moet worden.
	 * @param	huiswerkopdrachtId	Groep die verwijderd moet worden van Leerling (id).
	 * @return 	0 = Leerling en Groep zijn gekoppeld<br>
	 * 		 	1 = Leerling met opgegeven id bestaat niet.<br>
	 * 		 	2 = Groep met opgegeven id bestaat niet.<br>
	 * 		 	3 = Groep met opgegeven id is niet gekoppeld aan de Leerling.
	 */
	public int removeGroepFromLeerling(long id, long groepId){
		Leerling leerling = this.findById(id);
		if(leerling != null ){
			System.out.println("Leerling met id " + id + " bestaat.");
			Groep groep = groepService.findById(groepId);
			if(groep != null){
				System.out.println("Groep met id " + groepId + " bestaat.");
				if(leerling.isLinkedGroep(groep)){
					leerling.removeGroepFromGroepen(groep);
					this.save(leerling);
					System.out.println("Koppeling tussen groep met id " + groepId + " en leerling met id " + id + " is verwijderd");
					return 0;
				} else {
					System.out.println("Groep met id " + groepId + " is niet gekoppeld met leerling met id " + id);
					return 3;
				}
			} else {
				System.out.println("Groep met id " + groepId + " bestaat niet .");
				return 2;
			}
		} else {
			System.out.println("Leerling met id " + id + " bestaat niet.");
			return 1;
		}
	}
}