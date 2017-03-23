package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Muziekstuk;

@Service
@Transactional
public class MuziekstukService {

	@Autowired
	private MuziekstukRepository muziekstukRepository;

	public Muziekstuk save(Muziekstuk muziekstuk){
		return muziekstukRepository.save(muziekstuk);
	}

	public Muziekstuk findById(Long id) {
		return muziekstukRepository.findOne(id);
	}
	
	public Iterable <Muziekstuk> findAll(){
		Iterable <Muziekstuk> result = muziekstukRepository.findAll();
		return result;
	}
	
	public void deleteById(Long id) {
		muziekstukRepository.delete(id);
	}
}
