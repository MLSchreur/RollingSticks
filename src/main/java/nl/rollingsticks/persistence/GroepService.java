package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Groep;
import nl.rollingsticks.domain.Huiswerkopdracht;

@Service
@Transactional
public class GroepService {

	@Autowired
	private GroepRepository groepRepository;
	
	@Autowired
	private HuiswerkopdrachtService huiswerkopdrachtService;

	public Groep save(Groep groep){
		return groepRepository.save(groep);
	}

	public Groep findById(Long id) {
		return groepRepository.findOne(id);
	}
	
	public Iterable <Groep> findAll(){
		Iterable <Groep> result = groepRepository.findAll();
		return result;
	}
	
	public void deleteById(Long id) {
		groepRepository.delete(id);
	}
	
	/**
	 * Verwijderen van koppeling tussen Huiswerkopdracht(id) met Groep (id).
	 * @param 	id 					Id van de Groep waar een Huiswerkopdracht van verwijderd moet worden.
	 * @param	huiswerkopdrachtId	Huiswerkopdracht die verwijderd moet worden van groep (id).
	 * @return 	0 = Groep en Huiswerkopdracht zijn gekoppeld<br>
	 * 		 	1 = Groep met opgegeven id bestaat niet.<br>
	 * 		 	2 = Huiswerkopdracht met opgegeven id bestaat niet.<br>
	 * 		 	3 = Huiswerkopdracht met opgegeven id is niet gekoppeld aan de Groep.
	 */
	public int removeHuiswerkopdrachtFromGroep(long id, long huiswerkopdrachtId){
		Groep groep = this.findById(id);
		if(groep != null ){
			System.out.println("Groep met id " + id + " bestaat.");
			Huiswerkopdracht huiswerkopdracht = huiswerkopdrachtService.findById(huiswerkopdrachtId);
			if(huiswerkopdracht != null){
				System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " bestaat.");
				if(groep.isLinkedHuiswerkopdracht(huiswerkopdracht)){
					groep.removeHuiswerkopdrachtFromHuiswerkopdrachten(huiswerkopdracht);
					this.save(groep);
					System.out.println("Koppeling tussen huiswerkopdracht met id " + huiswerkopdrachtId + " en groep met id " + id + " is verwijderd");
					return 0;
				} else {
					System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " is niet gekoppeld met groep met id " + id);
					return 3;
				}
			} else {
				System.out.println("Huiswerkopdracht met id " + huiswerkopdrachtId + " bestaat niet .");
				return 2;
			}
		} else {
			System.out.println("Groep met id " + id + " bestaat niet.");
			return 1;
		}
	}
}
