package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Eigenaar;

@Service
@Transactional
public class EigenaarService {
	@Autowired
	private EigenaarRepository eigenaarRepository;

	public Eigenaar save(Eigenaar eigenaar){
		return eigenaarRepository.save(eigenaar);
	}

	public Eigenaar findById(Long id) {
		return eigenaarRepository.findOne(id);
	}
	
	public Iterable <Eigenaar> findAll(){
		Iterable <Eigenaar> result = eigenaarRepository.findAll();
		return result;
	}

	public void deleteById(Long id) {
		eigenaarRepository.delete(id);
	}
	
}