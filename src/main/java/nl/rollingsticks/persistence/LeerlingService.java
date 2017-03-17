package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Leerling;

@Service
@Transactional
public class LeerlingService {
	@Autowired
	
	private LeerlingRepository leerlingRepository;

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
	
}