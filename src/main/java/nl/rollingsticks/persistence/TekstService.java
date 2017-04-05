package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Tekst;

@Service
@Transactional
public class TekstService {

	@Autowired
	private TekstRepository tekstRepository;
	
//	public void save(Tekst tekst){
//		tekstRepository.save(tekst);
//	}
//
	public Tekst save(Tekst tekst){
		return tekstRepository.save(tekst);
	}

	public Tekst findById(Long id) {
		return tekstRepository.findOne(id);
	}
	
	public Iterable <Tekst> findAll(){
		Iterable <Tekst> result = tekstRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		tekstRepository.delete(id);
	}
	
}
