package nl.rollingsticks.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.rollingsticks.domain.Bladmuziek;

@Service
@Transactional
public class BladmuziekService {

	@Autowired
	private BladmuziekRepository bladmuziekRepository;

//	public void save(Bladmuziek bladmuziek){
//		bladmuziekRepository.save(bladmuziek);
//	}
//
	public Bladmuziek save(Bladmuziek bladmuziek){
		return bladmuziekRepository.save(bladmuziek);
	}

	public Bladmuziek findById(Long id) {
		return bladmuziekRepository.findOne(id);
	}
	
	public Iterable <Bladmuziek> findAll(){
		Iterable <Bladmuziek> result = bladmuziekRepository.findAll();
		return result;
	}
	
}
