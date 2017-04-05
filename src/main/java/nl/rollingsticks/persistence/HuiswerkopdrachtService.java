package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Huiswerkopdracht;

@Service
@Transactional
public class HuiswerkopdrachtService {

	@Autowired
	private HuiswerkopdrachtRepository huiswerkopdrachtRepository;

	public Huiswerkopdracht save(Huiswerkopdracht huiswerkopdracht) {
		return huiswerkopdrachtRepository.save(huiswerkopdracht);
	}

	public Huiswerkopdracht findById(Long id) {
		return huiswerkopdrachtRepository.findOne(id);
	}
	
	public Iterable <Huiswerkopdracht> findAll(){
		Iterable <Huiswerkopdracht> result = huiswerkopdrachtRepository.findAll();
		return result;
	}
	
	public void deleteById(Long id) {
		huiswerkopdrachtRepository.delete(id);
	}
}
