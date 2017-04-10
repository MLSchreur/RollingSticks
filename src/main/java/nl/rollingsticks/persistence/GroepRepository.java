package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Groep;

@Component
public interface GroepRepository extends CrudRepository <Groep, Long>{

}