package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Docent;

@Service
@Transactional
public class DocentService {
	
	@Autowired
	private DocentRepository docentRepository;

	public Docent save(Docent docent){
		return docentRepository.save(docent);
	}
	
	public Docent findById(Long id) {
		return docentRepository.findOne(id);
	}

	public Iterable <Docent> findAll(){
		Iterable <Docent> result = docentRepository.findAll();
		return result;
	}
	
	public void deleteById(Long id) {
		docentRepository.delete(id);
	}
}
