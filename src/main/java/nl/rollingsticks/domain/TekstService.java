package nl.rollingsticks.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TekstService {

	@Autowired
	private TekstRepository tekstRepository;
	
	public Iterable <Tekst> findAll(){
		Iterable <Tekst> result = tekstRepository.findAll();
		return result;
	}
	
	public void save(Tekst tekst){
		tekstRepository.save(tekst);
	}
}
