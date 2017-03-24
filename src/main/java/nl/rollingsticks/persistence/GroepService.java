package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Groep;

@Service
@Transactional
public class GroepService {

	@Autowired
	private GroepRepository groepRepository;

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
}
